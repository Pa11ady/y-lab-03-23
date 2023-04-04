package io.ylab.intensive.lesson05.messagefilter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface QueueProcessor {
    void execute() throws IOException, TimeoutException;
}
