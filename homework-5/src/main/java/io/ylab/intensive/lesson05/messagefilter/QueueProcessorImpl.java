package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class QueueProcessorImpl implements QueueProcessor {
    private final static String QUEUE_IN = "input";
    private final static String QUEUE_OUT = "output";

    private final TextProcessor textProcessor;
    private final ConnectionFactory connectionFactory;

    @Autowired
    public QueueProcessorImpl(TextProcessor textProcessor, ConnectionFactory connectionFactory) {
        this.textProcessor = textProcessor;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void execute() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_IN, false, false, false, null);
        channel.queueDeclare(QUEUE_OUT, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" in message: " + message);
            message = textProcessor.processText(message);
            System.out.println("out message: " + message);
            channel.basicPublish("", QUEUE_OUT, null, message.getBytes(StandardCharsets.UTF_8));
        };

        channel.basicConsume(QUEUE_IN, true, deliverCallback, consumerTag -> {
        });
    }
}


