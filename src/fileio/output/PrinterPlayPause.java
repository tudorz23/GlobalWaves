package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.User;

public class PrinterPlayPause extends Printer {
    private User user;

    /* Constructor */
    public PrinterPlayPause(User user, Session session, ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    public void print(String message) {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "playPause");
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());
        commandNode.put("message", message);

        output.add(commandNode);
    }
}
