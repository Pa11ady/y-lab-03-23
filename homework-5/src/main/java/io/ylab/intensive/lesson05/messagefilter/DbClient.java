package io.ylab.intensive.lesson05.messagefilter;

import java.util.List;

public interface DbClient {
    void initDb();

    void insert(List<String> words);

    boolean isBadWord(String word);
}
