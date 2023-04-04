package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TextProcessorImpl implements TextProcessor {
    private final DbClient dbClient;

    @Autowired
    public TextProcessorImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public String processText(String input) {
        StringBuilder outputBuilder = new StringBuilder();
        int wordStart = -1;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                if (wordStart == -1) {
                    wordStart = i;
                }
            } else {
                if (wordStart != -1) {
                    String word = input.substring(wordStart, i);
                    outputBuilder.append(processWord(word));
                    wordStart = -1;
                }
                outputBuilder.append(c);
            }
        }
        if (wordStart != -1) {
            String word = input.substring(wordStart);
            outputBuilder.append(processWord(word));
        }
        return outputBuilder.toString();
    }

    String processWord(String word) {
        if (dbClient.isBadWord(word.toLowerCase())) {
            return censorWord(word);
        }
        return word;
    }

    private String censorWord(String word) {
        final int length = word.length();
        char[] outWord = new char[length];
        Arrays.fill(outWord, '*');
        outWord[0] = word.charAt(0);
        outWord[length - 1] = word.charAt(length - 1);
        return String.valueOf(outWord);
    }
}

