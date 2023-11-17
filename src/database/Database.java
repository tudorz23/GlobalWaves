package database;

import java.util.ArrayList;

public final class Database {
    private ArrayList<Song> songs;
    private ArrayList<Podcast> podcasts;
    private ArrayList<User> users;
    private ArrayList<Playlist> playlists;

    /* Constructor */
    public Database() {
        this.songs = new ArrayList<>();
        this.podcasts = new ArrayList<>();
        this.users = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    /**
     * Adds a new song to the songs list.
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Adds a new podcast to the podcasts list.
     */
    public void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * Adds a new user to the users list.
     */
    public void addUser(final User user) {
        users.add(user);
    }

    /**
     * Adds a new playlist to the playlists list.
     */
    public void addPlaylist(final Playlist playlist) {
        playlists.add(playlist);
    }

    /**
     * Traverses the song database and returns the instance of the
     * requested song, if it exists.
     * @return Song instance for success, null otherwise.
     * @throws IllegalArgumentException if the song is not found.
     */
    public Song searchSongInDatabase(final Song reqSong) {
        for (Song song : songs) {
            if (song.getName().equals(reqSong.getName())
                && song.getArtist().equals(reqSong.getArtist())) {
                return song;
            }
        }
        throw new IllegalArgumentException("Critical: Song not found in the database.");
    }

    /* Getters and Setters */
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
