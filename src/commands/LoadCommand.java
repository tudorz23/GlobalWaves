package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterLoad;
import utils.AudioType;
import utils.PlayerState;
import utils.RepeatState;

public class LoadCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public LoadCommand(Session session, CommandInput commandInput,
                       User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }


    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterLoad printerLoad = new PrinterLoad(user, session, output);

        if (user.getSelection() == null) {
            printerLoad.print("Please select a source before attempting to load.");
            return;
        }

        if (user.getSelection().getType() == AudioType.PLAYLIST) {
            if (((Playlist) user.getSelection()).getSongs().isEmpty()) {
                printerLoad.print("You can't load an empty audio collection!");
                return;
            }
        }

        Player userPlayer = user.getPlayer();

        // Load the selection into the player. Introduce a deep-copy
        // into the player, so changes aren't visible to all users.
        userPlayer.setCurrPlaying(user.getSelection().getDeepCopy());

        userPlayer.setPrevTimeInfo(session.getTimestamp());
        userPlayer.setPlayerState(PlayerState.PLAYING);

        if (userPlayer.getCurrPlaying().getType() == AudioType.PLAYLIST) {
            userPlayer.setRepeatState(RepeatState.NO_REPEAT_PLAYLIST);
        } else {
            userPlayer.setRepeatState(RepeatState.NO_REPEAT);
        }

        printerLoad.print("Playback loaded successfully.");
    }
}
