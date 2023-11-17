package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.AudioType;
import utils.PlayerState;
import java.util.Collections;
import java.util.Random;

public final class ShuffleCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public ShuffleCommand(final Session session, final CommandInput commandInput,
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
            printer.print("Please load a source before using the shuffle function.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before using the shuffle function.");
            return;
        }

        if (userPlayer.getCurrPlaying().getType() != AudioType.PLAYLIST) {
            printer.print("The loaded source is not a playlist.");
            return;
        }

        Playlist currPlaylist = (Playlist) userPlayer.getCurrPlaying();

        // Clear the old shuffle array.
        currPlaylist.getShuffleArray().clear();

        if (userPlayer.isShuffle()) {
            // Set the shuffle array to v[i] = i. (i.e. "un-shuffle" the playlist).
            for (int i = 0; i < currPlaylist.getSongs().size(); i++) {
                currPlaylist.getShuffleArray().add(i);
            }

            userPlayer.setShuffle(false);
            printer.print("Shuffle function deactivated successfully.");
            return;
        }

        for (int i = 0; i < currPlaylist.getSongs().size(); i++) {
            currPlaylist.getShuffleArray().add(i);
        }

        Collections.shuffle(currPlaylist.getShuffleArray(),
                            new Random(commandInput.getSeed()));

        userPlayer.setShuffle(true);
        printer.print("Shuffle function activated successfully.");
    }
}
