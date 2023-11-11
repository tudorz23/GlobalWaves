package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commands.searchStrategy.ISearchStrategy;
import commands.searchStrategy.SearchPlaylistStrategy;
import commands.searchStrategy.SearchPodcastStrategy;
import commands.searchStrategy.SearchSongStrategy;
import database.User;
import fileio.input.CommandInput;
import fileio.output.PrinterSearch;

public class SearchCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private ArrayNode output;

    /* Constructor */
    public SearchCommand(Session session, CommandInput commandInput, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());

        User user;
        try {
            user = getUser();
        } catch (IllegalArgumentException illegalArgumentException) {
            return;
        }

        // Clear the old search result.
        user.getSearchResult().clear();

        ISearchStrategy searchStrategy = getSearchStrategy(user);

        searchStrategy.search();

        PrinterSearch printerSearch = new PrinterSearch(user, session, output);
        printerSearch.print();
    }

    private ISearchStrategy getSearchStrategy(User user) {
        switch (commandInput.getType()) {
            case "song" -> {
                return new SearchSongStrategy(session, commandInput, user);
            }
            case "playlist" -> {
                return new SearchPlaylistStrategy(session, commandInput, user);
            }
            case "podcast" -> {
                return new SearchPodcastStrategy(session, commandInput, user);
            }
            default -> throw new IllegalArgumentException("Invalid search criteria.");
        }
    }

    /**
     * Traverses the user list from the database.
     * @return User with the requested username.
     */
    private User getUser() {
        for (User user : session.getDatabase().getUsers()) {
            if (user.getUsername().equals(commandInput.getUsername())) {
                return user;
            }
        }
        throw new IllegalArgumentException("User not registered.");
    }
}
