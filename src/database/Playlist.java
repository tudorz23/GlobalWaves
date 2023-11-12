package database;

import utils.AudioType;
import utils.PlayerState;
import utils.RepeatState;
import utils.Visibility;
import java.util.ArrayList;

public class Playlist extends Audio {
    private String owner;
    private Visibility visibility;
    private ArrayList<Song> songs;
    private int playingSongIndex;

    /* Constructor */
    public Playlist(String name, String owner) {
        super();
        this.setName(name);
        this.owner = owner;
        this.visibility = Visibility.PUBLIC;
        this.songs = new ArrayList<>();
        setType(AudioType.PLAYLIST);
    }

    @Override
    public Playlist getDeepCopy() {
        Playlist copy = new Playlist(this.getName(), this.getOwner());
        copy.songs = new ArrayList<>();

        for (Song song : this.songs) {
            copy.songs.add(song.getDeepCopy());
        }

        copy.playingSongIndex = 0;
        return copy;
    }

    @Override
    public void simulateTimePass(Player player, int currTime) {
        // TODO
        if (player.getPlayerState() == PlayerState.PAUSED
                || player.getPlayerState() == PlayerState.STOPPED) {
            return;
        }

        int elapsedTime = currTime - player.getPrevTimeInfo();

        if (player.getRepeatState() == RepeatState.REPEAT_CURR_SONG_PLAYLIST) {
            simulateRepeatCurrSong(player, elapsedTime);
            return;
        }

        // No repeat or Repeat all state.
        while (elapsedTime > 0) {
            Song playingSong = songs.get(playingSongIndex);
            int songRemainedTime = playingSong.getRemainedTime();

            // If the player stopped, return.
            if (player.getPlayerState() == PlayerState.STOPPED) {
                return;
            }

            if (songRemainedTime <= elapsedTime) {
                changeToNextSong(player);
                elapsedTime -= songRemainedTime;
                continue;
            }

            int songNewTimePos = playingSong.getTimePosition() + elapsedTime;
            playingSong.setTimePosition(songNewTimePos);
            elapsedTime = 0;
        }
    }

    /**
     * Moves to the next song in the playlist, considering the repeat state.
     */
    private void changeToNextSong(Player player) {
        if (playingSongIndex == songs.size() - 1
                && player.getRepeatState() == RepeatState.NO_REPEAT_PLAYLIST) {
            // If No repeat is enabled and last song is reached, stop the player.
            Song currSong = songs.get(playingSongIndex);
            currSong.setTimePosition(currSong.getDuration());
            player.setPlayerState(PlayerState.STOPPED);
            return;
        }

        // Surely, it is either not last song or Repeat all is enabled.
        int nextSongIndex = (playingSongIndex + 1) % (songs.size());
        this.playingSongIndex = nextSongIndex;
        Song newSong = songs.get(nextSongIndex);
        newSong.setTimePosition(0);
    }

    /**
     * Sets the new Time position for the currently playing song.
     */
    private void simulateRepeatCurrSong(Player player, int elapsedTime) {
        Song currSong = songs.get(playingSongIndex);
        int newTimePos = (currSong.getTimePosition() + elapsedTime) % currSong.getDuration();
        currSong.setTimePosition(newTimePos);
    }

    @Override
    public int getRemainedTime() {
        // TODO
        return 0;
    }

    /**
     * Adds a song to the playlist.
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Removes a song from the playlist.
     */
    public void removeSong(Song song) {
        songs.remove(song);
    }

    /* Getters and Setters */
    public String getOwner() {
        return owner;
    }
    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
    public int getPlayingSongIndex() {
        return playingSongIndex;
    }
    public void setPlayingSongIndex(int playingSongIndex) {
        this.playingSongIndex = playingSongIndex;
    }
}
