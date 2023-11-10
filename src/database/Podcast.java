package database;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.AudioType;

import java.util.ArrayList;

public class Podcast extends Audio {
    private String name;
    private String owner;
    private ArrayList<Episode> episodes;

    /* Constructor */
    public Podcast(PodcastInput podcastInput) {
        super();
        this.name = podcastInput.getName();
        this.owner = podcastInput.getOwner();
        this.episodes = initializeEpisodes(podcastInput.getEpisodes());
        setType(AudioType.PODCAST);
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
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
}
