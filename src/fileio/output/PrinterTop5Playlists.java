package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Playlist;
import java.util.ArrayList;

public final class PrinterTop5Playlists extends Printer {
    /* Constructor */
    public PrinterTop5Playlists(final Session session, final ArrayNode output) {
        super(session, output);
    }

    /**
     * Appends the Top5Playlists output to the output ArrayNode.
     */
    public void print(final ArrayList<Playlist> followedPlaylists) {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "getTop5Playlists");
        commandNode.put("timestamp", session.getTimestamp());

        ArrayNode result = mapper.createArrayNode();
        for (Playlist playlist : followedPlaylists) {
            result.add(playlist.getName());
        }

        commandNode.set("result", result);
        output.add(commandNode);
    }
}
