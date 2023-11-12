package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Playlist;
import database.Song;
import database.User;

import java.util.ArrayList;

public class PrinterShowPlaylists extends Printer {
    private User user;

    /* Constructor */
    public PrinterShowPlaylists(User user, Session session, ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    public void print() {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "showPlaylists");
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());

        ArrayNode result = mapper.createArrayNode();
        for (Playlist playlist : user.getPlaylists()) {
            result.add(createPlaylistNode(playlist));
        }

        output.add(commandNode);
    }

    public ObjectNode createPlaylistNode(Playlist playlist) {
        ObjectNode playlistNode = mapper.createObjectNode();

        playlistNode.put("name", playlist.getName());

        ArrayNode songsNode = createSongsArrayNode(playlist.getSongs());
        playlistNode.set("songs", songsNode);

        playlistNode.put("visibility", playlist.getVisibility().getLabel());
        playlistNode.put("followers", playlist.getFollowersCnt());

        return playlistNode;
    }

    public ArrayNode createSongsArrayNode(ArrayList<Song> songs) {
        ArrayNode songsArrayNode = mapper.createArrayNode();

        for (Song song : songs) {
            songsArrayNode.add(song.getName());
        }

        return songsArrayNode;
    }
}
