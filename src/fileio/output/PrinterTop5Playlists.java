package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Playlist;

import java.util.ArrayList;

public class PrinterTop5Playlists extends Printer {
    /* Constructor */
    public PrinterTop5Playlists(Session session, ArrayNode output) {
        super(session, output);
    }

    public void print(ArrayList<Playlist> followedPlaylists) {
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
