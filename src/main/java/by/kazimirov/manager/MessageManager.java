package by.kazimirov.manager;

import by.kazimirov.controller.ControllerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
public class MessageManager {

    private static final Logger LOG = LogManager.getLogger();

    private static final String MESSAGE_FILE_PATH = "properties.message";

    private static MessageManager messageManager = new MessageManager();
    private ResourceBundle resourceBundle;
    private Locale currentLocale = ControllerConstants.DEFAULT_LOCALE;

    private MessageManager() {
        try {
            resourceBundle = ResourceBundle.getBundle(MESSAGE_FILE_PATH, ControllerConstants.DEFAULT_LOCALE);
        } catch (MissingResourceException e) {
            LOG.fatal("Can't load content property file.", e);
            throw new RuntimeException("Can't load content property file.");
        }
    }

    public static String getProperty(String key, Locale locale) {
        if (locale != null) {
            if (!messageManager.currentLocale.equals(locale)) {
                messageManager.resourceBundle = ResourceBundle.getBundle(MESSAGE_FILE_PATH, locale);
                messageManager.currentLocale = locale;
            }
        } else {
            messageManager.resourceBundle = ResourceBundle.getBundle(MESSAGE_FILE_PATH, ControllerConstants.DEFAULT_LOCALE);
            messageManager.currentLocale = ControllerConstants.DEFAULT_LOCALE;
        }
        return messageManager.resourceBundle.getString(key);
    }

}