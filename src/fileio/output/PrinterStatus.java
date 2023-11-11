package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Player;
import database.User;
import utils.PlayerState;

public class PrinterStatus extends Printer {
    private User user;

    /* Constructor */
    public PrinterStatus(User user, Session session, ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    public void print() {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "status");
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());

        Player userPlayer = user.getPlayer();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            commandNode.set("stats", null);
            output.add(commandNode);
            return;
        }

        ObjectNode stats = mapper.createObjectNode();

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            stats.put("name", "");
        } else {
            stats.put("name", userPlayer.getCurrPlaying().getName());
        }

        stats.put("remainedTime", userPlayer.getCurrPlaying().getRemainedTime());
        stats.put("repeat", userPlayer.getRepeatState().getLabel());
        stats.put("shuffle", userPlayer.isShuffle());

        stats.put("paused", userPlayer.getPlayerState() == PlayerState.PAUSED
                    || userPlayer.getPlayerState() == PlayerState.STOPPED);

        commandNode.set("stats", stats);

        output.add(commandNode);
    }
}
