package by.kazimirov.command.user;

import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.entity.Account;
import by.kazimirov.entity.Visitor;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.AccountLogic;
import by.kazimirov.manager.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static by.kazimirov.command.CommandConstants.*;
import static by.kazimirov.controller.ControllerConstants.VISITOR_KEY;

/**
 *
 */
public class ChangeAvatarCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();
    static final String PARAM_AVATAR = "avatar";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Visitor visitor = (Visitor) request.getSession().getAttribute(VISITOR_KEY);
        Account account = (Account) request.getSession().getAttribute(ATTR_ACCOUNT);
        AccountLogic logic = new AccountLogic();
        String resultData;

        int fileSize;
        byte[] avatar = null;
        try {
            Part imagePart = request.getPart(PARAM_AVATAR);
            fileSize = (int) imagePart.getSize();
            if (fileSize != 0) {
                avatar = new byte[fileSize];
                imagePart.getInputStream().read(avatar, 0, fileSize);
            }
        } catch (IOException | ServletException e) {
            LOG.log(Level.ERROR, "Errors during uploading avatar.", e);
        }

        try {
            logic.changeAvatar(account.getAccountId(), avatar);
            account.setAvatar(avatar);
            resultData = ConfigurationManager.getProperty(CommandConstants.PAGE_SETTINGS);
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors during changing avatar..", e);
            resultData = handleDBError(e, request, response);
        }

        return resultData;
    }
}
