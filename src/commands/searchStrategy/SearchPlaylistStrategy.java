package commands.searchStrategy;

import client.Session;
import database.Audio;
import database.Playlist;
import database.User;
import fileio.input.CommandInput;
import fileio.input.FiltersInput;
import utils.Visibility;

import java.util.ArrayList;
import java.util.Iterator;

import static utils.Constants.SEARCH_MAX_RES_SIZE;

public class SearchPlaylistStrategy implements ISearchStrategy {
    private Session session;
    private CommandInput commandInput;
    private User user;

    /* Constructor */
    public SearchPlaylistStrategy(Session session, CommandInput commandInput, User user) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
    }

    @Override
    public void search() {
        ArrayList<Audio> searchResult = user.getSearchResult();

        FiltersInput filtersInput = commandInput.getFilters();

        // Add all visible playlists, then remove those that do not respect the given filters.
        for (Playlist playlist : session.getDatabase().getPlaylists()) {
            if (playlist.getVisibility() == Visibility.PUBLIC
                || playlist.getOwner().equals(user.getUsername())) {
                searchResult.add(playlist);
            }
        }

        if (filtersInput.getName() != null) {
            searchPlaylistsByName(searchResult, filtersInput.getName());
        }

        if (filtersInput.getOwner() != null) {
            searchPlaylistsByOwner(searchResult, filtersInput.getOwner());
        }

        while (searchResult.size() > SEARCH_MAX_RES_SIZE) {
            searchResult.remove(searchResult.size() - 1);
        }
    }

    private void searchPlaylistsByName(ArrayList<Audio> searchResult, String name) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Playlist playlist = (Playlist) iterator.next();

            if (!playlist.getName().startsWith(name)) {
                iterator.remove();
            }
        }
    }

    private void searchPlaylistsByOwner(ArrayList<Audio> searchResult, String owner) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Playlist playlist = (Playlist) iterator.next();

            if (!playlist.getOwner().equals(owner)) {
                iterator.remove();
            }
        }
    }
}
