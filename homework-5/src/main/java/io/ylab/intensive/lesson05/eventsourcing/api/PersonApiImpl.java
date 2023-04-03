package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonApiImpl implements PersonApi {
    private final ApiDbClient apiDbClient;
    private final ApiRabbitClient apiRabbitClient;

    @Autowired
    public PersonApiImpl(ApiDbClient apiDbClient, ApiRabbitClient apiRabbitClient) {
        this.apiDbClient = apiDbClient;
        this.apiRabbitClient = apiRabbitClient;
    }

    @Override
    public void deletePerson(Long personId) {
        if (personId == null) {
            System.out.println("Код пользователя не должен быть null");
            return;
        }
        String message = String.join(";", "DELETE", personId.toString());
        apiRabbitClient.sendToRabbit(message);
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
        apiRabbitClient.sendToRabbit(message);
    }

    @Override
    public Person findPerson(Long personId) {
        return apiDbClient.findPerson(personId);
    }

    @Override
    public List<Person> findAll() {
        return apiDbClient.findAll();
    }
}

