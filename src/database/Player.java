package database;

import utils.PlayerState;
import utils.RepeatState;

public class Player {
    private Audio currPlaying;
    private PlayerState playerState;
    private RepeatState repeatState;
    private int prevTimeInfo; // previous time when internal states where updated
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

    /**
     * Simulates the passing of the time between two interactions with the player,
     * updating its internal states.
     * @param currTime Current timestamp of the query.
     */
    public void simulateTimePass(int currTime) {
        currPlaying.simulateTimePass(this, currTime);

        // Update previous time from the player.
        this.setPrevTimeInfo(currTime);
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
    public boolean isShuffle() {
        return shuffle;
    }
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }
}
