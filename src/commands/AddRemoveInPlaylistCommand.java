package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Playlist;
import database.Song;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterAddRemoveInPlaylist;
import utils.AudioType;
import utils.PlayerState;

public class AddRemoveInPlaylistCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public AddRemoveInPlaylistCommand(Session session, CommandInput commandInput,
                       User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterAddRemoveInPlaylist printer = new PrinterAddRemoveInPlaylist(user, session, output);
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

        Song playerSong = (Song) userPlayer.getCurrPlaying();

        // Remember that the Player stores a deep copy of the song,
        // so a search in the database is necessary to get the real song.
        Song song = session.getDatabase().searchSongInDatabase(playerSong);

        if (song == null) {
            throw new IllegalArgumentException("Song not found in the database.");
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
