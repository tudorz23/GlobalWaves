package database;

import utils.AudioType;

/**
 * Describes audio objects that can be searched by the user.
 * To be extended by classes Song, Playlist and Podcast.
 */
public abstract class Audio {
    private String name;
    private AudioType type;

    /* Constructor */
    public Audio() {
    }

    /**
     * @return Deep copy of Audio object.
     */
    public abstract Audio getDeepCopy();

    /**
     * Simulates the passing ot the time between last update moment
     * and current timestamp.
     */
    public abstract void simulateTimePass(Player player, int currTime);

    /**
     * @return Time remained until track ends.
     */
    public abstract int getRemainedTime();

    /**
     * Moves the player to the next track.
     */
    public abstract void next(Player player);

    /**
     * Moves the player to the previous track.
     */
    public abstract void prev(Player player);

    /**
     * @return Name of the currently playing track.
     */
    public abstract String getPlayingTrackName();

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
