package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.util.List;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        DataSource dataSource = initDB();

        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApi personApi = new PersonApiImpl(connectionFactory, dataSource);

        try {
            personApi.deletePerson(1L);
            personApi.savePerson(10L, "Иван1", "Иван2", "Иван3");
            personApi.savePerson(15L, "del1", "del2", "del3");
            personApi.savePerson(20L, "Владимир1", "Владимир2", "Владимир3");

            personApi.deletePerson(15L);
            personApi.findPerson(3L);

            Thread.sleep(1000);
            List<Person> people = personApi.findAll();
            people.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDB() throws Exception {
        return DbUtil.buildDataSource();
    }
}

