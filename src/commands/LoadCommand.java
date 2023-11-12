package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.*;
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

        // Load the selection into the player.
        loadSelection(userPlayer);

        userPlayer.setPrevTimeInfo(session.getTimestamp());
        userPlayer.setPlayerState(PlayerState.PLAYING);

        if (userPlayer.getCurrPlaying().getType() == AudioType.PLAYLIST) {
            userPlayer.setRepeatState(RepeatState.NO_REPEAT_PLAYLIST);
        } else {
            userPlayer.setRepeatState(RepeatState.NO_REPEAT);
        }

        userPlayer.setRepeatedOnce(false);

        user.setSelection(null);
        printerLoad.print("Playback loaded successfully.");
    }

    /**
     * Loads the selection into the player. Podcast load is done separately
     * For songs and playlists, a deep copy is directly added.
     */
    public void loadSelection(Player player) {
        Audio selection = user.getSelection();

        // If the selection is a podcast, check if it hasn't been listened to before.
        if (selection.getType() == AudioType.PODCAST) {
            loadPodcast(player, (Podcast) selection);
            return;
        }

        // If the selection is a song or a playlist, add a deep copy of it.
        player.setCurrPlaying(selection.getDeepCopy());
    }

    /**
     * Loads a podcast into the player. If the podcast has been listened to before,
     * that respective object is loaded. Else, a new deep copy is created and loaded.
     */
    public void loadPodcast(Player player, Podcast podcast) {
        boolean alreadyListened = false;

        for (Podcast listenedPodcast : user.getListenedPodcasts()) {
            if (listenedPodcast.getName().equals(podcast.getName())) {
                player.setCurrPlaying(listenedPodcast);
                alreadyListened = true;
                break;
            }
        }

        // If it has not been listened to, add a deep copy of it.
        if (!alreadyListened) {
            Podcast deepCopy = podcast.getDeepCopy();
            player.setCurrPlaying(deepCopy);
            user.getListenedPodcasts().add(deepCopy);
        }
    }
}
