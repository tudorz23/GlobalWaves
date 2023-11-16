package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Player;
import database.Podcast;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterBasic;
import utils.AudioType;
import utils.PlayerState;

public final class BackwardCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final User user;
    private final ArrayNode output;

    /* Constructor */
    public BackwardCommand(final Session session, final CommandInput commandInput,
                           final User user, final ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterBasic printer = new PrinterBasic(user, session, output, commandInput.getCommand());
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
