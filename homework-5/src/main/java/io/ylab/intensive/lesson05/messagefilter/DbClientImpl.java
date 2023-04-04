package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
public class DbClientImpl implements DbClient {
    private final DataSource dataSource;

    @Autowired
    public DbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void initDb() {
        String ddl = "" +
                "CREATE TABLE IF NOT EXISTS bad_words ( " +
                "id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                "name VARCHAR(30) NOT NULL, " +
                "CONSTRAINT uc_name UNIQUE (name))";
        //Пункт задания
        if (!containTable()) {
            applySql(ddl);
        }
        applySql("DELETE FROM bad_words");
    }

    @Override
    public void insert(List<String> words) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(
                     "INSERT INTO bad_words (name) VALUES (?)")) {
            for (String word : words) {
                prepareStatement.setString(1, word);
                prepareStatement.addBatch();
            }
            prepareStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isBadWord(String word) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id FROM  bad_words WHERE name = ?")) {
            preparedStatement.setString(1, word);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void applySql(String sql) {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean containTable() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet resultSet = metaData.getColumns(null, null, "bad_words", null)) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
