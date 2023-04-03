package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Версия с пакетной обработкой работает существенно быстрее!
 * Продолжительность пакетной обработки: 9418
 * Продолжительность обычной обработки: 122638
 */


public class FileSortImpl implements FileSorter {
    private final DataSource dataSource;
    private static final String FILE_OUT = "out.txt";

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        List<Long> numbers = loadFile(data);
        saveToDB(numbers);
        numbers = loadFromDB();
        return saveFile(numbers);
    }

    private void saveToDB(List<Long> numbers) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(
                     "INSERT INTO numbers (val) VALUES (?)")) {
            for (Long value : numbers) {
                prepareStatement.setLong(1, value);
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Long> loadFile(File data) {
        List<Long> numbers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(data))) {
            while (scanner.hasNext()) {
                long number = scanner.nextLong();
                numbers.add(number);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return numbers;
    }

    private File saveFile(List<Long> numbers) {
        File file = new File(FILE_OUT);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            numbers.forEach(printWriter::println);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    private List<Long> loadFromDB() {
        List<Long> numbers = new ArrayList<>();
        String selectSql = "SELECT val FROM numbers ORDER BY val DESC";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            while (resultSet.next()) {
                long value = resultSet.getLong(1);
                numbers.add(value);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numbers;
    }

}
