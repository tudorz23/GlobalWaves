package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Song;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterLike;
import utils.AudioType;
import utils.PlayerState;

public class LikeCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public LikeCommand(Session session, CommandInput commandInput,
                       User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterLike printer = new PrinterLike(user, session, output);
        Player userPlayer = user.getPlayer();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            printer.print("Please load a source before liking or unliking.");
            return;
        }

        if (userPlayer.getCurrPlaying().getType() != AudioType.SONG) {
            printer.print("Loaded source is not a song.");
            return;
        }

        Song playerSong = (Song) userPlayer.getCurrPlaying();
        Song song = session.getDatabase().searchSongInDatabase(playerSong);

        if (song == null) {
            throw new IllegalArgumentException("Song not found in the database.");
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
