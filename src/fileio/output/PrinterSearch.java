package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Audio;
import database.User;

public class PrinterSearch {
    private User user;
    private Session session;
    private ArrayNode output;
    private final ObjectMapper mapper = new ObjectMapper();

    /* Constructor */
    public PrinterSearch(User user, Session session, ArrayNode output) {
        this.user = user;
        this.session = session;
        this.output = output;
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
