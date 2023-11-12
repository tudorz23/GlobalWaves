package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Song;
import database.User;

public class PrinterShowPreferredSongs extends Printer {
    private User user;

    /* Constructor */
    public PrinterShowPreferredSongs(User user, Session session, ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    public void print() {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "showPreferredSongs");
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());

        ArrayNode result = mapper.createArrayNode();
        for (Song song : user.getLikedSongs()) {
            result.add(song.getName());
        }

        commandNode.set("result", result);

        output.add(commandNode);
    }
}
