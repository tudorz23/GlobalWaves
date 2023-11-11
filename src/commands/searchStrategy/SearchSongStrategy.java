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
    }

    private void searchSongsByName(ArrayList<Audio> searchResult, String name) {
        // If there were another criteria applied.
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Song song = (Song) iterator.next();

                if (!song.getName().startsWith(name)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Song song : session.getDatabase().getSongs()) {
            if (song.getName().startsWith(name)) {
                searchResult.add(song);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }

    private void searchSongsByAlbum(ArrayList<Audio> searchResult, String album) {
        // If there were another criteria applied.
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Song song = (Song) iterator.next();

                if (!song.getAlbum().equals(album)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Song song : session.getDatabase().getSongs()) {
            if (song.getAlbum().equals(album)) {
                searchResult.add(song);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }

    private void searchSongsByTags(ArrayList<Audio> searchResult, ArrayList<String> tags) {
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Song song = (Song) iterator.next();

                boolean containsTag = false;
                for (String tag : tags) {
                    if (song.getTags().contains(tag)) {
                        containsTag = true;
                        break;
                    }
                }

                if (!containsTag) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Song song : session.getDatabase().getSongs()) {
            for (String tag : tags) {
                if (song.getTags().contains(tag)) {
                    searchResult.add(song);
                    break;
                }
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }

    private void searchSongsByLyrics(ArrayList<Audio> searchResult, String lyrics) {
        // If there were another criteria applied.
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Song song = (Song) iterator.next();

                if (!song.getLyrics().contains(lyrics)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Song song : session.getDatabase().getSongs()) {
            if (song.getLyrics().contains(lyrics)) {
                searchResult.add(song);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }

    private void searchSongsByGenre(ArrayList<Audio> searchResult, String genre) {
        // If there were another criteria applied.
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Song song = (Song) iterator.next();

                if (!song.getGenre().equals(genre)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Song song : session.getDatabase().getSongs()) {
            if (song.getGenre().contains(genre)) {
                searchResult.add(song);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }

    private void searchSongsByReleaseYear(ArrayList<Audio> searchResult, String releaseYear) {
        Criteria criteria = Criteria.fromString(releaseYear);
        String stringYear = releaseYear.substring(1);
        int reqYear = Integer.parseInt(stringYear);

        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Song song = (Song) iterator.next();

                if (!respectsCriteria(song.getReleaseYear(), reqYear, criteria)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Song song : session.getDatabase().getSongs()) {
            if (respectsCriteria(song.getReleaseYear(), reqYear, criteria)) {
                searchResult.add(song);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
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
        if (!searchResult.isEmpty()) {
            Iterator<Audio> iterator = searchResult.iterator();
            while (iterator.hasNext()) {
                Song song = (Song) iterator.next();

                if (!song.getArtist().equals(artist)) {
                    iterator.remove();
                }
            }
            return;
        }

        for (Song song : session.getDatabase().getSongs()) {
            if (song.getArtist().equals(artist)) {
                searchResult.add(song);
            }

            if (searchResult.size() == SEARCH_MAX_RES_SIZE) {
                return;
            }
        }
    }
}
