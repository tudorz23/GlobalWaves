package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class SearchCommand implements ICommand {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public SearchCommand(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    @Override
    public void execute() {

    }
}
