package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.User;
import fileio.input.CommandInput;
import utils.CommandType;

public class CommandFactory {
    private Session session;
    private ArrayNode output;

    /* Constructor */
    public CommandFactory(Session session, ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    /**
     * Factory method that creates ICommand instances, based on the CommandInput.
     * @param commandInput key that decides the type of instance that is created.
     * @return ICommand object.
     * @throws IllegalArgumentException if command is not supported.
     */
    public ICommand getCommand(CommandInput commandInput) throws IllegalArgumentException {
        CommandType commandType = CommandType.fromString(commandInput.getCommand());

        if (commandType == null) {
            throw new IllegalArgumentException("Command " + commandInput.getCommand() + " not yet implemented.");
        }

        User user = getUser(commandInput);

        if (user == null) {
            throw new IllegalArgumentException("Invalid user argument.");
        }

        switch (commandType) {
            case SEARCH -> {
                return new SearchCommand(session, commandInput, user, output);
            }
            case SELECT -> {
                return new SelectCommand(session, commandInput, user, output);
            }
            case LOAD -> {
                return new LoadCommand(session, commandInput, user, output);
            }
            case PLAY_PAUSE -> {
                return new PlayPauseCommand(session, commandInput, user, output);
            }
            case STATUS -> {
                return new StatusCommand(session, commandInput, user, output);
            }
            case CREATE_PLAYLIST -> {
                return new CreatePlaylistCommand(session, commandInput, user, output);
            }
            case ADD_REMOVE_IN_PLAYLIST -> {
                return new AddRemoveInPlaylistCommand(session, commandInput, user, output);
            }
            case LIKE -> {
                return new LikeCommand(session, commandInput, user, output);
            }
            case SHOW_PLAYLISTS -> {
                return new ShowPlaylistsCommand(session, commandInput, user, output);
            }
            case SHOW_PREFERRED_SONGS -> {
                return new ShowPreferredSongsCommand(session, commandInput, user, output);
            }
            default -> throw new IllegalArgumentException("Command " + commandInput.getCommand()
                    + " not yet implemented.");
        }
    }

    /**
     * Traverses the user list from the database.
     * @return User with the requested username.
     */
    private User getUser(CommandInput commandInput) {
        for (User user : session.getDatabase().getUsers()) {
            if (user.getUsername().equals(commandInput.getUsername())) {
                return user;
            }
        }
        return null;
    }
}
