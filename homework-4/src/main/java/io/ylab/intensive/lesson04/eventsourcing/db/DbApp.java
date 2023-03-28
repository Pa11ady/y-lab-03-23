package io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;


public class DbApp {
    private final static String QUEUE_NAME = "queue1";
    private static DataSource dataSource;

    public static void main(String[] args) throws Exception {
        dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();

        // тут пишем создание и запуск приложения работы с БД
        getMessageFromRabbit(connectionFactory);
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }

    private static void getMessageFromRabbit(ConnectionFactory factory) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Ожидание сообщений...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Сообщение '" + message + "'");
            processingMessage(message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    private static void processingMessage(String message) {
        String[] split = message.split(";");
        if (split.length == 0) {
            System.out.println("Неверная команда");
            return;
        }
        String command = split[0];

        if ("SAVE".equals(command) && split.length >= 5) {
            Person person = toPerson(split);
            saveToDB(person);
        } else if ("DELETE".equals(command) && split.length == 2) {
            long id = Long.parseLong(split[1]);
            deleteFromDB(id);
        } else {
            System.out.println("Неверная команда");
        }
    }

    private static Person toPerson(String[] cols) {
        long id = Long.parseLong(cols[1]);
        String name = cols[2].isBlank() ? "" : cols[2];
        String lastName = cols[3].isBlank() ? "" : cols[3];
        String middleName = cols[4].isBlank() ? "" : cols[4];
        return new Person(id, name, lastName, middleName);
    }

    private static void saveToDB(Person person) {
        if (containsPerson(person.getId())) {
            updateData(person);
        } else {
            insertData(person);
        }
    }

    private static void insertData(Person person) {
        final String SQL_INSERT =
                "INSERT INTO person (person_id, first_name, last_name, middle_name) " +
                        "VALUES(?, ?, ?, ?)";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setLong(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, person.getMiddleName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateData(Person person) {
        final String SQL_UPDATE =
                "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getMiddleName());
            preparedStatement.setLong(4, person.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFromDB(Long personId) {
        if (!containsPerson(personId)) {
            System.out.println("Попытка удаления. Пользователь с id=" + personId + " не существует.");
        }
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM person WHERE person_id = ?")) {
            preparedStatement.setLong(1, personId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean containsPerson(Long personId) {
        final String SQL_SELECT = "SELECT person_id FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT)) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
