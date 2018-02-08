package by.kazimirov.dao;

import by.kazimirov.entity.Account;
import by.kazimirov.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 *accounts
 */
public class AccountDAO {
    private Connection connection;
    private static final Logger LOG = LogManager.getLogger();
    private static final String SQL_ADD_USER_ACCOUNT = "INSERT INTO `accounts` (`first_name`, `last_name`, `login`, `email`, `password`, `is_admin`) " +
            "VALUES (?, ?, ?, ?, SHA2(?, 256), FALSE)";
    private static final String SQL_CHECK_ACCOUNT = "SELECT `login` FROM `accounts` WHERE (`login` = ? OR `email` = ?) AND `password` = SHA2(?, 256)";
    private static final String SQL_CHECK_LOGIN = "SELECT `login` FROM `accounts` WHERE `login` = ?";
    private static final String SQL_CHECK_EMAIL = "SELECT `login` FROM `accounts` WHERE `email` = ?";
    private static final String SQL_CHECK_ADMIN_RIGHTS = "SELECT `login` FROM `accounts` WHERE (`login` = ? OR `email` = ?) AND `is_admin` = TRUE";
    private static final String SQL_CHECK_EMAIL_BY_ACCOUNT_ID = "SELECT `login` FROM `accounts` WHERE `account_id`=? AND `email` = ?";
    private static final String SQL_CHECK_LOGIN_BY_ACCOUNT_ID = "SELECT `login` FROM `accounts` WHERE `account_id`=? AND `login` = ?";
    private static final String SQL_CHECK_PASSWORD_BY_ACCOUNT_ID = "SELECT `login` FROM `accounts` WHERE `account_id` = ? AND `password` = SHA2(?, 256)";

    private static final String SQL_LOAD_ACCOUNT_BY_AUTHORIZATION_NAME = "SELECT * FROM `accounts` WHERE (`login` = ? OR `email` = ?)";
    private static final String SQL_LOAD_IMAGE = "SELECT `avatar` FROM `accounts` WHERE `account_id` = ?";
    private static final String SQL_LOAD_ALL_ACCOUNTS = "SELECT * FROM `accounts`";
    private static final String SQL_LOAD_ACCOUNT_BY_ID = "SELECT * FROM `accounts` WHERE `account_id` = ?";

