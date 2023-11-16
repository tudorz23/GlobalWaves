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

public final class AddRemoveInPlaylistCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public AddRemoveInPlaylistCommand(final Session session, final CommandInput commandInput,
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
            printer.print("Please load a source before adding to or removing from the playlist.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please load a source before adding to or removing from the playlist.");
            return;
        }

        if (commandInput.getPlaylistId() > user.getPlaylists().size()) {
            printer.print("The specified playlist does not exist.");
            return;
        }

        if (userPlayer.getCurrPlaying().getType() != AudioType.SONG) {
            printer.print("The loaded source is not a song.");
            return;
        }

        int realIndex = commandInput.getPlaylistId() - 1;
        Playlist userPlaylist = user.getPlaylists().get(realIndex);

        Song playingSong = (Song) userPlayer.getCurrPlaying();

        // Remember that the Player stores a deep copy of the song,
        // so a search in the database is necessary to get the real song instance.
        Song song;
        try {
            song = session.getDatabase().searchSongInDatabase(playingSong);
        } catch (IllegalArgumentException illegalArgumentException) {
            return;
        }

       if (userPlaylist.getSongs().contains(song)) {
           userPlaylist.removeSong(song);
           printer.print("Successfully removed from playlist.");
           return;
       }

       userPlaylist.addSong(song);
       printer.print("Successfully added to playlist.");
    }
}
