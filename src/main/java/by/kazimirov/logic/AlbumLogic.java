package by.kazimirov.logic;

import by.kazimirov.ajax.BiTuple;
import by.kazimirov.dao.AlbumDAO;
import by.kazimirov.database.ConnectionPool;
import by.kazimirov.entity.Album;
import by.kazimirov.exception.DAOException;
import by.kazimirov.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public class AlbumLogic {
    public BiTuple<LogicActionResult, Integer> addAlbum(String title, LocalDate releaseDate, byte[] image) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AlbumDAO albumDAO = new AlbumDAO(connection);
            LogicActionResult actionResult = new LogicActionResult();

            int albumId = 0;

            if (albumDAO.checkAlbum(title, releaseDate)) {
                albumId = albumDAO.addNewAlbum(title, releaseDate, image);
                actionResult.setState(LogicActionResult.State.SUCCESS);
                actionResult.setResult(ActionResult.SUCCESS_ADD_ALBUM);
            } else {
                actionResult.setState(LogicActionResult.State.FAILURE);
                actionResult.setResult(ActionResult.FAILURE_ALBUM_NOT_UNIQUE);
            }
            return new BiTuple<>(actionResult, albumId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while creating album in logic.", e);
        }
    }

    public Album loadAlbum(String title, LocalDate releaseDate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AlbumDAO albumDAO = new AlbumDAO(connection);
            return albumDAO.findAlbum(title, releaseDate);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading album in logic.", e);
        }
    }

    public Album loadAlbumById(int albumId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AlbumDAO albumDAO = new AlbumDAO(connection);
            return albumDAO.findAlbumById(albumId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading album in logic.", e);
        }
    }

    public List<Album> loadAllAlbums() throws LogicException {
        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            AlbumDAO albumDAO = new AlbumDAO(connection);
            return albumDAO.loadAllAlbums();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading all albums in logic.", e);
        }
    }


    public void setArtistId(String[] albumIds, int artistId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AlbumDAO albumDAO = new AlbumDAO(connection);
            albumDAO.updateArtistId(albumIds, artistId);
        } catch (SQLException | DAOException e) {
            throw new LogicException(e);
        }
    }


    public List<Album> loadArtistAlbums(int artistId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AlbumDAO albumDAO = new AlbumDAO(connection);
            return albumDAO.loadArtistAlbums(artistId);
        } catch (SQLException | DAOException e) {
            throw new LogicException(e);
        }
    }

    public byte[] loadImage(int albumId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AlbumDAO albumDAO = new AlbumDAO(connection);
            return albumDAO.findImage(albumId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading image.", e);
        }
    }
}
