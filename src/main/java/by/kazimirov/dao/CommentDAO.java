package by.kazimirov.dao;

import by.kazimirov.entity.Comment;
import by.kazimirov.exception.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class CommentDAO {
    private Connection connection;
    private static final String COLUMN_COMMENT_ID = "comment_id";
    private static final String COLUMN_TRACK_ID = "track_id";
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_POST_DATE = "post_date";

    private static final String SQL_LOAD_ALL_TRACK_COMMENTS = "SELECT * FROM `comments` WHERE `track_id` = ?";
    private static final String SQL_LOAD_ALL_COMMENTS = "SELECT * FROM `comments`";
    private static final String SQL_LOAD_COMMENT = "SELECT * FROM `comments` WHERE `comment_id` = ?";
    private static final String SQL_ADD_COMMENT = "INSERT INTO `comments` (`track_id`, `account_id`, `text`, `post_date`) VALUES(?,?,?, NOW())";


    public CommentDAO(Connection connection) {
        this.connection = connection;
    }

    public int addNewComment(int trackId, int accountId, String text) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COMMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, trackId);
            preparedStatement.setInt(2, accountId);
            preparedStatement.setString(3, text);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            } else throw new DAOException("Error while inserting new comment to database.");
        } catch (SQLException e) {
            throw new DAOException("Error while inserting new comment to database.", e);
        }
    }

    public List<Comment> loadAllTrackComments(int trackId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALL_TRACK_COMMENTS)) {
            preparedStatement.setInt(1, trackId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Comment> commentList = new LinkedList<>();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setCommentId(resultSet.getInt(COLUMN_COMMENT_ID));
                comment.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                comment.setTrackId(resultSet.getInt(COLUMN_TRACK_ID));
                comment.setText(resultSet.getString(COLUMN_TEXT));
                comment.setDateTime(resultSet.getTimestamp(COLUMN_POST_DATE).toLocalDateTime());
                commentList.add(comment);
            }
            return commentList;
        } catch (SQLException e) {
            throw new DAOException("Error while selecting all comments for track from database.", e);
        }
    }

    public List<Comment> loadAllComments() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALL_COMMENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Comment> commentList = new LinkedList<>();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setCommentId(resultSet.getInt(COLUMN_COMMENT_ID));
                comment.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                comment.setTrackId(resultSet.getInt(COLUMN_TRACK_ID));
                comment.setText(resultSet.getString(COLUMN_TEXT));
                comment.setDateTime(resultSet.getTimestamp(COLUMN_POST_DATE).toLocalDateTime());
                commentList.add(comment);
            }
            return commentList;
        } catch (SQLException e) {
            throw new DAOException("Error while selecting all comments from database.", e);
        }
    }

    public Comment loadComment(int commentId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_COMMENT)) {
            preparedStatement.setInt(1, commentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Comment comment = new Comment();
                comment.setCommentId(commentId);
                comment.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                comment.setTrackId(resultSet.getInt(COLUMN_TRACK_ID));
                comment.setText(resultSet.getString(COLUMN_TEXT));
                comment.setDateTime(resultSet.getTimestamp(COLUMN_POST_DATE).toLocalDateTime());
                return comment;
            } else {
                throw new DAOException("No comment with such id in database.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while adding new comment to database.", e);
        }
    }
}
