package database;

import java.util.ArrayList;

public class Database {
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
     * Adds a new song to the song list.
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Adds a new podcast to the podcast list.
     */
    public void addPodcast(Podcast podcast) {
        podcasts.add(podcast);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    /**
     * Traverses the song database and returns the instance of the
     * requested song, if it exists.
     * @return song instance for success, null otherwise.
     */
    public Song searchSongInDatabase(Song reqSong) {
        for (Song song : songs) {
            if (song.getName().equals(reqSong.getName())
                && song.getArtist().equals(reqSong.getArtist())) {
                return song;
            }
        }
        return null;
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
