package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Playlist;
import database.Song;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.AudioType;
import utils.PlayerState;

public final class LikeCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public LikeCommand(final Session session, final CommandInput commandInput,
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
            printer.print("Please load a source before liking or unliking.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before liking or unliking.");
            return;
        }

        if (userPlayer.getCurrPlaying().getType() == AudioType.PODCAST) {
            printer.print("Loaded source is not a song.");
            return;
        }

        Song playingSong;
        if (userPlayer.getCurrPlaying().getType() == AudioType.SONG) {
            playingSong = (Song) userPlayer.getCurrPlaying();
        } else {
            Playlist currPlaylist = (Playlist) (userPlayer.getCurrPlaying());
            playingSong = (currPlaylist.getSongs().get(currPlaylist.getPlayingSongIndex()));
        }

        Song song;
        try {
            song = session.getDatabase().searchSongInDatabase(playingSong);
        } catch (IllegalArgumentException illegalArgumentException) {
            return;
        }

        if (user.getLikedSongs().contains(song)) {
            user.removeLikedSong(song);
            printer.print("Unlike registered successfully.");
            return;
        }

        user.addLikedSong(song);
        printer.print("Like registered successfully.");
    }
}
