package database;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import utils.AudioType;
import utils.PlayerState;
import utils.RepeatState;

import java.util.ArrayList;

public class Podcast extends Audio {
    private String owner;
    private ArrayList<Episode> episodes;
    private int playingEpisodeIdx;

    /* Constructors */
    private Podcast() {
    }

    public Podcast(PodcastInput podcastInput) {
        super();
        this.setName(podcastInput.getName());
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

        copy.playingEpisodeIdx = 0;
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

        while (elapsedTime > 0) {
            Episode playingEpisode = episodes.get(playingEpisodeIdx);
            int episodeRemainedTime = playingEpisode.getRemainedTime();

            // If the player stopped, return.
            if (player.getPlayerState() == PlayerState.STOPPED) {
                return;
            }

            if (episodeRemainedTime <= elapsedTime) {
                changeToNextEpisode(player);
                elapsedTime -= episodeRemainedTime;
                continue;
            }

            int episodeNewTimePos = playingEpisode.getTimePosition() + elapsedTime;
            playingEpisode.setTimePosition(episodeNewTimePos);
            elapsedTime = 0;
        }
    }

    /**
     * Moves to the next episode, considering the repeat state.
     */
    private void changeToNextEpisode(Player player) {
        if (playingEpisodeIdx == episodes.size() - 1
            && player.getRepeatState() == RepeatState.NO_REPEAT) {
            // If no repeat is enabled and last episode is reached, stop the player.
            Episode currEpisode = episodes.get(playingEpisodeIdx);
            currEpisode.setTimePosition(currEpisode.getDuration());
            player.setPlayerState(PlayerState.STOPPED);
            return;
        }

        if (playingEpisodeIdx == episodes.size() - 1
            && player.getRepeatState() == RepeatState.REPEAT_ONCE) {
            if (player.hasRepeatedOnce()) {
                // If it already repeated once, stop the player.
                Episode currEpisode = episodes.get(playingEpisodeIdx);
                currEpisode.setTimePosition(currEpisode.getDuration());
                player.setPlayerState(PlayerState.STOPPED);
                return;
            }

            player.setRepeatedOnce(true);
        }

        // Surely, it is either not last episode or Repeat is enabled.
        int nextEpisodeIdx = (playingEpisodeIdx + 1) % (episodes.size());
        this.playingEpisodeIdx = nextEpisodeIdx;
        Episode newEpisode = episodes.get(nextEpisodeIdx);
        newEpisode.setTimePosition(0);
    }

    @Override
    public int getRemainedTime() {
        Episode playingEpisode = episodes.get(playingEpisodeIdx);
        return playingEpisode.getRemainedTime();
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
    public int getPlayingEpisodeIdx() {
        return playingEpisodeIdx;
    }
    public void setPlayingEpisodeIdx(int playingEpisodeIdx) {
        this.playingEpisodeIdx = playingEpisodeIdx;
    }
}
