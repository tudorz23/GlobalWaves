package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.PlayerState;

public class PlayPauseCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public PlayPauseCommand(Session session, CommandInput commandInput,
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
