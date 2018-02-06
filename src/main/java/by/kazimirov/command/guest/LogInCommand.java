package by.kazimirov.command.guest;

import by.kazimirov.ajax.AJAXState;
import by.kazimirov.ajax.BiTuple;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.controller.ControllerConfiguration;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Account;
import by.kazimirov.entity.Visitor;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.AccountLogic;
import by.kazimirov.manager.ConfigurationManager;
import by.kazimirov.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class LogInCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();

    private static final String PARAM_AUTHORIZATION_NAME = "authorizationName";
    private static final String PARAM_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String emailOrLogin = request.getParameter(PARAM_AUTHORIZATION_NAME);
        String password = request.getParameter(PARAM_PASSWORD);

        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(ControllerConstants.CONTROLLER_CONFIG_KEY);
        Visitor visitor = (Visitor) request.getSession().getAttribute(ControllerConstants.VISITOR_KEY);
        AccountLogic accountLogic = new AccountLogic();
        String resultData;
        if (controllerConfiguration.getState() != ControllerConfiguration.State.AJAX) {
            resultData = ConfigurationManager.getProperty(CommandConstants.PAGE_REGISTRATION);
            request.setAttribute(PARAM_AUTHORIZATION_NAME,   emailOrLogin);
            request.setAttribute(PARAM_PASSWORD, password);
        } else {
            try {
                BiTuple<AJAXState, Object> data;
                if (accountLogic.authorizeAccount(emailOrLogin, password)) {
                    Account account = accountLogic.loadAccount(emailOrLogin);
                    if (account.isAdmin()) {
                        visitor.setRole(Visitor.Role.ADMIN);
                    } else {
                        visitor.setRole(Visitor.Role.USER);
                    }
                    request.getSession().setAttribute(CommandConstants.ATTR_ACCOUNT, account);
                    data = new BiTuple<>(AJAXState.LOCATION_REDIRECT, request.getContextPath() + ConfigurationManager.getProperty(CommandConstants.PAGE_USER_MAIN));
                } else {
                    data = new BiTuple<>(AJAXState.HANDLE, MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_LOGIN, visitor.getLocale()));
                }
                response.setContentType(CommandConstants.MIME_TYPE_JSON);
                resultData = toJson(data);
            } catch (LogicException e) {
                LOG.log(Level.ERROR, "Errors during log on operation.", e);
                resultData = handleDBError(e, request, response);
            }
        }
        return resultData;
    }
}
