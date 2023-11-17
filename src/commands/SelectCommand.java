package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;

public final class SelectCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public SelectCommand(final Session session, final CommandInput commandInput,
                         final User user, final ArrayNode output) {
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
