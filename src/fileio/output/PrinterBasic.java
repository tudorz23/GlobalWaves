package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.User;

public final class PrinterBasic extends Printer {
    private final User user;
    private final String command;

    /* Constructor */
    public PrinterBasic(final User user, final Session session,
                        final ArrayNode output, final String command) {
        super(session, output);
        this.user = user;
        this.command = command;
    }

    /**
     * Appends a standard output, composed of generic data and a message,
     * to the output ArrayNode.
     */
    public void print(final String message) {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", command);
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());
        commandNode.put("message", message);

        output.add(commandNode);
    }
}
