package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterCreatePlaylist;

public class CreatePlaylistCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public CreatePlaylistCommand(Session session, CommandInput commandInput,
                       User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterCreatePlaylist printer = new PrinterCreatePlaylist(user, session, output);

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
