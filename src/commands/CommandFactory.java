package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import utils.CommandType;

public class CommandFactory {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public CommandFactory(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    /**
     * Factory method that creates ICommand instances, based on the CommandInput.
     * @param commandInput key that decides the type of instance that is created.
     * @return ICommand object.
     * @throws IllegalArgumentException if command is not supported.
     */
    public ICommand getCommand(CommandInput commandInput) throws IllegalArgumentException {
        CommandType commandType = CommandType.fromString(commandInput.getCommand());

        if (commandType == null) {
            throw new IllegalArgumentException("Command " + commandInput.getCommand() + " not yet implemented.");
        }

        switch (commandType) {
            case SEARCH -> {
                return new SearchCommand(session, commandInput, output);
            }
            default -> throw new IllegalArgumentException("Command " + commandInput.getCommand()
                    + " not yet implemented.");
        }
    }
}
