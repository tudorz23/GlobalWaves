package database;

import fileio.input.SongInput;
import utils.AudioType;
import utils.PlayerState;
import utils.RepeatState;

import java.util.ArrayList;

public class Song extends Audio {
    private Integer duration;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;
    private int timePosition; // song time at last known moment
    private int likeCnt;

    /* Constructors */
    private Song() {
    }

    public Song(SongInput songInput) {
        super();
        this.setName(songInput.getName());
        this.duration = songInput.getDuration();
        this.album = songInput.getAlbum();
        this.tags = songInput.getTags();
        this.lyrics = songInput.getLyrics();
        this.genre = songInput.getGenre();
        this.releaseYear = songInput.getReleaseYear();
        this.artist = songInput.getArtist();
        setType(AudioType.SONG);
        likeCnt = 0;
    }

    @Override
    public Song getDeepCopy() {
        Song copy = new Song();
        copy.setName(this.getName());
        copy.setType(this.getType());
        copy.setDuration(this.duration);
        copy.setAlbum(this.album);
        copy.tags = new ArrayList<>();
        copy.tags.addAll(this.tags);
        copy.setLyrics(this.lyrics);
        copy.setGenre(this.genre);
        copy.setReleaseYear(this.releaseYear);
        copy.setArtist(this.artist);
        copy.timePosition = 0;
        copy.likeCnt = this.likeCnt;

        return copy;
    }

    @Override
    public void simulateTimePass(Player player, int currTime) {
        if (player.getPlayerState() == PlayerState.PAUSED
            || player.getPlayerState() == PlayerState.STOPPED) {
            return;
        }

        int elapsedTime = currTime - player.getPrevTimeInfo();

        if (player.getRepeatState() == RepeatState.REPEAT_INFINITE) {
            simulateRepeatInfinite(elapsedTime);
            return;
        }

        if (player.getRepeatState() == RepeatState.REPEAT_ONCE) {
            simulateRepeatOnce(player, elapsedTime);
            return;
        }

        simulateNoRepeat(player, elapsedTime);
    }

    /**
     * Simulates the time passing when Repeat Infinite is on.
     */
    private void simulateRepeatInfinite(int elapsedTime) {
        int newTimePos = (timePosition + elapsedTime) % duration;
        timePosition = newTimePos;
    }

    /**
     * Simulates the time passing when Repeat Once is on.
     */
    private void simulateRepeatOnce(Player player, int elapsedTime) {
        int quotient = (timePosition + elapsedTime) / duration;
        int remainder = (timePosition + elapsedTime) % duration;

        if (quotient == 0) {
            // No repeat necessary.
            timePosition = remainder;
            return;
        }

        if (quotient == 1) {
            // Repeat it once and set repeat state to No repeat.
            player.setRepeatState(RepeatState.NO_REPEAT);
            timePosition = remainder;
            return;
        }

        // quotient > 1. Surely, the player needs to be stopped.
        player.setPlayerState(PlayerState.STOPPED);
        player.setRepeatState(RepeatState.NO_REPEAT);
        timePosition = duration;
    }

    /**
     * Simulates the time passing when No repeat is on.
     */
    private void simulateNoRepeat(Player player, int elapsedTime) {
        int quotient = (timePosition + elapsedTime) / duration;
        int remainder = (timePosition + elapsedTime) % duration;

        if (quotient == 0) {
            // Song did not end.
            timePosition = remainder;
            return;
        }

        // Surely, song ended.
        player.setPlayerState(PlayerState.STOPPED);
        timePosition = duration;
    }

    @Override
    public int getRemainedTime() {
        return (duration - timePosition);
    }

    /* Getters and Setters */
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    public String getLyrics() {
        return lyrics;
    }
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public Integer getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public int getTimePosition() {
        return timePosition;
    }
    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }
    public int getLikeCnt() {
        return likeCnt;
    }
    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }
}
