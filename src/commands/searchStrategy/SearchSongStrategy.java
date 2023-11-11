package commands.searchStrategy;

import client.Session;
import database.Audio;
import database.Song;
import database.User;
import fileio.input.CommandInput;
import fileio.input.FiltersInput;
import utils.Criteria;
import java.util.ArrayList;
import java.util.Iterator;

import static utils.Constants.SEARCH_MAX_RES_SIZE;

public class SearchSongStrategy implements ISearchStrategy {
    private Session session;
    private CommandInput commandInput;
    private User user;

    /* Constructor */
    public SearchSongStrategy(Session session, CommandInput commandInput, User user) {
        this.session = session;
        this.commandInput = commandInput;
        this.user = user;
    }

    @Override
    public void search() {
        ArrayList<Audio> searchResult = user.getSearchResult();

        FiltersInput filtersInput = commandInput.getFilters();

        // Add all songs, then remove those that do not respect the given filters.
        searchResult.addAll(session.getDatabase().getSongs());

        if (filtersInput.getName() != null) {
            searchSongsByName(searchResult, filtersInput.getName());
        }

        if (filtersInput.getAlbum() != null) {
            searchSongsByAlbum(searchResult, filtersInput.getAlbum());
        }

        if (filtersInput.getTags() != null) {
            searchSongsByTags(searchResult, filtersInput.getTags());
        }

        if (filtersInput.getLyrics() != null) {
            searchSongsByLyrics(searchResult, filtersInput.getLyrics());
        }

        if (filtersInput.getGenre() != null) {
            searchSongsByGenre(searchResult, filtersInput.getGenre());
        }

        if (filtersInput.getReleaseYear() != null) {
            searchSongsByReleaseYear(searchResult, filtersInput.getReleaseYear());
        }

        if (filtersInput.getArtist() != null) {
            searchSongsByArtist(searchResult, filtersInput.getArtist());
        }

        while (searchResult.size() > SEARCH_MAX_RES_SIZE) {
            searchResult.remove(searchResult.size() - 1);
        }
    }

    private void searchSongsByName(ArrayList<Audio> searchResult, String name) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Song song = (Song) iterator.next();

            if (!song.getName().startsWith(name)) {
                iterator.remove();
            }
        }
    }

    private void searchSongsByAlbum(ArrayList<Audio> searchResult, String album) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Song song = (Song) iterator.next();

            if (!song.getAlbum().equals(album)) {
                iterator.remove();
            }
        }
    }

    private void searchSongsByTags(ArrayList<Audio> searchResult, ArrayList<String> tags) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Song song = (Song) iterator.next();

            if (!song.getTags().containsAll(tags)) {
                iterator.remove();
            }
        }
    }

    private void searchSongsByLyrics(ArrayList<Audio> searchResult, String lyrics) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Song song = (Song) iterator.next();

            if (!song.getLyrics().contains(lyrics)) {
                iterator.remove();
            }
        }
    }

    private void searchSongsByGenre(ArrayList<Audio> searchResult, String genre) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Song song = (Song) iterator.next();

            if (!song.getGenre().equalsIgnoreCase(genre)) {
                iterator.remove();
            }
        }
    }

    private void searchSongsByReleaseYear(ArrayList<Audio> searchResult, String releaseYear) {
        Criteria criteria = Criteria.fromString(releaseYear);
        String stringYear = releaseYear.substring(1);
        int reqYear = Integer.parseInt(stringYear);

        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Song song = (Song) iterator.next();

            if (!respectsCriteria(song.getReleaseYear(), reqYear, criteria)) {
                iterator.remove();
            }
        }
    }

    /**
     * Checks if the year respects the given criteria compared to the requested year.
     */
    private boolean respectsCriteria(int year, int reqYear, Criteria criteria) {
        if (criteria == Criteria.BEFORE) {
            return (year < reqYear);
        }

        return (year > reqYear);
    }

    private void searchSongsByArtist(ArrayList<Audio> searchResult, String artist) {
        Iterator<Audio> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            Song song = (Song) iterator.next();

            if (!song.getArtist().equals(artist)) {
                iterator.remove();
            }
        }
    }
}
