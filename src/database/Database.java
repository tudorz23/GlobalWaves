package database;

import java.util.ArrayList;

public class Database {
    private ArrayList<Song> songs;
    private ArrayList<Podcast> podcasts;
    private ArrayList<User> users;


    /* Constructor */
    public Database() {
        this.songs = new ArrayList<>();
        this.podcasts = new ArrayList<>();
        this.users = new ArrayList<>();
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
}
