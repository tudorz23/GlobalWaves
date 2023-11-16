package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.PlayerState;

public class NextCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public NextCommand(Session session, CommandInput commandInput,
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
        Player userPlayer = user.getPlayer();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            printer.print("Please load a source before skipping to the next track.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before skipping to the next track.");
            return;
        }

        // Apply the "next" command.
        userPlayer.getCurrPlaying().next(userPlayer);

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before skipping to the next track.");
            return;
        }

        String trackName = userPlayer.getCurrPlaying().getPlayingTrackName();
        printer.print("Skipped to next track successfully. The current track is "
                    + trackName + ".");
    }
}
