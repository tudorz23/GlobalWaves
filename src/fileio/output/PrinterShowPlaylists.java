package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Playlist;
import database.Song;
import database.User;
import java.util.ArrayList;

public final class PrinterShowPlaylists extends Printer {
    private final User user;

    /* Constructor */
    public PrinterShowPlaylists(final User user, final Session session,
                                final ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    /**
     * Appends the ShowPlaylists output to the output ArrayNode.
     */
    public void print() {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "showPlaylists");
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());

        ArrayNode result = mapper.createArrayNode();
        for (Playlist playlist : user.getPlaylists()) {
            result.add(createPlaylistNode(playlist));
        }

        commandNode.set("result", result);
        output.add(commandNode);
    }

    /**
     * @param playlist Original Playlist object.
     * @return ObjectNode containing data regarding a Playlist.
     */
    public ObjectNode createPlaylistNode(final Playlist playlist) {
        ObjectNode playlistNode = mapper.createObjectNode();

        playlistNode.put("name", playlist.getName());

        ArrayNode songsNode = createSongsArrayNode(playlist.getSongs());
        playlistNode.set("songs", songsNode);

        playlistNode.put("visibility", playlist.getVisibility().getLabel());
        playlistNode.put("followers", playlist.getFollowersCnt());

        return playlistNode;
    }

    /**
     * @param songs ArrayList of Song objects.
     * @return ArrayNode containing the names of the songs from an ArrayList.
     */
    public ArrayNode createSongsArrayNode(final ArrayList<Song> songs) {
        ArrayNode songsArrayNode = mapper.createArrayNode();

        for (Song song : songs) {
            songsArrayNode.add(song.getName());
        }

        return songsArrayNode;
    }
}
