package by.kazimirov.dao;

import by.kazimirov.entity.Genre;
import by.kazimirov.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class GenreDAO {
    private Connection connection;
    private static final String SQL_ADD_GENRE = "INSERT INTO `genres` (`genre_name`) VALUES (?)";
    private static final String SQL_CHECK_GENRE_UNIQUENESS = "SELECT `genre_id` FROM `genres` WHERE `genre_name` = ?";
    private static final String SQL_LOAD_ALL_GENRES = "SELECT `genre_name`, `genre_id` FROM `genres`";
    private static final String SQL_LOAD_GENRE_BY_ID = "SELECT * FROM `genres` WHERE `genre_id` = ?";
    private static final String COLUMN_GENRE_ID = "genre_id";
    private static final String COLUMN_GENRE_NAME = "genre_name";

    public GenreDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Genre> loadAllGenres() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALL_GENRES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Genre> genreList = new LinkedList<>();

            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setGenreId(resultSet.getInt(COLUMN_GENRE_ID));
                genre.setName(resultSet.getString(COLUMN_GENRE_NAME));
                genreList.add(genre);
            }
            return genreList;
        } catch (SQLException e) {
            throw new DAOException("Error while selecting all genres from database.", e);
        }
    }

    public boolean checkGenreUniqueness(String genreName) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_GENRE_UNIQUENESS)) {
            preparedStatement.setString(1, genreName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking genre name uniqueness in database.", e);
        }
    }

    public void addNewGenre(String genreName) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GENRE)) {
            preparedStatement.setString(1, genreName);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while inserting new genre in database.", e);
        }
    }

    public Genre loadGenreById(int genreId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_GENRE_BY_ID)) {
            preparedStatement.setInt(1, genreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Genre genre = null;
            if(resultSet.next()) {
                genre = new Genre();
                genre.setGenreId(genreId);
                genre.setName(resultSet.getString(COLUMN_GENRE_NAME));
            }
            return genre;
        } catch (SQLException e) {
            throw new DAOException("Error while inserting new genre in database.", e);
        }
    }
}
