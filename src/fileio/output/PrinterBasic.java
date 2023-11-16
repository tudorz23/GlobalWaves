package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.User;

public class PrinterBasic extends Printer {
    private User user;
    private String command;

    /* Constructor */
    public PrinterBasic(User user, Session session, ArrayNode output, String command) {
        super(session, output);
        this.user = user;
        this.command = command;
    }

    public void print(String message) {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", command);
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());
        commandNode.put("message", message);

        output.add(commandNode);
    }
}
