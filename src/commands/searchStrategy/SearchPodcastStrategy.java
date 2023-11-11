package commands.searchStrategy;

import client.Session;
import database.Audio;
import database.Podcast;
import database.User;
import fileio.input.CommandInput;
import fileio.input.FiltersInput;
import java.util.ArrayList;
import java.util.Iterator;

import static utils.Constants.SEARCH_MAX_RES_SIZE;

public class SearchPodcastStrategy implements ISearchStrategy {
    private Session session;
    private CommandInput commandInput;
    private User user;

    /* Constructor */
    public SearchPodcastStrategy(Session session, CommandInput commandInput, User user) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
    }

    @Override
    public void search() {
        ArrayList<Audio> searchResult = user.getSearchResult();

        FiltersInput filtersInput = commandInput.getFilters();

        if (filtersInput.getName() != null) {
            searchPodcastsByName(searchResult, filtersInput.getName());
        }

        if (filtersInput.getOwner() != null) {
            searchPodcastsByOwner(searchResult, filtersInput.getOwner());
        }
    }

    private void searchPodcastsByName(ArrayList<Audio> searchResult, String name) {
        // If there were another criteria applied.
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Podcast podcast = (Podcast) iterator.next();

                if (!podcast.getName().startsWith(name)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Podcast podcast : session.getDatabase().getPodcasts()) {
            if (podcast.getName().startsWith(name)) {
                searchResult.add(podcast);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }

    private void searchPodcastsByOwner(ArrayList<Audio> searchResult, String owner) {
        // If there were another criteria applied.
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Podcast podcast = (Podcast) iterator.next();

                if (!podcast.getOwner().equals(owner)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Podcast podcast : session.getDatabase().getPodcasts()) {
            if (podcast.getOwner().equals(owner)) {
                searchResult.add(podcast);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }
}
