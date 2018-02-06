package by.kazimirov.command;

import by.kazimirov.controller.ControllerConfiguration;
import by.kazimirov.controller.ControllerConstants;
import by.kazimirov.manager.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 *
 */
public class LoadTrackCommand extends AbstractServletCommand {
    private static  final Logger LOG = LogManager.getLogger();
    private static final String PARAM_TRACK_ID = "trackId";
    private static final String PARAM_TRACK_LOCATION = "location";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(ControllerConstants.CONTROLLER_CONFIG_KEY);
        String trackId = request.getParameter(PARAM_TRACK_ID);
        String location = request.getParameter(PARAM_TRACK_LOCATION);

        String resultData;
        int fileSize;
        byte[] trackBytes = null;
        try {
            Path trackPath = Paths.get(ConfigurationManager.getProperty(CommandConstants.RESOURCE_AUDIO), location);
            File trackFile = trackPath.toFile();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(trackFile));
            fileSize = (int) trackFile.length();

            if (fileSize != 0) {
                trackBytes = new byte[fileSize];
                in.read(trackBytes, 0, fileSize);
            }

            response.setContentType(CommandConstants.MIME_TYPE_AUDIO_MP3);
            controllerConfiguration.setState(ControllerConfiguration.State.RESPONSE);
            resultData = Base64.getEncoder().encodeToString(trackBytes);
        } catch (IOException e) {
            LOG.error("Errors loading track bytes.", e);
            resultData = handleDBError(e, request, response);
        }

        return resultData;
    }
}
