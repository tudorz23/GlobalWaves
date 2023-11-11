package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterStatus;
import utils.PlayerState;

public class StatusCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public StatusCommand(Session session, CommandInput commandInput,
                       User user, ArrayNode output) {
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
