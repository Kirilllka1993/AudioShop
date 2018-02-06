package by.kazimirov.command.user;

import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Visitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 *
 */
public class ChangeLocaleCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();

    private static final String PARAM_LOCALE = "locale";
    private static final String LOCALE_DELIMITER = "_";
    private static final int INDEX_LANGUAGE = 0;
    private static final int INDEX_COUNTRY = 1;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Visitor visitor = (Visitor) request.getSession().getAttribute(ControllerConstants.VISITOR_KEY);

        String[] chosenLocaleInfo = request.getParameter(PARAM_LOCALE).split(LOCALE_DELIMITER);
        Locale chosenLocale = new Locale(chosenLocaleInfo[INDEX_LANGUAGE], chosenLocaleInfo[INDEX_COUNTRY]);
        if (!chosenLocale.equals(visitor.getLocale())) {
            visitor.setLocale(chosenLocale);
        }
        return visitor.getCurrentPage();
    }
}
