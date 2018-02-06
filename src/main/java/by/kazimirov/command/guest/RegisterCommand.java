package by.kazimirov.command.guest;

import by.kazimirov.ajax.AJAXState;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.controller.ControllerConfiguration;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Visitor;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.AccountLogic;
import by.kazimirov.logic.LogicActionResult;
import by.kazimirov.manager.ConfigurationManager;
import by.kazimirov.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static by.kazimirov.controller.ControllerConstants.VISITOR_KEY;

/**
 *
 */
public class RegisterCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_PASSWORD_CONFIRM = "passwordConfirm";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String email = request.getParameter(PARAM_EMAIL);
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        String passwordConfirm = request.getParameter(PARAM_PASSWORD_CONFIRM);

        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(ControllerConstants.CONTROLLER_CONFIG_KEY);
        Visitor visitor = (Visitor) request.getSession().getAttribute(VISITOR_KEY);
        AccountLogic logic = new AccountLogic();
        String resultData;
        if (controllerConfiguration.getState() != ControllerConfiguration.State.AJAX) {
            resultData = ConfigurationManager.getProperty(CommandConstants.PAGE_REGISTRATION);
        } else {
            try {
                LogicActionResult registrationResult = logic.registerAccount(firstName, lastName, login, email, password, passwordConfirm);
                setResultMessage(registrationResult, visitor.getLocale());
                response.setContentType(CommandConstants.MIME_TYPE_JSON);
                AJAXState state = AJAXState.HANDLE;
                resultData = toJson(state, registrationResult);
            } catch (LogicException e) {
                LOG.log(Level.ERROR, "Errors during register operation.", e);
                resultData = handleDBError(e, request, response);
            }
        }

        return resultData;
    }

    private void setResultMessage(LogicActionResult registrationResult, Locale locale) {
        switch (registrationResult.getResult()) {
            case FAILURE_INVALID_USERNAME:
                registrationResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_INVALID_LOGIN, locale));
                registrationResult.setTarget(PARAM_LOGIN);
                break;
            case FAILURE_INVALID_EMAIL:
                registrationResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_INVALID_EMAIL, locale));
                registrationResult.setTarget(PARAM_EMAIL);
                break;
            case FAILURE_INVALID_PASSWORD:
                registrationResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_INVALID_PASSWORD, locale));
                registrationResult.setTarget(PARAM_EMAIL);
                break;
            case FAILURE_PASSWORDS_NOT_EQUALS:
                registrationResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_PASSWORDS_NOT_EQUALS, locale));
                registrationResult.setTarget(PARAM_PASSWORD_CONFIRM);
                break;
            case FAILURE_USERNAME_NOT_UNIQUE:
                registrationResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_LOGIN_NOT_UNIQUE, locale));
                registrationResult.setTarget(PARAM_LOGIN);
                break;
            case FAILURE_EMAIL_NOT_UNIQUE:
                registrationResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_EMAIL_NOT_UNIQUE, locale));
                registrationResult.setTarget(PARAM_EMAIL);
                break;
            case SUCCESS_REGISTER:
                registrationResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_SUCCESS_REGISTER, locale));
                break;
            default:
        }
    }
}
