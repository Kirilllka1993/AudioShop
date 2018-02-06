package by.kazimirov.dao;

import by.kazimirov.entity.Album;
import by.kazimirov.exception.DAOException;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class AlbumDAO {
    private Connection connection;
    private static final String SQL_ADD_ALBUM = "INSERT INTO `albums` (`album_title`, `release_date`, `album_image`) " +
            "VALUES (?, ?, ?)";
    private static final String SQL_CHECK_ALBUM = "SELECT `album_id` FROM `albums` WHERE `album_title` = ? AND `release_date` = ?";
    private static final String SQL_LOAD_ALBUM = "SELECT * FROM `albums` WHERE `album_title` = ? AND `release_date`=?";
    private static final String SQL_LOAD_ALBUM_BY_ID = "SELECT * FROM `albums` WHERE `album_id` = ?";
    private static final String SQL_LOAD_ALL_ALBUMS = "SELECT * FROM `albums` ORDER BY `album_title`";
    private static final String SQL_LOAD_ARTIST_ALBUMS = "SELECT `album_id`, `album_title` FROM `albums` WHERE `artist_id` = ?";
    private static final String SQL_LOAD_ALBUM_ID = "SELECT `album_id` FROM `albums` WHERE `album_title` = ? AND `release_date`=?";
    private static final String SQL_UPDATE_ARTIST_ID = "UPDATE `albums` SET `artist_id` = ? WHERE `album_id` = ?";
    private static final String SQL_LOAD_IMAGE = "SELECT `album_image` FROM `albums` WHERE `album_id` = ?";

    private static final String COLUMN_ALBUM_ID = "album_id";
    private static final String COLUMN_ARTIST_ID = "artist_id";
    private static final String COLUMN_TITLE = "album_title";
    private static final String COLUMN_RELEASE_DATE = "release_date";
    private static final String COLUMN_IMAGE = "album_image";



    public AlbumDAO(Connection connection) {
        this.connection = connection;
    }

    public int addNewAlbum(String title, LocalDate releaseDate, byte[] image) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_ALBUM, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setDate(2, Date.valueOf(releaseDate));
            if (image != null) {
                preparedStatement.setBlob(3, new ByteArrayInputStream(image));
            } else {
                preparedStatement.setBlob(3, (Blob) null);
            }
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DAOException("Error while inserting new album into database.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while inserting new album into database.", e);
        }
    }

    public boolean checkAlbum(String title, LocalDate releaseDate) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_ALBUM)) {
            preparedStatement.setString(1, title);
            preparedStatement.setDate(2, Date.valueOf(releaseDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking album in database.", e);
        }
    }

    public Album findAlbum(String title, LocalDate releaseDate) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALBUM)) {
            preparedStatement.setString(1, title);
            preparedStatement.setDate(2, Date.valueOf(releaseDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Album album = new Album();
                album.setAlbumId(resultSet.getInt(COLUMN_ALBUM_ID));
                album.setArtistId(resultSet.getInt(COLUMN_ARTIST_ID));
                album.setTitle(resultSet.getString(COLUMN_TITLE));
                album.setReleaseDate(resultSet.getDate(COLUMN_RELEASE_DATE));
                Blob image = resultSet.getBlob(COLUMN_IMAGE);
                if (image == null) {
                    album.setImage(null);
                } else {
                    album.setImage(image.getBytes(1, (int) image.length()));
                }
                return album;
            } else {
                throw new DAOException("No album with such title and release date found in database.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error while searching for album by title and release date in database.", e);
        }
    }

    public Album findAlbumById(int albumId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALBUM_BY_ID)) {
            preparedStatement.setInt(1, albumId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Album album = null;
            if (resultSet.next()) {
                album = new Album();
                album.setAlbumId(resultSet.getInt(COLUMN_ALBUM_ID));
                album.setArtistId(resultSet.getInt(COLUMN_ARTIST_ID));
                album.setTitle(resultSet.getString(COLUMN_TITLE));
                album.setReleaseDate(resultSet.getDate(COLUMN_RELEASE_DATE));
                Blob image = resultSet.getBlob(COLUMN_IMAGE);
                if (image == null) {
                    album.setImage(null);
                } else {
                    album.setImage(image.getBytes(1, (int) image.length()));
                }
            }
            return album;
        } catch (SQLException e) {
            throw new DAOException("Error while searching for album by title and release date in database.", e);
        }
    }

    public List<Album> loadAllAlbums() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALL_ALBUMS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Album> albumList = new LinkedList<>();

            while (resultSet.next()) {
                Album album = new Album();
                album.setAlbumId(resultSet.getInt(COLUMN_ALBUM_ID));
                album.setTitle(resultSet.getString(COLUMN_TITLE));
                album.setReleaseDate(resultSet.getDate(COLUMN_RELEASE_DATE));
                albumList.add(album);
            }
            return albumList;
        } catch (SQLException e) {
            throw new DAOException("Error while selecting all albums from database.", e);
        }
    }


    public void updateArtistId(String[] albumIds, int artistId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ARTIST_ID)) {
            for (int i = 0; i < albumIds.length; i++) {
                preparedStatement.setInt(1, artistId);
                preparedStatement.setInt(2, Integer.parseInt(albumIds[i]));
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating artist id for albums in database.", e);
        }
    }


    public List<Album> loadArtistAlbums(int artistId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ARTIST_ALBUMS)) {
            preparedStatement.setInt(1, artistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Album> albums = new LinkedList<>();
            while (resultSet.next()) {
                Album album = new Album();
                album.setAlbumId(resultSet.getInt(COLUMN_ALBUM_ID));
                album.setArtistId(artistId);
                album.setTitle(resultSet.getString(COLUMN_TITLE));
                albums.add(album);
            }

            return albums;
        } catch (SQLException e) {
            throw new DAOException("Error while loading artist albums from database.", e);
        }
    }

    public byte[] findImage(int albumId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_IMAGE)) {
            preparedStatement.setInt(1, albumId);
            ResultSet resultSet = preparedStatement.executeQuery();
            byte[] image = null;
            while (resultSet.next()) {
                image = resultSet.getBytes(COLUMN_IMAGE);
            }
            return image;
        } catch (SQLException e) {
            throw new DAOException("Error while searching for album image in database.", e);
        }
    }
}
