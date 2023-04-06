package io.ylab.intensive.lesson05.eventsourcing.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class ApiRabbitClientImpl implements ApiRabbitClient {
    private final static String QUEUE_NAME = "queue1";
    private final ConnectionFactory factory;

    @Autowired
    public ApiRabbitClientImpl(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void sendToRabbit(String message) {
        try (com.rabbitmq.client.Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
