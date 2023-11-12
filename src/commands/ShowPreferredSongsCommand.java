package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterShowPreferredSongs;

public class ShowPreferredSongsCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public ShowPreferredSongsCommand(Session session, CommandInput commandInput,
                                     User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());

        PrinterShowPreferredSongs printer = new PrinterShowPreferredSongs(user, session, output);
        printer.print();
    }
}
