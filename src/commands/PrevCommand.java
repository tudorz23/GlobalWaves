package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterPrev;
import utils.PlayerState;

public class PrevCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public PrevCommand(Session session, CommandInput commandInput,
                       User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }


    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterPrev printer = new PrinterPrev(user, session, output);
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
