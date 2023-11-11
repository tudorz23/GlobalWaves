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
    private User user;
    private ArrayNode output;

    /* Constructor */
    public SearchCommand(Session session, CommandInput commandInput,
                         User user, ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());

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
}
