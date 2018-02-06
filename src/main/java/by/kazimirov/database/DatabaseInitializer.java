package by.kazimirov.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
class DatabaseInitializer {

    private static final Logger LOG = LogManager.getLogger();

    final String DRIVER;
    final String URL;
    final String LOGIN;
    final int POOL_SIZE;
    final String PASSWORD;

    DatabaseInitializer() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.database");
            DRIVER = resourceBundle.getString("db.driver");
            URL = resourceBundle.getString("db.url");
            LOGIN = resourceBundle.getString("db.login");
            POOL_SIZE = Integer.valueOf(resourceBundle.getString("db.poolsize"));
            if (POOL_SIZE < 1) {
                LOG.fatal("Pool size is null.");
                throw new RuntimeException("Pool size can't be non positive.");
            }
            PASSWORD = resourceBundle.getString("db.password");
        } catch (NumberFormatException | MissingResourceException e) {
            LOG.fatal("Cannot initialize the database connection.", e);
            throw new RuntimeException("Cannot initialize the database connection.", e);
        }
    }
}
