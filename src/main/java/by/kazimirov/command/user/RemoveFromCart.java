package by.kazimirov.command.user;

import by.kazimirov.ajax.AJAXState;
import by.kazimirov.ajax.BiTuple;
import by.kazimirov.command.AbstractServletCommand;
import by.kazimirov.command.CommandConstants;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.entity.Track;
import by.kazimirov.entity.Visitor;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.TrackLogic;
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
public class RemoveFromCart extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_TRACK_ID = "trackId";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int trackId = Integer.parseInt(request.getParameter(PARAM_TRACK_ID));
        Visitor visitor = (Visitor) request.getSession().getAttribute(ControllerConstants.VISITOR_KEY);

        TrackLogic trackLogic = new TrackLogic();
        String resultData;

        try {
            BiTuple<AJAXState, Object> data;
            Track track = trackLogic.loadTrackById(trackId);
            List<Track> cartItems = (List<Track>) request.getSession().getAttribute(CommandConstants.ATTR_CART_ITEMS);
            cartItems.remove(track);
            resultData = visitor.getCurrentPage();
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors while removing track from cart.", e);
            resultData = handleDBError(e, request, response);
        }
        return resultData;
    }
}
