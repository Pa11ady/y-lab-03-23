package io.ylab.intensive.lesson05.eventsourcing.api;

public interface ApiRabbitClient {
    void sendToRabbit(String message);
}
