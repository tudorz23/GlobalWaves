package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Generic Printer class to be subclassed by more specialized printers.
 */
public abstract class Printer {
    protected Session session;
    protected ArrayNode output;
    protected final ObjectMapper mapper = new ObjectMapper();

    /* Constructor */
    public Printer(final Session session, final ArrayNode output) {
        this.session = session;
        this.output = output;
    }
}
