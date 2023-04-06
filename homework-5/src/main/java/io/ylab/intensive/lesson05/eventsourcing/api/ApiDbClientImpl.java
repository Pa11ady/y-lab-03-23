package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.DbUtil;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiDbClientImpl implements ApiDbClient {
    private final DataSource dataSource;

    @Autowired
    public ApiDbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void initDB() {
        String ddl = ""
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        try {
            DbUtil.applyDdl(ddl, dataSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        final String SQL_SELECT =
                "SELECT person_id, " +
                        "first_name, " +
                        "last_name, " +
                        "middle_name " +
                        "FROM person " +
                        "WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT)) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return toPerson(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        final String SQL_SELECT =
                "SELECT person_id, " +
                        "first_name, " +
                        "last_name, " +
                        "middle_name " +
                        "FROM person";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                persons.add(toPerson(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    private Person toPerson(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4));
    }
}
