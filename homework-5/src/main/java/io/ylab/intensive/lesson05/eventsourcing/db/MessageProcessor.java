package io.ylab.intensive.lesson05.eventsourcing.db;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageProcessor {
    void execute() throws IOException, TimeoutException;
}
