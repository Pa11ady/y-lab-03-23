package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
    private final static String QUEUE_NAME = "queue1";
    private final ConnectionFactory factory;
    private final DataSource dataSource;

    public PersonApiImpl(ConnectionFactory factory, DataSource dataSource) {
        this.factory = factory;
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        if (personId == null) {
            System.out.println("Код пользователя не должен быть null");
            return;
        }
        String message = String.join(";", "DELETE", personId.toString());
        sendToRabbit(message);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        if (personId == null) {
            System.out.println("Код пользователя не должен быть null");
            return;
        }
        if (firstName == null || firstName.isEmpty()) {
            firstName = " ";
        }
        if (lastName == null || lastName.isEmpty()){
            lastName = " ";
        }
        if (middleName == null || middleName.isEmpty()) {
            middleName = " ";
        }

        String message = String.join(";", "SAVE", personId.toString(), firstName, lastName, middleName);
        sendToRabbit(message);
    }

    @Override
    public Person findPerson(Long personId) {
        final String SQL_SELECT =
                "SELECT person_id, " +
                        "first_name, " +
                        "last_name, " +
                        "middle_name " +
                        "FROM person " +
                        "WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT)) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return toPerson(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        final String SQL_SELECT =
                "SELECT person_id, " +
                        "first_name, " +
                        "last_name, " +
                        "middle_name " +
                        "FROM person";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                persons.add(toPerson(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    private Person toPerson(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4));
    }

    private void sendToRabbit(String message) {
        try (com.rabbitmq.client.Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
