package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MovieLoaderImpl implements MovieLoader {
    private final DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        List<Movie> movies = loadMovies(file);
        saveMoviesToDB(movies);
    }

    private List<Movie> loadMovies(File file) {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                Movie movie = new Movie();
                movie.setYear(values[0].isEmpty() ? null : Integer.parseInt(values[0]));
                movie.setLength(values[1].isEmpty() ? null : Integer.parseInt(values[1]));
                movie.setTitle(values[2]);
                movie.setSubject(values[3]);
                movie.setActors(values[4]);
                movie.setActress(values[5]);
                movie.setDirector(values[6]);
                movie.setPopularity(values[7].isEmpty() ? null : Integer.parseInt(values[7]));
                movie.setAwards(toBoolean(values[8]));
                movies.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }
    private Boolean toBoolean(String value) {
        if ("Yes".equals(value)) {
            return true;
        }
        if ("No".equals(value)) {
            return false;
        }
        return null;
    }

    private void saveMoviesToDB(List<Movie> movies) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO movie( " +
                     "year, " +
                     "length, " +
                     "title, " +
                     "subject, " +
                     "actors, " +
                     "actress, " +
                     "director, " +
                     "popularity, " +
                     "awards) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            for (Movie movie : movies) {
                Integer year = movie.getYear();
                if (year == null) {
                    statement.setNull(1, Types.INTEGER);
                } else {
                    statement.setInt(1, year);
                }

                Integer length = movie.getLength();
                if (length == null) {
                    statement.setNull(2, Types.INTEGER);
                } else {
                    statement.setInt(2, length);
                }

                statement.setString(3, movie.getTitle());
                statement.setString(4, movie.getSubject());
                statement.setString(5, movie.getActors());
                statement.setString(6, movie.getActress());
                statement.setString(7, movie.getDirector());

                Integer popularity =  movie.getPopularity();
                if (popularity == null) {
                    statement.setNull(8, Types.INTEGER);
                } else {
                    statement.setInt(8, popularity);
                }

                Boolean awards = movie.getAwards();
                if (awards == null) {
                    statement.setNull(9, Types.BOOLEAN);
                } else {
                    statement.setBoolean(9, awards);
                }
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
