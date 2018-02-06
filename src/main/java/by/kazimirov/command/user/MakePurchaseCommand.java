package by.kazimirov.command.user;

import by.kazimirov.ajax.AJAXState;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.controller.ControllerConfiguration;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Account;
import by.kazimirov.entity.Track;
import by.kazimirov.entity.Visitor;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.LogicActionResult;
import by.kazimirov.logic.PurchaseLogic;
import by.kazimirov.manager.ConfigurationManager;
import by.kazimirov.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 *
 */
public class MakePurchaseCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Track> cartItems = (List<Track>) request.getSession().getAttribute(CommandConstants.ATTR_CART_ITEMS);
        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(ControllerConstants.CONTROLLER_CONFIG_KEY);
        Visitor visitor = (Visitor) request.getSession().getAttribute(ControllerConstants.VISITOR_KEY);
        Account account = (Account) request.getSession().getAttribute(CommandConstants.ATTR_ACCOUNT);
        PurchaseLogic purchaseLogic = new PurchaseLogic();
        String resultData;

        if (controllerConfiguration.getState() != ControllerConfiguration.State.AJAX) {
            resultData = ConfigurationManager.getProperty(CommandConstants.PAGE_CART);
        } else {
            try {
                List<Integer> tracksId = cartItems.stream().map(Track::getTrackId).collect(Collectors.toList());
                LogicActionResult result = purchaseLogic.addPurchase(account.getAccountId(), tracksId);
                response.setContentType(CommandConstants.MIME_TYPE_JSON);
                setResultMessage(result, visitor.getLocale());
                if (result.getState() == LogicActionResult.State.SUCCESS) {
                    cartItems.clear();
                    resultData = toJson(AJAXState.HANDLE, result);
                } else {
                    resultData = toJson(AJAXState.HANDLE, result);
                }
            } catch (LogicException e) {
                LOG.log(Level.ERROR, "Errors while adding new purchase.", e);
                resultData = handleDBError(e, request, response);
            }
        }

        return resultData;
    }

    private void setResultMessage(LogicActionResult makePurchaseResult, Locale locale) {
        switch (makePurchaseResult.getResult()) {
            case SUCCESS_PURCHASE:
                makePurchaseResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_SUCCESS_MAKE_PURCHASE, locale));
                break;
            case FAILURE_EMPTY_CART:
                makePurchaseResult.setMessage(MessageManager.getProperty(CommandConstants.MESSAGE_FAILURE_EMPTY_SHOPPING_CART, locale));
                break;
        }
    }
}