    private static final String SQL_UPDATE_LOGIN = "UPDATE `accounts` SET `login` = ? WHERE `account_id` = ?";
    private static final String SQL_UPDATE_AVATAR = "UPDATE `accounts` SET `avatar` = ? WHERE `account_id` = ?";
    private static final String SQL_UPDATE_NAME = "UPDATE `accounts` SET `first_name` = ?, `last_name` = ? WHERE `account_id` = ?";
    private static final String SQL_UPDATE_EMAIL = "UPDATE `accounts` SET `email` = ? WHERE `account_id` = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE `accounts` SET `password` = SHA2(?,256) WHERE `account_id` = ?";

    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ADMIN_RIGHTS = "is_admin";
    private static final String COLUMN_AVATAR = "avatar";

    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    public void registerAccount(String firstName, String lastName, String login, String email, String password) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER_ACCOUNT)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while inserting new account into database.", e);
        }
    }

    public boolean authorizeAccount(String authorizationName, String password) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_ACCOUNT)) {
            preparedStatement.setString(1, authorizationName);
            preparedStatement.setString(2, authorizationName);
            preparedStatement.setString(3, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking account in database.", e);
        }
    }

    public boolean checkLoginUniqueness(String login) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking login uniqueness in database.", e);
        }
    }

    public boolean checkLoginUniqueness(int accountId, String login) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_LOGIN_BY_ACCOUNT_ID)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking login uniqueness by account id in database.", e);
        }
    }

    public boolean checkEmailUniqueness(String email) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking email uniqueness in database.", e);
        }
    }

    public boolean checkEmailUniqueness(int accountId, String email) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_EMAIL_BY_ACCOUNT_ID)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking email uniqueness by account id in database.", e);
        }
    }

    public boolean checkPassword(int accountId, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CHECK_PASSWORD_BY_ACCOUNT_ID)) {
            statement.setInt(1, accountId);
            statement.setString(2, password);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking account password in database.", e);
        }
    }

    public boolean checkAdminRights(String authorizationName) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_ADMIN_RIGHTS)) {
            preparedStatement.setString(1, authorizationName);
            preparedStatement.setString(2, authorizationName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException("Error while checking account admin rights in database.", e);
        }
    }

    public Account findByAuthorizationName(String authorizationName) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_LOAD_ACCOUNT_BY_AUTHORIZATION_NAME)) {
            statement.setString(1, authorizationName);
            statement.setString(2, authorizationName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                account.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
                account.setLastName(resultSet.getString(COLUMN_LAST_NAME));
                account.setLogin(resultSet.getString(COLUMN_LOGIN));
                account.setEmail(resultSet.getString(COLUMN_EMAIL));
                Blob avatar = resultSet.getBlob(COLUMN_AVATAR);
                if (avatar == null) {
                    account.setAvatar(null);
                } else {
                    account.setAvatar(avatar.getBytes(1, (int) avatar.length()));
                }
                account.setAdmin(resultSet.getBoolean(COLUMN_ADMIN_RIGHTS));
                return account;
            } else {
                throw new DAOException("No account with such email or login found in database.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error while searching for account by email or login in database.", e);
        }
    }

    public List<Account> loadAllAccounts() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_LOAD_ALL_ACCOUNTS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Account> accounts = new LinkedList<>();
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                account.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
                account.setLastName(resultSet.getString(COLUMN_LAST_NAME));
                account.setLogin(resultSet.getString(COLUMN_LOGIN));
                account.setEmail(resultSet.getString(COLUMN_EMAIL));
                Blob avatar = resultSet.getBlob(COLUMN_AVATAR);
                if (avatar == null) {
                    account.setAvatar(null);
                } else {
                    account.setAvatar(avatar.getBytes(1, (int) avatar.length()));
                }
                account.setAdmin(resultSet.getBoolean(COLUMN_ADMIN_RIGHTS));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DAOException("Error while searching for account by email or login in database.", e);
        }
    }

    public Account loadAccountById(int accountId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getInt(COLUMN_ACCOUNT_ID));
                account.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
                account.setLastName(resultSet.getString(COLUMN_LAST_NAME));
                account.setLogin(resultSet.getString(COLUMN_LOGIN));
                account.setEmail(resultSet.getString(COLUMN_EMAIL));
                Blob avatar = resultSet.getBlob(COLUMN_AVATAR);
                if (avatar == null) {
                    account.setAvatar(null);
                } else {
                    account.setAvatar(avatar.getBytes(1, (int) avatar.length()));
                }
                account.setAdmin(resultSet.getBoolean(COLUMN_ADMIN_RIGHTS));
                return account;
            } else {
                throw new DAOException("No account with such id found in database.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while searching for account by email or login in database.", e);
        }
    }

    public void updateLogin(int accountId, String login) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_LOGIN)) {
            statement.setString(1, login);
            statement.setInt(2, accountId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while updating account login in database.", e);
        }
    }

    public void updateAvatar(int accountId, byte[] avatar) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_AVATAR)) {
            if (avatar != null) {
                statement.setBlob(1, new ByteArrayInputStream(avatar));
            } else {
                statement.setBlob(1, (Blob) null);
            }
            statement.setInt(2, accountId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while updating account avatar in database.", e);
        }
    }

    public void updateName(int accountId, String firstName, String lastName) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_NAME)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, accountId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while updating account first and last names in database.", e);
        }
    }

    public void updateEmail(int accountId, String email) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_EMAIL)) {
            statement.setString(1, email);
            statement.setInt(2, accountId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while updating account email in database.", e);
        }
    }

    public void updatePassword(int accountId, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(1, password);
            statement.setInt(2, accountId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while updating account password in database.", e);
        }
    }

    public byte[] findImage(int accountId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOAD_IMAGE)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            byte[] avatar = null;
            while (resultSet.next()) {
                avatar = resultSet.getBytes(COLUMN_AVATAR);
            }
            return avatar;
        } catch (SQLException e) {
            throw new DAOException("Error while searching for account avatar in database.", e);
        }
    }

}
