package database;

import fileio.input.UserInput;

import java.util.ArrayList;

public class User {
    private String username;
    private int age;
    private String city;
    private ArrayList<Audio> searchResult;  // null when no search has been done
    private Audio selection;
    private Player player;
    private ArrayList<Playlist> playlists;
    private ArrayList<Podcast> listenedPodcasts;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;

    /* Constructor */
    public User(UserInput userInput) {
        this.username = userInput.getUsername();
        this.age = userInput.getAge();
        this.city = userInput.getCity();
        this.searchResult = null;
        this.player = new Player();
        this.playlists = new ArrayList<>();
        this.listenedPodcasts = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
    }

    /**
     * Adds a new Playlist to user's playlists.
     * @return true, for success, false, if a playlist with the
     * same name already exists in user's list.
     */
    public boolean addPlaylist(Playlist newPlaylist) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(newPlaylist.getName())) {
                return false;
            }
        }

        playlists.add(newPlaylist);
        return true;
    }

    public void addFollowedPlaylist(Playlist playlist) {
        followedPlaylists.add(playlist);
    }

    public void removeFollowedPlaylist(Playlist playlist) {
        followedPlaylists.remove(playlist);
    }

    public void addLikedSong(Song song) {
        likedSongs.add(song);
    }

    public void removeLikedSong(Song song) {
        likedSongs.remove(song);
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
    public void setSearchResult(ArrayList<Audio> searchResult) {
        this.searchResult = searchResult;
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
    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }
    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }
}
