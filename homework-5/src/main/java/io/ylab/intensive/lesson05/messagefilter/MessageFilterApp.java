package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {
    public static void main(String[] args) throws IOException, TimeoutException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        DataLoader dataLoader = applicationContext.getBean(DataLoader.class);
        dataLoader.execute();
        QueueProcessor messageProcessor = applicationContext.getBean(QueueProcessor.class);
        messageProcessor.execute();
    }
}
