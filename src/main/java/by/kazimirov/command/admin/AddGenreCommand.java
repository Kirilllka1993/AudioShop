package by.kazimirov.command.admin;

import by.kazimirov.ajax.AJAXState;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.controller.ControllerConfiguration;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Visitor;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.GenreLogic;
import by.kazimirov.logic.LogicActionResult;
import by.kazimirov.manager.ConfigurationManager;
import by.kazimirov.manager.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static by.kazimirov.controller.ControllerConstants.VISITOR_KEY;

/**
 *
 */
public class AddGenreCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_GENRE_NAME = "genreName";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String genreName = request.getParameter(PARAM_GENRE_NAME);
        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(ControllerConstants.CONTROLLER_CONFIG_KEY);
        Visitor visitor = (Visitor) request.getSession().getAttribute(VISITOR_KEY);
        String resultData;
        GenreLogic genreLogic = new GenreLogic();
        if (controllerConfiguration.getState() != ControllerConfiguration.State.AJAX) {
            resultData = ConfigurationManager.getProperty(CommandConstants.PAGE_CREATE);
        } else {
            try {
                LogicActionResult registrationResult = genreLogic.addGenre(genreName);
                setResultMessage(registrationResult, visitor.getLocale());
                response.setContentType(CommandConstants.MIME_TYPE_JSON);
                resultData = toJson(AJAXState.HANDLE, registrationResult);
            } catch (LogicException e) {
                LOG.error("Errors while creating new genre.", e);
                resultData = handleDBError(e, request, response);
            }
        }
        return resultData;
    }

    private void setResultMessage(LogicActionResult addGenreResult, Locale locale) {
        switch (addGenreResult.getResult()) {
            case FAILURE_GENRE_NOT_UNIQUE:
                addGenreResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_GENRE_NOT_UNIQUE, locale));
                addGenreResult.setTarget(PARAM_GENRE_NAME);
            case SUCCESS_ADD_GENRE:
                addGenreResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_SUCCESS_ADD_GENRE, locale));
                break;
            default:
        }
    }
}
