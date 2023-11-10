package database;

import utils.AudioType;

/**
 * Describes audio objects that can be searched by the user.
 * To be extended by classes Song, Playlist and Podcast.
 */
public class Audio {
    private String name;
    private AudioType type;

    /* Constructor */
    public Audio() {

    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public AudioType getType() {
        return type;
    }
    public void setType(AudioType type) {
        this.type = type;
    }
}
