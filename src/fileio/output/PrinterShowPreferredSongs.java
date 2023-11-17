package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Song;
import database.User;

public final class PrinterShowPreferredSongs extends Printer {
    private final User user;

    /* Constructor */
    public PrinterShowPreferredSongs(final User user, final Session session,
                                     final ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    /**
     * Appends the ShowPreferredSongs output to the output ArrayNode.
     */
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
