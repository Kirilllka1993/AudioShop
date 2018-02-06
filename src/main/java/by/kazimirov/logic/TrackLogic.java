package by.kazimirov.logic;

import by.kazimirov.dao.TrackDAO;
import by.kazimirov.database.ConnectionPool;
import by.kazimirov.entity.Track;
import by.kazimirov.exception.DAOException;
import by.kazimirov.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class TrackLogic {
    public LogicActionResult addTrack(String title, String location, double price, int genreId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            TrackDAO trackDAO = new TrackDAO(connection);
            LogicActionResult actionResult = new LogicActionResult();
            trackDAO.addNewTrack(title, location, price, genreId);
            actionResult.setState(LogicActionResult.State.SUCCESS);
            actionResult.setResult(ActionResult.SUCCESS_ADD_TRACK);
            return actionResult;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while creating track in logic.", e);
        }
    }

    public List<Track> loadPopularTracks(int page, int recordsPerPage) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            TrackDAO trackDAO = new TrackDAO(connection);

            return trackDAO.loadPortionOfTracks(page, recordsPerPage);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading popular tracks in logic.", e);
        }
    }

    public List<Track> loadAllTracks() throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            TrackDAO trackDAO = new TrackDAO(connection);
            return trackDAO.loadAllTracks();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading all tracks in logic.", e);
        }
    }

    public Track loadTrackById(int trackId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            TrackDAO trackDAO = new TrackDAO(connection);
            return trackDAO.findTrackById(trackId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading all tracks in logic.", e);
        }
    }

    public void setAlbumId(String[] trackIds, int albumId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            TrackDAO trackDAO = new TrackDAO(connection);
            trackDAO.updateAlbumId(trackIds, albumId);
        } catch (SQLException | DAOException e) {
            throw new LogicException(e);
        }
    }

    public List<Track> loadAlbumTracks(int albumId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            TrackDAO trackDAO = new TrackDAO(connection);
            return trackDAO.loadAlbumTracks(albumId);
        } catch (SQLException | DAOException e) {
            throw new LogicException(e);
        }
    }

    public List<Track> loadPurchaseTracks(int purchaseId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            TrackDAO trackDAO = new TrackDAO(connection);
            return trackDAO.loadPurchaseTracks(purchaseId);
        } catch (SQLException | DAOException e) {
            throw new LogicException(e);
        }
    }
}

