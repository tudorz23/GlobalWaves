package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;

public final class CreatePlaylistCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public CreatePlaylistCommand(final Session session, final CommandInput commandInput,
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

        Playlist newPlaylist = new Playlist(commandInput.getPlaylistName(), user.getUsername());

        if (!user.addPlaylist(newPlaylist)) {
            printer.print("A playlist with the same name already exists.");
            return;
        }

        // Add the playlist to the database.
        session.getDatabase().addPlaylist(newPlaylist);

        printer.print("Playlist created successfully.");
    }
}
