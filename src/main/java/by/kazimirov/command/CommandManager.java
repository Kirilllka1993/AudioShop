package by.kazimirov.command;

/**
 *
 */
public class CommandManager {

    public IServletCommand defineCommand(String commandAction) {
        IServletCommand currentCommand = new EmptyCommand();
        if (commandAction == null || commandAction.isEmpty()) {
            return currentCommand;
        }

        CommandType currentType = CommandType.valueOf(commandAction.toUpperCase());
        currentCommand = currentType.getCurrentCommand();
        return currentCommand;
    }

}
