package by.kazimirov.manager;

/**
 *
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigurationManager {

    private static final Logger LOG = LogManager.getLogger();

    private static final String CONFIGURATION_FILE_PATH = "properties.configuration";

    private static  ConfigurationManager configurationManager = new ConfigurationManager();
    private ResourceBundle resourceBundle;

    private ConfigurationManager() {
        try {
            resourceBundle = ResourceBundle.getBundle(CONFIGURATION_FILE_PATH);
        } catch (MissingResourceException e) {
            LOG.fatal("Can't load configuration property file.", e);
            throw new RuntimeException("Can't load configuration property file.");
        }
    }

    public static String getProperty(String key) {
        return configurationManager.resourceBundle.getString(key);
    }
}
