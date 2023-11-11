package database;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.AudioType;

import java.util.ArrayList;

public class Podcast extends Audio {
    private String owner;
    private ArrayList<Episode> episodes;
    private Episode lastPlaying; // episode playing at last known moment

    /* Constructors */
    private Podcast() {
    }

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

    @Override
    public Podcast getDeepCopy() {
        Podcast copy = new Podcast();
        copy.setName(this.getName());
        copy.setType(this.getType());
        copy.owner = this.owner;
        copy.episodes = new ArrayList<>();

        for (Episode episode : this.episodes) {
            copy.episodes.add(episode.getDeepCopy());
        }

        copy.lastPlaying = copy.episodes.get(0);

        return copy;
    }

    @Override
    public void simulateTimePass(Player player, int currTime) {
        // TODO
    }

    @Override
    public int getRemainedTime() {
        // TODO
        return 0;
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
