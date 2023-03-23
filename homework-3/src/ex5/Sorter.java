package ex5;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Sorter {
    //private static final int CHUNK_SIZE = 10;
    private static final int CHUNK_SIZE = 5_000_000;
    private static final String TMP_FILE_PREFIX = "tmp_chunk_";

    public File sortFile(File dataFile) throws IOException {
        // Разбиваем файл на части и сортируем их в памяти
        List<File> chunks = splitIntoChunks(dataFile);
        for (File chunk : chunks) {
            sortChunk(chunk);
        }

        if (chunks.size() == 1) {
            return chunks.get(0);
        }
        // Сливаем части в один отсортированный файл
        return mergeChunks(chunks);
    }

    private List<File> splitIntoChunks(File dataFile) throws IOException {
        List<File> chunks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(dataFile))) {
            int chunkNum = 0;
            while (scanner.hasNextLong()) {
                // Создаем новую часть и записываем в нее числа
                File chunkFile = new File(TMP_FILE_PREFIX + chunkNum);
                try (PrintWriter pw = new PrintWriter(chunkFile)) {
                    for (int i = 0; i < CHUNK_SIZE && scanner.hasNextLong(); i++) {
                        pw.println(scanner.nextLong());
                    }
                    pw.flush();
                }
                chunks.add(chunkFile);
                chunkNum++;
            }
        }
        return chunks;
    }

    private void sortChunk(File chunk) throws IOException {
        List<Long> numbers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(chunk))) {
            while (scanner.hasNextLong()) {
                numbers.add(scanner.nextLong());
            }
        }
        Collections.sort(numbers);
        try (PrintWriter writer = new PrintWriter(chunk)) {
            for (Long number : numbers) {
                writer.println(number);
            }
            writer.flush();
        }
    }

    public static File mergeChunks(List<File> chunks) throws IOException {
        File mergedChunk = null;
        int numChunks = chunks.size();
        while (numChunks > 1) {
            List<File> mergedChunks = new ArrayList<>();
            for (int i = 0; i < numChunks; i += 2) {
                if (i == numChunks - 1) {
                    // Если частей нечетное количество, то последний просто копируется
                    mergedChunks.add(chunks.get(i));
                } else {
                    mergedChunk = mergeTwoChunks(chunks.get(i), chunks.get(i + 1));
                    mergedChunks.add(mergedChunk);
                }
            }
            // Обновляем список частей для объединения
            chunks = mergedChunks;
            numChunks = chunks.size();
        }
        return mergedChunk;
    }

    private static File mergeTwoChunks(File chunk1, File chunk2) throws IOException {
        File mergedChunk = File.createTempFile("chunk", ".txt");

        try (Scanner scanner1 = new Scanner(chunk1);
             Scanner scanner2 = new Scanner(chunk2);
             PrintWriter writer = new PrintWriter(new FileWriter(mergedChunk))) {

            Long num1 = getNextLong(scanner1);
            Long num2 = getNextLong(scanner2);

            while (num1 != null || num2 != null) {
                if (num1 == null) {
                    writer.println(num2);
                    num2 = getNextLong(scanner2);
                } else if (num2 == null) {
                    writer.println(num1);
                    num1 = getNextLong(scanner1);
                } else if (num1 < num2) {
                    writer.println(num1);
                    num1 = getNextLong(scanner1);
                } else {
                    writer.println(num2);
                    num2 = getNextLong(scanner2);
                }
            }
            writer.flush();
        }

        chunk1.delete();
        chunk2.delete();
        return mergedChunk;
    }

    private static Long getNextLong(Scanner scanner) {
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        }
        return null;
    }
}


