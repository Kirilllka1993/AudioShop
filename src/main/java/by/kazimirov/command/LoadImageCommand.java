package by.kazimirov.command;

import by.kazimirov.controller.ControllerConfiguration;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.exception.LogicException;
import by.kazimirov.logic.AccountLogic;
import by.kazimirov.logic.AlbumLogic;
import by.kazimirov.logic.ArtistLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class LoadImageCommand  extends AbstractServletCommand {
    private static  final Logger LOG = LogManager.getLogger();
    private static final String PARAM_ELEMENT_ID = "elementId";
    private static final String PARAM_TARGET = "target";
    private static final String ALBUM_IMAGE = "album";
    private static final String ARTIST_IMAGE = "artist";
    private static final String ACCOUNT_IMAGE = "account";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(ControllerConstants.CONTROLLER_CONFIG_KEY);

        int elementId = Integer.parseInt(request.getParameter(PARAM_ELEMENT_ID));
        String imageTarget = request.getParameter(PARAM_TARGET);
        byte[] image = null;

        String resultData;
        try {
            switch (imageTarget.toLowerCase()) {
                case ALBUM_IMAGE:
                    AlbumLogic albumLogic = new AlbumLogic();
                    image = albumLogic.loadImage(elementId);
                    break;
                case ARTIST_IMAGE:
                    ArtistLogic artistLogic = new ArtistLogic();
                    image = artistLogic.loadImage(elementId);
                    break;
                case ACCOUNT_IMAGE:
                    AccountLogic accountLogic = new AccountLogic();
                    image = accountLogic.loadImage(elementId);
                    break;
            }
            response.setContentType(CommandConstants.MIME_TYPE_IMAGE_JPG);
            controllerConfiguration.setState(ControllerConfiguration.State.RESPONSE);
            resultData = Base64.getEncoder().encodeToString(image);
        } catch (LogicException e) {
            LOG.error("Errors during load image bytes.", e);
            resultData = handleDBError(e, request, response);
        }
        return resultData;
    }
}
