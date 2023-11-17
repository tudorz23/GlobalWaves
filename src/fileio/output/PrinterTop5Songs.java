package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Song;
import java.util.ArrayList;

public final class PrinterTop5Songs extends Printer {
    /* Constructor */
    public PrinterTop5Songs(final Session session, final ArrayNode output) {
        super(session, output);
    }

    /**
     * Appends the Top5Songs output to the output ArrayNode.
     */
    public void print(final ArrayList<Song> likedSongs) {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "getTop5Songs");
        commandNode.put("timestamp", session.getTimestamp());

        ArrayNode result = mapper.createArrayNode();
        for (Song song : likedSongs) {
            result.add(song.getName());
        }

        commandNode.set("result", result);
        output.add(commandNode);
    }
}
