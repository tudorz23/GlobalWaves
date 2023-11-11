package database;

import fileio.input.EpisodeInput;

public class Episode {
    private String name;
    private Integer duration;
    private String description;
    private int timePosition; // time of the episode at last known moment

    /* Constructors */
    private Episode() {
    }

    public Episode(EpisodeInput episodeInput) {
        this.name = episodeInput.getName();
        this.duration = episodeInput.getDuration();
        this.description = episodeInput.getDescription();
        this.timePosition = 0;
    }

    /**
     * @return Deep copy of the Episode object.
     */
    public Episode getDeepCopy() {
        Episode copy = new Episode();
        copy.name = this.name;
        copy.duration = this.duration;
        copy.description = this.description;
        copy.timePosition = 0;

        return copy;
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getTimePosition() {
        return timePosition;
    }
    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }
}
