package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Printer {
    protected Session session;
    protected ArrayNode output;
    protected final ObjectMapper mapper = new ObjectMapper();

    /* Constructor */
    public Printer(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }
}
