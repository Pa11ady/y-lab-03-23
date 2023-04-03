package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        ApiDbClient apiDbClient = applicationContext.getBean(ApiDbClient.class);
        apiDbClient.initDB();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        testPersonApi(personApi);
    }

    private static void testPersonApi(PersonApi personApi) throws InterruptedException {
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
}
