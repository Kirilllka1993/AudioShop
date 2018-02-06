package by.kazimirov.command.user;

import by.kazimirov.ajax.AJAXState;
import by.kazimirov.ajax.BiTuple;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.controller.ControllerConfiguration;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Track;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.TrackLogic;
import by.kazimirov.manager.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 *
 */
public class AddToCartCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_TRACK_ID = "trackId";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int trackId = Integer.parseInt(request.getParameter(PARAM_TRACK_ID));
        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(ControllerConstants.CONTROLLER_CONFIG_KEY);

        TrackLogic trackLogic = new TrackLogic();
        String resultData = null;

        if (controllerConfiguration.getState() != ControllerConfiguration.State.AJAX) {
            resultData = ConfigurationManager.getProperty(CommandConstants.PAGE_USER_MAIN);
        } else {
            if (controllerConfiguration.getState() == ControllerConfiguration.State.AJAX) {
                try {
                    BiTuple<AJAXState, Object> data;
                    Track track = trackLogic.loadTrackById(trackId);
                    List<Track> cart = (List<Track>) request.getSession().getAttribute(CommandConstants.ATTR_CART_ITEMS);
                    if (!cart.contains(track)) {
                        cart.add(track);
                    }
                    data = new BiTuple<>(AJAXState.OK, null);
                    response.setContentType(CommandConstants.MIME_TYPE_JSON);
                    resultData = toJson(data);
                } catch (LogicException e) {
                    LOG.log(Level.ERROR, "Errors during adding track to cart.", e);
                    resultData = handleDBError(e, request, response);
                }
            }
        }

        return resultData;
    }
}
