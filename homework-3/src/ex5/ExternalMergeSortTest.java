package ex5;

import java.io.*;
import java.util.Random;

public class ExternalMergeSortTest {
    public static void main(String[] args) throws IOException {
        String inputFile = "input.txt";
        int n = 10000000; // Количество чисел во входном файле

        // Создаем входной файл с n случайными числами
        Random random = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (int i = 0; i < n; i++) {
                writer.write(Long.toString(random.nextLong()));
                writer.newLine();
            }
        }

        // Сортируем входной файл внешней сортировкой слиянием
        long startTime = System.currentTimeMillis();
        Sorter sorter = new Sorter();
        File tmp = sorter.sortFile(new File("input.txt"));
        System.out.println(tmp.getName());
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");

        // Проверяем, что выходной файл содержит отсортированные числа
        try (BufferedReader reader = new BufferedReader(new FileReader(tmp))) {
            String line;
            long prev = Long.MIN_VALUE;
            while ((line = reader.readLine()) != null) {
                long curr = Long.parseLong(line);
                if (curr < prev) {
                    System.out.println("Файл не отсортирован.");
                    break;
                }
                prev = curr;
            }
        }
    }
}
