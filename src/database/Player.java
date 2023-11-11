package database;

import utils.PlayPauseState;
import utils.RepeatState;

public class Player {
    private Audio currPlaying;
    private PlayPauseState playPauseState;
    private RepeatState repeatState;

    /* Constructor */
    public Player(RepeatState repeatState) {
        this.playPauseState = PlayPauseState.PLAYING;
        this.repeatState = repeatState;
    }

    /* Getters and Setters */
    public Audio getCurrPlaying() {
        return currPlaying;
    }
    public void setCurrPlaying(Audio currPlaying) {
        this.currPlaying = currPlaying;
    }
    public PlayPauseState getPlayPauseState() {
        return playPauseState;
    }
    public void setPlayPauseState(PlayPauseState playPauseState) {
        this.playPauseState = playPauseState;
    }
    public RepeatState getRepeatState() {
        return repeatState;
    }
    public void setRepeatState(RepeatState repeatState) {
        this.repeatState = repeatState;
    }
}
