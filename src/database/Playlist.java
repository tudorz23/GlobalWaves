package database;

import utils.AudioType;
import utils.PlayerState;
import utils.RepeatState;
import utils.Visibility;
import java.util.ArrayList;

public final class Playlist extends Audio {
    private String owner;
    private Visibility visibility;
    private ArrayList<Song> songs;
    private int playingSongIndex; // Index from the songs array.
    private int followersCnt;
    private ArrayList<Integer> shuffleArray;

    /* Constructor */
    public Playlist(final String name, final String owner) {
        super(name);
        this.owner = owner;
        this.visibility = Visibility.PUBLIC;
        this.songs = new ArrayList<>();
        this.setType(AudioType.PLAYLIST);
        this.followersCnt = 0;
    }

    @Override
    public Playlist getDeepCopy() {
        Playlist copy = new Playlist(this.getName(), this.getOwner());

        for (Song song : this.songs) {
            copy.songs.add(song.getDeepCopy());
        }

        copy.playingSongIndex = 0;
        copy.followersCnt = this.followersCnt;

        // Initialize the shuffle array with the array v[i] = i (i.e. un-shuffled).
        copy.shuffleArray = new ArrayList<>();
        for (int i = 0; i < songs.size(); i++) {
            copy.shuffleArray.add((i));
        }

        return copy;
    }

    @Override
    public void simulateTimePass(final Player player, final int currTime) {
        if (player.getPlayerState() == PlayerState.PAUSED
                || player.getPlayerState() == PlayerState.STOPPED) {
            return;
        }

        int elapsedTime = currTime - player.getPrevTimeInfo();
        int songRemainingTime = songs.get(playingSongIndex).getRemainedTime();

        while (elapsedTime >= songRemainingTime) {
            if (player.getPlayerState() == PlayerState.STOPPED) {
                return;
            }

            changeToNextSong(player);
            elapsedTime -= songRemainingTime;

            songRemainingTime = songs.get(playingSongIndex).getRemainedTime();
        }

        if (elapsedTime == 0) {
            return;
        }

        Song playingSong = songs.get(playingSongIndex);
        int songNewTimePos = playingSong.getTimePosition() + elapsedTime;
        playingSong.setTimePosition(songNewTimePos);
    }

    /**
     * Moves to the next song in the playlist, considering the shuffled state
     * of the playlist and the repeat state.
     */
    private void changeToNextSong(final Player player) {
        if (player.getRepeatState() == RepeatState.REPEAT_CURR_SONG_PLAYLIST) {
            songs.get(playingSongIndex).resetTimePosition();
            return;
        }

        int shuffleIndex = getShuffleIndex(playingSongIndex);

        if (shuffleIndex == shuffleArray.size() - 1
                && player.getRepeatState() == RepeatState.NO_REPEAT_PLAYLIST) {
            // If No repeat is enabled and last song is reached, stop the player.
            Song currSong = songs.get(playingSongIndex);
            currSong.setTimePosition(currSong.getDuration());

            player.setPlayerState(PlayerState.STOPPED);
            player.setShuffle(false);
            return;
        }

        // Surely, it is either not last song or Repeat all is enabled.
        int nextShuffleIdx = (shuffleIndex + 1) % (shuffleArray.size());
        int nextSongIndex = shuffleArray.get(nextShuffleIdx);

        this.playingSongIndex = nextSongIndex;
        Song newSong = songs.get(nextSongIndex);
        newSong.setTimePosition(0);
    }

    /**
     * @return Index of the shuffleArray where the songArrayIndex
     * is found as a value.
     * @param songArrayIndex index from the array of songs.
     * @throws IllegalArgumentException if the passed index is out of bounds.
     */
    private int getShuffleIndex(final int songArrayIndex) {
        for (int i = 0; i < shuffleArray.size(); i++) {
            if (shuffleArray.get(i) == songArrayIndex) {
                return i;
            }
        }

        // Should never be reached.
        throw new IllegalArgumentException("Invalid index argument passed!");
    }

    @Override
    public int getRemainedTime() {
        Song currPlayingSong = songs.get(playingSongIndex);
        return currPlayingSong.getRemainedTime();
    }

    @Override
    public void next(final Player player) {
        changeToNextSong(player);

        if (player.getPlayerState() == PlayerState.PAUSED) {
            player.setPlayerState(PlayerState.PLAYING);
        }
    }

    @Override
    public void prev(final Player player) {
        Song playingSong = songs.get(playingSongIndex);

        if (playingSong.getTimePosition() != 0) {
            // At least 1 second passed.
            playingSong.setTimePosition(0);

            if (player.getPlayerState() == PlayerState.PAUSED) {
                player.setPlayerState(PlayerState.PLAYING);
            }
            return;
        }

        // No second passed.
        changeToPrevSong();

        if (player.getPlayerState() == PlayerState.PAUSED) {
            player.setPlayerState(PlayerState.PLAYING);
        }
    }

    /**
     * Moves to the previous song in the playlist, considering the shuffle state
     * of the playlist.
     */
    private void changeToPrevSong() {
        int shuffleIndex = getShuffleIndex(playingSongIndex);

        if (shuffleIndex == 0) {
            // First song.
            return;
        }

        int prevShuffleIndex = shuffleIndex - 1;
        int prevSongIndex = shuffleArray.get(prevShuffleIndex);

        playingSongIndex = prevSongIndex;
        Song prevSong = songs.get(prevSongIndex);
        prevSong.setTimePosition(0);
    }

    @Override
    public String getPlayingTrackName() {
        Song currPlayingSong = songs.get(playingSongIndex);
        return currPlayingSong.getName();
    }

    /**
     * Adds a song to the playlist.
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Removes a song from the playlist.
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * Increments the number of followers.
     */
    public void incrementFollowersCnt() {
        followersCnt++;
    }

    /**
     * Decrements the number of followers.
     */
    public void decrementFollowersCnt() {
        followersCnt--;
    }

    /* Getters and Setters */
    public String getOwner() {
        return owner;
    }
    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(final Visibility visibility) {
        this.visibility = visibility;
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }
    public int getPlayingSongIndex() {
        return playingSongIndex;
    }
    public int getFollowersCnt() {
        return followersCnt;
    }
    public ArrayList<Integer> getShuffleArray() {
        return shuffleArray;
    }
}
