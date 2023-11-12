package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterRepeat;
import utils.PlayerState;
import utils.RepeatState;

public class RepeatCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public RepeatCommand(Session session, CommandInput commandInput,
                         User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterRepeat printer = new PrinterRepeat(user, session, output);
        Player userPlayer = user.getPlayer();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            printer.print("Please load a source before setting the repeat status.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        RepeatState currRepeatState = userPlayer.getRepeatState();
        RepeatState newRepeatState = RepeatState.cycleState(currRepeatState);

        userPlayer.setRepeatState(newRepeatState);
        if (newRepeatState == RepeatState.REPEAT_ONCE) {
            userPlayer.setRepeatedOnce(false);
        }

        printer.print("Repeat mode changed to " + newRepeatState.getLabel() + ".");
    }
}
