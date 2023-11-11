package database;

import fileio.input.SongInput;
import utils.AudioType;
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
        copy.timePosition = 0;

        return copy;
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
}
