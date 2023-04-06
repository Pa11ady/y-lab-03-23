package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class DataLoaderImpl implements DataLoader {
    private final File inputFile;
    private final DbClient client;

    @Autowired
    public DataLoaderImpl(File inputFile, DbClient client) {
        this.inputFile = inputFile;
        this.client = client;
    }

    @Override
    public void execute() {
        client.initDb();
        client.insert(readFile());
    }

    private List<String> readFile() {
        List<String> lines;
        Path path = inputFile.toPath();
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Невозможно прочитать файл " + path);
        }
        return lines;
    }
}
