package by.kazimirov.logic;

import by.kazimirov.dao.PurchaseDAO;
import by.kazimirov.database.ConnectionPool;
import by.kazimirov.entity.Purchase;
import by.kazimirov.exception.DAOException;
import by.kazimirov.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class PurchaseLogic {

    public LogicActionResult addPurchase(int accountId, List<Integer> tracksId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            LogicActionResult result = new LogicActionResult();
            if(tracksId.isEmpty()){
                result.setState(LogicActionResult.State.FAILURE);
                result.setResult(ActionResult.FAILURE_EMPTY_CART);
            } else {
                PurchaseDAO purchaseDAO = new PurchaseDAO(connection);
                int purchaseId = purchaseDAO.addPurchase(accountId);
                purchaseDAO.fillPurchaseM2mTracks(purchaseId, tracksId);
                result.setState(LogicActionResult.State.SUCCESS);
                result.setResult(ActionResult.SUCCESS_PURCHASE);
            }
            return result;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while adding new purchase.", e);
        }
    }

    public List<Purchase> loadAllPurchases(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
           PurchaseDAO purchaseDAO = new PurchaseDAO(connection);
           return purchaseDAO.loadAccountPurchases(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while adding new purchase.", e);
        }
    }

    public Purchase loadPurchaseById(int purchaseId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PurchaseDAO purchaseDAO = new PurchaseDAO(connection);
            return purchaseDAO.findPurchaseById(purchaseId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while adding new purchase.", e);
        }
    }
}
