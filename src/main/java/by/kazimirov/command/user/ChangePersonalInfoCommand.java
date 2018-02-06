package by.kazimirov.command.user;

import by.kazimirov.ajax.AJAXState;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.entity.Account;
import by.kazimirov.entity.Visitor;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.AccountLogic;
import by.kazimirov.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kazimirov.command.CommandConstants.*;
import static by.kazimirov.controller.ControllerConstants.VISITOR_KEY;

/**
 *
 */
public class ChangePersonalInfoCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String FIRST_NAME_PARAM = "firstName";
    private static final String LAST_NAME_PARAM = "lastName";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Visitor visitor = (Visitor) request.getSession().getAttribute(VISITOR_KEY);
        String firstName = request.getParameter(FIRST_NAME_PARAM);
        String lastName = request.getParameter(LAST_NAME_PARAM);

        AccountLogic accountLogic = new AccountLogic();
        Account account = (Account) request.getSession().getAttribute(ATTR_ACCOUNT);

        String resultData;
        try {
            accountLogic.changeName(account.getAccountId(), firstName, lastName);
            account.setFirstName(firstName);
            account.setLastName(lastName);
            resultData = toJson(AJAXState.OK, MessageManager.getProperty(CommandConstants.MESSAGE_SUCCESS_SAVE_CHANGES, visitor.getLocale()));
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors during changing avatar..", e);
            resultData = handleDBError(e, request, response);
        }
        return resultData;
    }
}
