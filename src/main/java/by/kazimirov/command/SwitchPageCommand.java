package by.kazimirov.command;

import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.TrackLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.kazimirov.command.CommandConstants.ATTR_POPULAR_TRACKS_ON_PAGE;

/**
 *
 */
public class SwitchPageCommand extends AbstractServletCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_CURRENT_PAGE_NUMBER = "pageNumber";
    private static final String PARAM_TO_FIRST_PAGE = "toFirstPage";
    private static final String PARAM_TO_LAST_PAGE = "toLastPage";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean toFirstPage = Boolean.parseBoolean(request.getParameter(PARAM_TO_FIRST_PAGE));
        boolean toLastPage = Boolean.parseBoolean(request.getParameter(PARAM_TO_LAST_PAGE));
        int pageAmount = (int) request.getSession().getAttribute(CommandConstants.ATTR_PAGE_AMOUNT);

        int pageNumber;
        if (toFirstPage) {
            pageNumber = 0;
        } else if (toLastPage) {
            pageNumber = pageAmount - 1;
        } else {
            pageNumber = Integer.parseInt(request.getParameter(PARAM_CURRENT_PAGE_NUMBER)) - 1;
        }

        String resultData = "";
        try {
            TrackLogic trackLogic = new TrackLogic();
            request.getSession().setAttribute(ATTR_POPULAR_TRACKS_ON_PAGE, trackLogic.loadPopularTracks(pageNumber * CommandConstants.POPULAR_TRACKS_PER_PAGE, CommandConstants.POPULAR_TRACKS_PER_PAGE));
        } catch (LogicException e) {
            LOG.error("Errors while switch page operation.", e);
            resultData = handleDBError(e, request, response);
        }

        return resultData;
    }
}
