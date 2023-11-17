package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterStatus;
import utils.PlayerState;

public final class StatusCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public StatusCommand(final Session session, final CommandInput commandInput,
                         final User user, final ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterStatus printer = new PrinterStatus(user, session, output);
        Player userPlayer = user.getPlayer();

        if (userPlayer != null && userPlayer.getPlayerState() != PlayerState.EMPTY) {
            userPlayer.simulateTimePass(session.getTimestamp());
        }

        printer.print();
    }
}
