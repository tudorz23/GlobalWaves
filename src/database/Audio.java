package database;

import utils.AudioType;

/**
 * Describes audio objects that can be searched by the user.
 * To be extended by classes Song, Playlist and Podcast.
 */
public class Audio {
    private AudioType type;

    /* Constructor */
    public Audio() {

    }

    /* Getters and Setters */
    public AudioType getType() {
        return type;
    }
    public void setType(AudioType type) {
        this.type = type;
    }
}
