package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterShuffle;
import utils.AudioType;
import utils.PlayerState;

import java.util.Collections;
import java.util.Random;

public class ShuffleCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public ShuffleCommand(Session session, CommandInput commandInput,
                          User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterShuffle printer = new PrinterShuffle(user, session, output);
        Player userPlayer = user.getPlayer();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            printer.print("Please load a source before using the shuffle function.");
            return;
        }

        if (userPlayer.getCurrPlaying().getType() != AudioType.PLAYLIST) {
            printer.print("The loaded source is not a playlist.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before using the shuffle function.");
            return;
        }

        if (userPlayer.isShuffle()) {
            userPlayer.setShuffle(false);
            printer.print("Shuffle function deactivated successfully.");
            return;
        }

        Playlist currPlaylist = (Playlist) userPlayer.getCurrPlaying();

        // Clear any old shuffle.
        currPlaylist.getShuffleArray().clear();

        for (int i = 0; i < currPlaylist.getSongs().size(); i++) {
            currPlaylist.getShuffleArray().add(i);
        }

        Collections.shuffle(currPlaylist.getShuffleArray(),
                            new Random(commandInput.getSeed()));

        userPlayer.setShuffle(true);
        printer.print("Shuffle function activated successfully.");
    }
}
