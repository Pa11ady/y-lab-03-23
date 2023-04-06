package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class MessageProcessorImpl implements MessageProcessor {
    private final static String QUEUE_NAME = "queue1";
    private final ConnectionFactory connectionFactory;
    private final DbClient dbClient;

    @Autowired
    public MessageProcessorImpl(ConnectionFactory connectionFactory, DbClient dbClient) {
        this.connectionFactory = connectionFactory;
        this.dbClient = dbClient;
    }

    @Override
    public void execute()  throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
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

    private void processingMessage(String message) {
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

    private void saveToDB(Person person) {
        if (dbClient.containsPerson(person.getId())) {
            dbClient.update(person);
        } else {
            dbClient.insert(person);
        }
    }

    private void deleteFromDB(Long personId) {
        if (!dbClient.containsPerson(personId)) {
            System.out.println("Попытка удаления. Пользователь с id=" + personId + " не существует.");
            return;
        }
        dbClient.delete(personId);
    }
}
