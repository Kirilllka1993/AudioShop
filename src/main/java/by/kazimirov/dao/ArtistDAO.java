package by.kazimirov.dao;

import by.kazimirov.entity.Artist;
import by.kazimirov.exception.DAOException;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ArtistDAO {
    private Connection connection;
    private static final String SQL_ADD_ARTIST = "INSERT INTO `artists` (`artist_name`, `country`, `description`, `artist_image`) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_CHECK_ARTIST = "SELECT `artist_id` FROM `artists` WHERE `artist_name` = ? AND `country` = ?";
    private static final String SQL_LOAD_ARTIST_BY_ALBUM_ID = "SELECT * FROM `artists` JOIN `albums` ON `artists`.`artist_id` = `albums`.`artist_id` WHERE `album_id` = ?";
    private static final String SQL_LOAD_ARTIST_BY_ARTIST_ID = "SELECT * FROM `artists` WHERE `artist_id` = ?";
    private static final String SQL_LOAD_ALL_ARTISTS = "SELECT * FROM `artists` ORDER BY `artist_name`";
    private static final String SQL_LOAD_IMAGE = "SELECT `artist_image` FROM `artists` WHERE `artist_id` = ?";

    private static final String COLUMN_ARTIST_ID = "artist_id";
    private static final String COLUMN_ARTIST_NAME = "artist_name";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "artist_image";

    public ArtistDAO(Connection connection) {
        this.connection = connection;
    }

    public int addNewArtist(String name, String country, String description, byte[] image) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_ARTIST, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, description);
            if (image != null) {
                preparedStatement.setBlob(4, new ByteArrayInputStream(image));
            } else {
                preparedStatement.setBlob(4, (Blob) null);
            }
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DAOException("Error while inserting new artist into database.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while inserting new artist into database.", e);
        }
    }

    public boolean checkArtist(String name, String country) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_ARTIST)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, country);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking artist in database.", e);
        }
    }

    public Artist findArtistByArtistId(int artistId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ARTIST_BY_ARTIST_ID)) {
            preparedStatement.setInt(1, artistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Artist artist = null;
            if (resultSet.next()) {
                artist = new Artist();
                artist.setArtistId(resultSet.getInt(COLUMN_ARTIST_ID));
                artist.setName(resultSet.getString(COLUMN_ARTIST_NAME));
                artist.setCountry(resultSet.getString(COLUMN_COUNTRY));
                artist.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
            }
            return artist;
        } catch (SQLException e) {
            throw new DAOException("Error while searching for artist by name and country in database.", e);
        }
    }
    public Artist findArtistByAlbumId(int albumId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ARTIST_BY_ALBUM_ID)) {
            preparedStatement.setInt(1, albumId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Artist artist = null;
            if (resultSet.next()) {
                artist = new Artist();
                artist.setArtistId(resultSet.getInt(COLUMN_ARTIST_ID));
                artist.setName(resultSet.getString(COLUMN_ARTIST_NAME));
                artist.setCountry(resultSet.getString(COLUMN_COUNTRY));
                artist.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
            }
            return artist;
        } catch (SQLException e) {
            throw new DAOException("Error while searching for artist by name and country in database.", e);
        }
    }

    public List<Artist> loadAllArtists() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALL_ARTISTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Artist> artistList = new LinkedList<>();
            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setArtistId(resultSet.getInt(COLUMN_ARTIST_ID));
                artist.setName(resultSet.getString(COLUMN_ARTIST_NAME));
                artist.setCountry(resultSet.getString(COLUMN_COUNTRY));
                artist.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                artistList.add(artist);
            }
            return artistList;
        } catch (SQLException e) {
            throw new DAOException("Error while selecting all albums from database.", e);
        }
    }

    public byte[] findImage(int artistId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_IMAGE)) {
            preparedStatement.setInt(1, artistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            byte[] image = null;
            while (resultSet.next()) {
                image = resultSet.getBytes(COLUMN_IMAGE);
            }
            return image;
        } catch (SQLException e) {
            throw new DAOException("Error while searching for artist image in database.", e);
        }
    }
}
