package database;

import utils.AudioType;
import utils.Visibility;

import java.util.ArrayList;

public class Playlist extends Audio {
    private String name;
    private String owner;
    private Visibility visibility;
    private ArrayList<Song> songs;

    /* Constructor */
    public Playlist(String name, String owner) {
        super();
        this.name = name;
        this.owner = owner;
        this.visibility = Visibility.PUBLIC;
        this.songs = new ArrayList<>();
        setType(AudioType.PLAYLIST);
    }

    /**
     * Adds a song to the playlist.
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Removes a song from the playlist.
     */
    public void removeSong(Song song) {
        songs.remove(song);
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }
    public String getOwner() {
        return owner;
    }
    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
