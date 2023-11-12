package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.*;
import utils.AudioType;
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

        ObjectNode stats = mapper.createObjectNode();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            printEmptyPlayer(stats);
            commandNode.set("stats", stats);
            output.add(commandNode);
            return;
        }

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            stats.put("name", "");
        } else {
            String trackName = userPlayer.getCurrPlaying().getPlayingTrackName();
            stats.put("name", trackName);
        }

        stats.put("remainedTime", userPlayer.getCurrPlaying().getRemainedTime());
        stats.put("repeat", userPlayer.getRepeatState().getLabel());
        stats.put("shuffle", userPlayer.isShuffle());

        stats.put("paused", userPlayer.getPlayerState() == PlayerState.PAUSED
                    || userPlayer.getPlayerState() == PlayerState.STOPPED);

        commandNode.set("stats", stats);

        output.add(commandNode);
    }

    private void printEmptyPlayer(ObjectNode stats) {
        stats.put("name" , "");
        stats.put("remainedTime", 0);
        stats.put("repeat", "No Repeat");
        stats.put("shuffle", false);
        stats.put("paused", true);
    }
}
