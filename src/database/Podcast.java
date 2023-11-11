package database;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.AudioType;

import java.util.ArrayList;

public class Podcast extends Audio {
    private String owner;
    private ArrayList<Episode> episodes;
    private Episode lastPlaying; // last episode playing before interruption

    /* Constructor */
    public Podcast(PodcastInput podcastInput) {
        super();
        this.setName(podcastInput.getName());
        this.owner = podcastInput.getOwner();
        this.episodes = initializeEpisodes(podcastInput.getEpisodes());
        setType(AudioType.PODCAST);
        this.lastPlaying = episodes.get(0);
    }

    /**
     * Helper for initializing the episodes field.
     * @param episodeInputs list of EpisodeInputs objects to be converted to Episode.
     */
    private ArrayList<Episode> initializeEpisodes(ArrayList<EpisodeInput> episodeInputs) {
        ArrayList<Episode> episodeList = new ArrayList<>();

        for (EpisodeInput episodeInput : episodeInputs) {
            Episode episode = new Episode(episodeInput);
            episodeList.add(episode);
        }

        return episodeList;
    }


    /* Getters and Setters */
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }
    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
    public Episode getLastPlaying() {
        return lastPlaying;
    }
    public void setLastPlaying(Episode lastPlaying) {
        this.lastPlaying = lastPlaying;
    }
}
