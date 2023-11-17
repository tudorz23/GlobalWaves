package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.User;
import fileio.input.CommandInput;
import utils.CommandType;

public class CommandFactory {
    private final Session session;
    private final ArrayNode output;

    /* Constructor */
    public CommandFactory(final Session session, final ArrayNode output) {
        this.session = session;
        this.output = output;
    }

    /**
     * Factory method that creates ICommand instances, based on the CommandInput.
     * @param commandInput key that decides the type of instance that is created.
     * @return ICommand object.
     * @throws IllegalArgumentException if command is not supported.
     */
    public ICommand getCommand(final CommandInput commandInput) throws IllegalArgumentException {
        CommandType commandType = CommandType.fromString(commandInput.getCommand());
        if (commandType == null) {
            throw new IllegalArgumentException("Command " + commandInput.getCommand()
                                                + " not supported.");
        }

        User user = null;
        if (commandType != CommandType.GET_TOP5_PLAYLISTS
                && commandType != CommandType.GET_TOP5_SONGS) {
            user = getUser(commandInput);
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
            case REPEAT -> {
                return new RepeatCommand(session, commandInput, user, output);
            }
            case SHUFFLE -> {
                return new ShuffleCommand(session, commandInput, user, output);
            }
            case NEXT -> {
                return new NextCommand(session, commandInput, user, output);
            }
            case SWITCH_VISIBILITY -> {
                return new SwitchVisibilityCommand(session, commandInput, user, output);
            }
            case FOLLOW -> {
                return new FollowPlaylistCommand(session, commandInput, user, output);
            }
            case PREV -> {
                return new PrevCommand(session, commandInput, user, output);
            }
            case FORWARD -> {
                return new ForwardCommand(session, commandInput, user, output);
            }
            case BACKWARD -> {
                return new BackwardCommand(session, commandInput, user, output);
            }
            case GET_TOP5_SONGS -> {
                return new GetTop5SongsCommand(session, commandInput, output);
            }
            case GET_TOP5_PLAYLISTS -> {
                return new GetTop5PlaylistsCommand(session, commandInput, output);
            }
            default -> throw new IllegalArgumentException("Command " + commandInput.getCommand()
                    + " not supported.");
        }
    }

    /**
     * Traverses the user list from the database.
     * @return User with the requested username.
     * @throws IllegalArgumentException if there is no user with the requested username
     * in the database.
     */
    private User getUser(final CommandInput commandInput) {
        for (User user : session.getDatabase().getUsers()) {
            if (user.getUsername().equals(commandInput.getUsername())) {
                return user;
            }
        }

        throw new IllegalArgumentException("Nonexistent user.");
    }
}
