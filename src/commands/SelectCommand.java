package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;

public class SelectCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public SelectCommand(Session session, CommandInput commandInput,
                         User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());

        PrinterBasic printer = new PrinterBasic(user, session, output, commandInput.getCommand());

        if (user.getSearchResult() == null) {
            printer.print("Please conduct a search before making a selection.");
            return;
        }

        if (commandInput.getItemNumber() > user.getSearchResult().size()) {
            printer.print("The selected ID is too high.");
            return;
        }

        int index = commandInput.getItemNumber() - 1;
        user.setSelection(user.getSearchResult().get(index));

        printer.print("Successfully selected " + user.getSelection().getName() + ".");
    }
}
