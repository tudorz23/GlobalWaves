package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Song;

import java.util.ArrayList;

public class PrinterTop5Songs extends Printer {
    /* Constructor */
    public PrinterTop5Songs(Session session, ArrayNode output) {
        super(session, output);
    }

    public void print(ArrayList<Song> likedSongs) {
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
