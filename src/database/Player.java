package database;

import utils.PlayerState;
import utils.RepeatState;

public class Player {
    private Audio currPlaying;
    private PlayerState playerState;
    private RepeatState repeatState;
    private int prevTimeInfo; // previous time when internal states where updated
    private boolean repeatedOnce; // to know if repeated once has been done
    private boolean shuffle;

    /* Constructor */
    public Player() {
        playerState = PlayerState.EMPTY;
        shuffle = false;
    }

    /**
     * Clears the user's player (i.e. nothing is playing anymore).
     */
    public void emptyPlayer() {
        playerState = PlayerState.EMPTY;
    }

    public void simulateTimePass(int currTime) {
        currPlaying.simulateTimePass(this, currTime);
    }

    /* Getters and Setters */
    public Audio getCurrPlaying() {
        return currPlaying;
    }
    public void setCurrPlaying(Audio currPlaying) {
        this.currPlaying = currPlaying;
    }
    public PlayerState getPlayerState() {
        return playerState;
    }
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
    public RepeatState getRepeatState() {
        return repeatState;
    }
    public void setRepeatState(RepeatState repeatState) {
        this.repeatState = repeatState;
    }
    public int getPrevTimeInfo() {
        return prevTimeInfo;
    }
    public void setPrevTimeInfo(int prevTimeInfo) {
        this.prevTimeInfo = prevTimeInfo;
    }
    public boolean hasRepeatedOnce() {
        return repeatedOnce;
    }
    public void setRepeatedOnce(boolean repeatedOnce) {
        this.repeatedOnce = repeatedOnce;
    }
    public boolean isShuffle() {
        return shuffle;
    }
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }
}
