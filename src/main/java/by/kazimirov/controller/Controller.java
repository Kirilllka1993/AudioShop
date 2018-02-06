package by.kazimirov.controller;


import by.kazimirov.command.CommandManager;
import by.kazimirov.command.IServletCommand;
import by.kazimirov.database.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static by.kazimirov.controller.ControllerConstants.CONTROLLER_CONFIG_KEY;

/**
 *
 */

@WebServlet(urlPatterns = "/s", name = "Controller")
@MultipartConfig
public class Controller extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closePool();
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerConfiguration controllerConfiguration = (ControllerConfiguration) request.getSession().getAttribute(CONTROLLER_CONFIG_KEY);
        CommandManager commandManager = new CommandManager();
        IServletCommand command = commandManager.defineCommand(controllerConfiguration.getCommand());
        String commandExecuteRes = command.execute(request, response);

        switch (controllerConfiguration.getState()) {
            case FORWARD:
                request.getRequestDispatcher(commandExecuteRes).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(commandExecuteRes);
                break;
            case RESPONSE:
                if(!commandExecuteRes.isEmpty()) {
                    response.getOutputStream().write(Base64.getDecoder().decode(commandExecuteRes));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                break;
            case AJAX:
                response.getWriter().write(commandExecuteRes);
                break;
            default:
        }
    }
}
