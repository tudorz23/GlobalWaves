package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Audio;
import database.User;

public class PrinterSearch extends Printer {
    private User user;

    /* Constructor */
    public PrinterSearch(User user, Session session, ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    public void print() {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "search");
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());


        String message = "Search returned " + user.getSearchResult().size() + " results";
        commandNode.put("message", message);

        ArrayNode results = mapper.createArrayNode();
        for (Audio audio : user.getSearchResult()) {
            results.add(audio.getName());
        }

        commandNode.set("results", results);

        output.add(commandNode);
    }
}