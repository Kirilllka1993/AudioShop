package by.kazimirov.logic;

import by.kazimirov.dao.GenreDAO;
import by.kazimirov.database.ConnectionPool;
import by.kazimirov.entity.Genre;
import by.kazimirov.exception.DAOException;
import by.kazimirov.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class GenreLogic {
    public List<Genre> loadAllGenres() throws LogicException {
        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            GenreDAO genreDAO = new GenreDAO(connection);
            return genreDAO.loadAllGenres();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading all genres in logic.", e);
        }
    }

    public Genre loadGenreById(int genreId) throws LogicException {
        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            GenreDAO genreDAO = new GenreDAO(connection);
            return genreDAO.loadGenreById(genreId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading all genres in logic.", e);
        }
    }

    public LogicActionResult addGenre(String genreName) throws LogicException {
        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            GenreDAO genreDAO = new GenreDAO(connection);
            LogicActionResult logicActionResult = new LogicActionResult();
            if(!genreDAO.checkGenreUniqueness(genreName)) {
                logicActionResult.setState(LogicActionResult.State.FAILURE);
                logicActionResult.setResult(ActionResult.FAILURE_GENRE_NOT_UNIQUE);
            } else {
                logicActionResult.setState(LogicActionResult.State.SUCCESS);
                logicActionResult.setResult(ActionResult.SUCCESS_ADD_GENRE);
                genreDAO.addNewGenre(genreName);
            }
             return logicActionResult;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading all genres in logic.", e);
        }
    }
}
