package database;

import fileio.input.EpisodeInput;
import utils.RepeatState;

public final class Episode {
    private String name;
    private Integer duration;
    private String description;
    private int timePosition; // Episode time at last known moment.

    /* Constructors */
    private Episode() {
    }

    public Episode(final EpisodeInput episodeInput) {
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

    /**
     * @return Time remained until episode ends.
     */
    public int getRemainedTime() {
        return (duration - timePosition);
    }

    /**
     * Simulates the time passing when Repeat Infinite on an Episode is on.
     */
    public void repeatInfinite(final int elapsedTime) {
        timePosition = (timePosition + elapsedTime) % duration;
    }

    /**
     * Simulates the time passing when Repeat Once on an Episode is on.
     * @param player User's player.
     * @param elapsedTime Time to be simulated.
     * @return Remaining time to be simulated.
     */
    public int repeatOnce(final Player player, final int elapsedTime) {
        int quotient = (timePosition + elapsedTime) / duration;
        int remainder = (timePosition + elapsedTime) % duration;

        if (quotient == 0) {
            // No repeat necessary.
            timePosition = remainder;
            return 0;
        }

        if (quotient == 1) {
            // Repeat it once and set repeat state to No repeat.
            player.setRepeatState(RepeatState.NO_REPEAT);
            timePosition = remainder;
            return 0;
        }

        // quotient > 1. Repeat it once, then calculate the remaining time.
        int remainingTimeToSimulate = elapsedTime - getRemainedTime() - duration;

        player.setRepeatState(RepeatState.NO_REPEAT);
        timePosition = duration;
        return remainingTimeToSimulate;
    }

    /**
     * Sets the time position to 0.
     */
    public void resetTimePosition() {
        timePosition = 0;
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public Integer getDuration() {
        return duration;
    }
    public String getDescription() {
        return description;
    }
    public int getTimePosition() {
        return timePosition;
    }
    public void setTimePosition(final int timePosition) {
        this.timePosition = timePosition;
    }
}
