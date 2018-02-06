package by.kazimirov.logic;

import by.kazimirov.ajax.BiTuple;
import by.kazimirov.dao.ArtistDAO;
import by.kazimirov.database.ConnectionPool;
import by.kazimirov.entity.Artist;
import by.kazimirov.exception.DAOException;
import by.kazimirov.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class ArtistLogic {
    public BiTuple<LogicActionResult, Integer> addArtist(String name, String country, String description, byte[] image) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ArtistDAO artistDAO = new ArtistDAO(connection);
            LogicActionResult actionResult = new LogicActionResult();

            int artistId = 0;

            if (artistDAO.checkArtist(name, country)) {
                artistId = artistDAO.addNewArtist(name, country, description, image);
                actionResult.setState(LogicActionResult.State.SUCCESS);
                actionResult.setResult(ActionResult.SUCCESS_ADD_ARTIST);
            } else {
                actionResult.setState(LogicActionResult.State.FAILURE);
                actionResult.setResult(ActionResult.FAILURE_ARTIST_NOT_UNIQUE);
            }
            return new BiTuple<>(actionResult, artistId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while creating artist in logic.", e);
        }
    }

    public Artist loadArtistByArtistId(int artistId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ArtistDAO artistDAO = new ArtistDAO(connection);
            return artistDAO.findArtistByArtistId(artistId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading album id in logic.", e);
        }
    }

    public Artist loadArtistByAlbumId(int albumId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ArtistDAO artistDAO = new ArtistDAO(connection);
            return artistDAO.findArtistByAlbumId(albumId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading album id in logic.", e);
        }
    }

    public List<Artist> loadAllArtists() throws LogicException {
        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            ArtistDAO artistDAO = new ArtistDAO(connection);
            return artistDAO.loadAllArtists();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading all artists in logic.", e);
        }
    }

    public byte[] loadImage(int albumId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ArtistDAO artistDAO = new ArtistDAO(connection);
            return artistDAO.findImage(albumId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading image.", e);
        }
    }
}
