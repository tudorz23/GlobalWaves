package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.Visibility;

public final class SwitchVisibilityCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public SwitchVisibilityCommand(final Session session, final CommandInput commandInput,
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

        int oldId = commandInput.getPlaylistId();
        if (oldId > user.getPlaylists().size()) {
            printer.print("The specified playlist ID is too high.");
            return;
        }

        int realId = oldId - 1;

        Playlist playlist = user.getPlaylists().get(realId);

        Visibility newVisibility = Visibility.cycleVisibility(playlist.getVisibility());
        playlist.setVisibility(newVisibility);

        printer.print("Visibility status updated successfully to "
                        + playlist.getVisibility().getLabel() + ".");
    }
}
