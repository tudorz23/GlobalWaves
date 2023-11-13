package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Podcast;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBackward;
import utils.AudioType;
import utils.PlayerState;

public class BackwardCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private User user;
    private ArrayNode output;

    /* Constructor */
    public BackwardCommand(Session session, CommandInput commandInput,
                           User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterBackward printer = new PrinterBackward(user, session, output);
        Player userPlayer = user.getPlayer();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            printer.print("Please select a source before rewinding.");
            return;
        }

        userPlayer.simulateTimePass(session.getTimestamp());

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            printer.print("Please select a source before rewinding.");
            return;
        }

        if (userPlayer.getCurrPlaying().getType() != AudioType.PODCAST) {
            printer.print("The loaded source is not a podcast.");
            return;
        }

        Podcast podcast = (Podcast) userPlayer.getCurrPlaying();

        // Apply the "backward" command.
        podcast.backward(userPlayer);
        printer.print("Rewound successfully.");
    }
}