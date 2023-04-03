package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        DataSource dataSource = initDB();

        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApi personApi = new PersonApiImpl(connectionFactory, dataSource);

        personApi.deletePerson(1L);
        personApi.deletePerson(null);
        personApi.savePerson(3L, "Андрей", "", "");
        personApi.savePerson(2L, null, "Ваня2", null);
        personApi.savePerson(5L, "Вова", null, null);
        personApi.savePerson(null, null, null, null);
        personApi.savePerson(10L, null, null, null);
        personApi.savePerson(10L, "", "", "");
        personApi.savePerson(10L, "Иван1", "Иван2", "Иван3");
        personApi.savePerson(10L, "Петров1", "Петров2", "Петров3");
        personApi.savePerson(15L, "del1", "del2", "del3");
        personApi.savePerson(20L, "Владимир1", "Владимир2", "Владимир3");

        personApi.deletePerson(15L);
        personApi.findPerson(3L);

        Thread.sleep(4000);
        List<Person> people = personApi.findAll();
        people.forEach(System.out::println);
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    /**
     * Чтобы не валилось, если запустят сервис раньше.
     */

    private static DataSource initDB() throws SQLException {
        String ddl = ""
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
}

