package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterShowPlaylists;

public final class ShowPlaylistsCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public ShowPlaylistsCommand(final Session session, final CommandInput commandInput,
                                final User user, final ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterShowPlaylists printer = new PrinterShowPlaylists(user, session, output);

        printer.print();
    }
}
