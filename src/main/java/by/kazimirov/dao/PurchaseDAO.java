package by.kazimirov.dao;

import by.kazimirov.entity.Purchase;
import by.kazimirov.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class PurchaseDAO {
    private Connection connection;
    private static final String SQL_FILL_PURCHASE_M2M_TRACKS_TABLE = "INSERT INTO `purchases_m2m_tracks` (`purchase_id`, `track_id`) VALUES (?, ?)";
    private static final String SQL_ADD_PURCHASE = "INSERT INTO `purchases` (`account_id`, `date`) VALUES (?, NOW())";
    private static final String SQL_LOAD_PURCHASE_BY_ID = "SELECT * FROM `purchases` WHERE `purchase_id` = ?";
    private static final String SQL_LOAD_ALL_PURCHASES = "SELECT * FROM `purchases` WHERE  `account_id` = ?";
    private static final String SQL_LOAD_TRACKS_ID = "SELECT `track_id` FROM `purchases_m2m_tracks` WHERE `purchase_id` = ?";
    private static final String SQL_CALCULATE_TOTAL_PRICE = "SELECT SUM(track_price) FROM tracks\n" +
            "  LEFT JOIN purchases_m2m_tracks ON tracks.track_id = purchases_m2m_tracks.track_id\n" +
            "  LEFT JOIN purchases ON purchases_m2m_tracks.purchase_id = purchases.purchase_id WHERE purchases.purchase_id=?";
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private static final String COLUMN_PURCHASE_ID = "purchase_id";
    private static final String COLUMN_TRACK_ID = "track_id";
    private static final String COLUMN_DATE = "date";

    public PurchaseDAO(Connection connection) {
        this.connection = connection;
    }

    public int addPurchase(int accountId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_PURCHASE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DAOException("Error while adding purchase to database.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while adding purchase to database.", e);
        }
    }

    public void fillPurchaseM2mTracks(int purchaseId, List<Integer> tracksId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FILL_PURCHASE_M2M_TRACKS_TABLE)) {
            for (int i = 0; i < tracksId.size(); i++) {
                preparedStatement.setInt(1, purchaseId);
                preparedStatement.setInt(2, tracksId.get(i));
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while filling purchase_m2m_tracks table.", e);
        }
    }

    public Purchase findPurchaseById(int purchaseId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_PURCHASE_BY_ID)) {
            preparedStatement.setInt(1, purchaseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setPurchaseId(purchaseId);
                purchase.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                purchase.setTracksId(loadPurchaseTracksId(purchaseId));
                purchase.setTotalPrice(calculateTotalPrice(purchaseId));
                purchase.setDate(resultSet.getTimestamp(COLUMN_DATE).toLocalDateTime());
                return purchase;
            } else {
                throw new DAOException("No purchase with such id found in database.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error while searching for purchase by id in database.", e);
        }
    }

    public List<Purchase> loadAccountPurchases(int accountId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ALL_PURCHASES)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> purchases = new LinkedList<>();
            while (resultSet.next()) {
                Purchase purchase = new Purchase();
                int purchaseId = resultSet.getInt(COLUMN_PURCHASE_ID);
                purchase.setPurchaseId(purchaseId);
                purchase.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                purchase.setTracksId(loadPurchaseTracksId(purchaseId));
                purchase.setTotalPrice(calculateTotalPrice(purchaseId));
                purchase.setDate(resultSet.getTimestamp(COLUMN_DATE).toLocalDateTime());
                purchases.add(purchase);
            }
            return purchases;
        } catch (SQLException e) {
            throw new DAOException("Error while selecting all account purchases from database.", e);
        }
    }

    public List<Integer> loadPurchaseTracksId(int purchaseId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_TRACKS_ID)) {
            preparedStatement.setInt(1, purchaseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> tracksId = new ArrayList<>();
            while (resultSet.next()) {
                tracksId.add(resultSet.getInt(COLUMN_TRACK_ID));
            }
            return tracksId;
        } catch (SQLException e) {
            throw new DAOException("Error while selecting purchase tracks id from database.", e);
        }
    }

    public double calculateTotalPrice(int purchaseId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CALCULATE_TOTAL_PRICE)) {
            preparedStatement.setInt(1, purchaseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            double totalPrice = 0;
            if(resultSet.next()) {
                totalPrice = resultSet.getDouble(1);
            }
            return totalPrice;
        } catch (SQLException e) {
            throw new DAOException("Error while calculating purchase total price.", e);
        }
    }
}
