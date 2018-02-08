package by.kazimirov.logic;

import by.kazimirov.dao.AccountDAO;
import by.kazimirov.database.ConnectionPool;
import by.kazimirov.entity.Account;
import by.kazimirov.exception.DAOException;
import by.kazimirov.exception.LogicException;
import by.kazimirov.validator.AccountValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */

public class AccountLogic {
    public AccountLogic() {
    }

    public LogicActionResult registerAccount(String firstName, String lastName, String login, String email, String password, String passwordConfirm) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            LogicActionResult logicActionResult = new LogicActionResult();
            logicActionResult.setState(LogicActionResult.State.FAILURE);
            if (!AccountValidator.validateLogin(login)) {
                logicActionResult.setResult(ActionResult.FAILURE_INVALID_USERNAME);
            } else if (!AccountValidator.validateEmail(email)) {
                logicActionResult.setResult(ActionResult.FAILURE_INVALID_EMAIL);
            } else if (!AccountValidator.validatePassword(password)) {
                logicActionResult.setResult(ActionResult.FAILURE_INVALID_PASSWORD);
            } else if (!AccountValidator.checkPasswordsMatch(password, passwordConfirm)) {
                logicActionResult.setResult(ActionResult.FAILURE_PASSWORDS_NOT_EQUALS);
            } else if (!accountDAO.checkLoginUniqueness(login)) {
                logicActionResult.setResult(ActionResult.FAILURE_USERNAME_NOT_UNIQUE);
            } else if (!accountDAO.checkEmailUniqueness(email)) {
                logicActionResult.setResult(ActionResult.FAILURE_EMAIL_NOT_UNIQUE);
            } else {
                logicActionResult.setState(LogicActionResult.State.SUCCESS);
                logicActionResult.setResult(ActionResult.SUCCESS_REGISTER);
                accountDAO.registerAccount(firstName, lastName, login, email, password);
            }
            return logicActionResult;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while account registration.", e);
        }
    }

    public boolean authorizeAccount(String authorizationName, String password) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            if (AccountValidator.validateAuthorization(authorizationName, password)) {
                return accountDAO.authorizeAccount(authorizationName, password);
            } else {
                return false;
            }
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while account authorization.", e);
        }
    }

    public Account loadAccount(String authorizationName) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            return accountDAO.findByAuthorizationName(authorizationName);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while account authorization.", e);
        }
    }

    public Account loadAccount(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            return accountDAO.loadAccountById(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while account authorization.", e);
        }
    }

    public List<Account> loadAllAccounts() throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            return accountDAO.loadAllAccounts();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while account authorization.", e);
        }
    }

    public void changeName(int accountId, String firstName, String lastName) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            accountDAO.updateName(accountId, firstName, lastName);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while changing account first name and last name");
        }
    }

    public LogicActionResult changeLogin(int accountId, String login) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            LogicActionResult logicActionResult = new LogicActionResult();
            logicActionResult.setState(LogicActionResult.State.FAILURE);
            if (!AccountValidator.validateLogin(login)) {
                logicActionResult.setResult(ActionResult.FAILURE_INVALID_USERNAME);
            } else if (!accountDAO.checkLoginUniqueness(accountId, login)) {
                logicActionResult.setResult(ActionResult.FAILURE_USERNAME_NOT_UNIQUE);
            } else {
                logicActionResult.setState(LogicActionResult.State.SUCCESS);
                logicActionResult.setResult(ActionResult.SUCCESS_CHANGE_USERNAME);
                accountDAO.updateLogin(accountId, login);
            }
            return logicActionResult;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while changing account login.", e);
        }
    }

    public LogicActionResult changeEmail(int accountId, String email) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            LogicActionResult logicActionResult = new LogicActionResult();
            logicActionResult.setState(LogicActionResult.State.FAILURE);
            if (!AccountValidator.validateEmail(email)) {
                logicActionResult.setResult(ActionResult.FAILURE_INVALID_EMAIL);
            } else if (!accountDAO.checkEmailUniqueness(accountId, email)) {
                logicActionResult.setResult(ActionResult.FAILURE_EMAIL_NOT_UNIQUE);
            } else {
                logicActionResult.setState(LogicActionResult.State.SUCCESS);
                logicActionResult.setResult(ActionResult.SUCCESS_CHANGE_EMAIL);
                accountDAO.updateEmail(accountId, email);
            }
            return logicActionResult;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while changing account email.", e);
        }
    }

    public LogicActionResult changePassword(int accountId, String oldPassword, String newPassword, String newPasswordConfirm) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            LogicActionResult logicActionResult = new LogicActionResult();
            logicActionResult.setState(LogicActionResult.State.FAILURE);
            if (!AccountValidator.validatePassword(oldPassword)) {
                logicActionResult.setResult(ActionResult.FAILURE_INVALID_PASSWORD);
            } else if (!accountDAO.checkPassword(accountId, oldPassword)) {
                logicActionResult.setResult(ActionResult.FAILURE_NOT_OLD_PASSWORD);
            } else if (!AccountValidator.validatePassword(newPassword)) {
                logicActionResult.setResult(ActionResult.FAILURE_INVALID_NEW_PASSWORD);
            } else if (AccountValidator.checkPasswordsMatch(oldPassword, newPassword)) {
                logicActionResult.setResult(ActionResult.FAILURE_NOT_NEW_UNIQUE_PASSWORD);
            } else if (!AccountValidator.checkPasswordsMatch(newPassword, newPasswordConfirm)) {
                logicActionResult.setResult(ActionResult.FAILURE_PASSWORDS_NOT_EQUALS);
            } else {
                logicActionResult.setState(LogicActionResult.State.SUCCESS);
                logicActionResult.setResult(ActionResult.SUCCESS_CHANGE_PASSWORD);
                accountDAO.updatePassword(accountId, newPassword);
            }
            return logicActionResult;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while changing account password.", e);
        }
    }

    public boolean isAdmin(String authorizationName) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            return accountDAO.checkAdminRights(authorizationName);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while admin rights check in logic.", e);
        }
    }

    public void changeAvatar(int accountId, byte[] avatar) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            accountDAO.updateAvatar(accountId, avatar);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while changing account avatar.", e);
        }
    }

    public byte[] loadImage(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            return accountDAO.findImage(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Error while loading image.", e);
        }
    }

}
