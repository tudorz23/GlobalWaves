package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.PlayerState;

public final class PlayPauseCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public PlayPauseCommand(final Session session, final CommandInput commandInput,
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
            printer.print("Please load a source before attempting to pause or resume playback.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before attempting to pause or resume playback.");
            return;
        }

        if (userPlayer.getPlayerState() == PlayerState.PLAYING) {
            userPlayer.setPlayerState(PlayerState.PAUSED);
            printer.print("Playback paused successfully.");
            return;
        }

        userPlayer.setPlayerState(PlayerState.PLAYING);
        printer.print("Playback resumed successfully.");
    }
}
