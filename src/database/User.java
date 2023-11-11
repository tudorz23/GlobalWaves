package database;

import fileio.input.UserInput;

import java.util.ArrayList;

public class User {
    private String username;
    private int age;
    private String city;
    private ArrayList<Audio> searchResult;
    private Audio selection;
    private Player player;
    private ArrayList<Playlist> playlists;
    private ArrayList<Podcast> listenedPodcasts;

    /* Constructor */
    public User(UserInput userInput) {
        this.username = userInput.getUsername();
        this.age = userInput.getAge();
        this.city = userInput.getCity();
        this.searchResult = new ArrayList<>();
        this.player = new Player();
        this.playlists = new ArrayList<>();
        this.listenedPodcasts = new ArrayList<>();
    }

    /* Getters and Setters */
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public ArrayList<Audio> getSearchResult() {
        return searchResult;
    }
    public Audio getSelection() {
        return selection;
    }
    public void setSelection(Audio selection) {
        this.selection = selection;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
    public ArrayList<Podcast> getListenedPodcasts() {
        return listenedPodcasts;
    }
}
