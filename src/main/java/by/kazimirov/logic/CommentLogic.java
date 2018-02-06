package by.kazimirov.logic;

import by.kazimirov.ajax.BiTuple;
import by.kazimirov.dao.CommentDAO;
import by.kazimirov.database.ConnectionPool;
import by.kazimirov.entity.Comment;
import by.kazimirov.exception.DAOException;
import by.kazimirov.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class CommentLogic {
    public BiTuple<LogicActionResult, Integer> createComment(int trackId, int accountId, String text) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            CommentDAO commentDAO = new CommentDAO(connection);
            int commentId = commentDAO.addNewComment(trackId, accountId, text);
            LogicActionResult logicActionResult = new LogicActionResult();
            logicActionResult.setState(LogicActionResult.State.SUCCESS);
            logicActionResult.setResult(ActionResult.SUCCESS_ADD_COMMENT);
            return new BiTuple<>(logicActionResult, commentId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while creating comment in logic.", e);
        }
    }

    public Comment loadComment(int commentId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            CommentDAO commentDAO = new CommentDAO(connection);
            return commentDAO.loadComment(commentId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading comment in logic.", e);
        }
    }
    public List<Comment> loadAllTrackComments(int trackId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            CommentDAO commentDAO = new CommentDAO(connection);
            return commentDAO.loadAllTrackComments(trackId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading comment in logic.", e);
        }
    }

    public List<Comment> loadAllComments() throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            CommentDAO commentDAO = new CommentDAO(connection);
            return commentDAO.loadAllComments();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading comment in logic.", e);
        }
    }
}

