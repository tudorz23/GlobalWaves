package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.PlayerState;

public final class PrevCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public PrevCommand(final Session session, final CommandInput commandInput,
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
        Player userPlayer = user.getPlayer();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            printer.print("Please load a source before returning to the previous track.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before returning to the previous track.");
            return;
        }

        // Apply the "prev" command.
        userPlayer.getCurrPlaying().prev(userPlayer);

        String trackName = userPlayer.getCurrPlaying().getPlayingTrackName();
        printer.print("Returned to previous track successfully. The current track is "
                    + trackName + ".");
    }
}
