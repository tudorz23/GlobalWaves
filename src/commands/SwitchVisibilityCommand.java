package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterSwitchVisibility;
import utils.Visibility;

public class SwitchVisibilityCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public SwitchVisibilityCommand(Session session, CommandInput commandInput,
                                   User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterSwitchVisibility printer = new PrinterSwitchVisibility(user, session, output);

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
