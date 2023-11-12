package fileio.output;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.*;
import utils.AudioType;
import utils.PlayerState;

public class PrinterStatus extends Printer {
    private User user;

    /* Constructor */
    public PrinterStatus(User user, Session session, ArrayNode output) {
        super(session, output);
        this.user = user;
    }

    public void print() {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", "status");
        commandNode.put("user", user.getUsername());
        commandNode.put("timestamp", session.getTimestamp());

        Player userPlayer = user.getPlayer();

        ObjectNode stats = mapper.createObjectNode();

        if (userPlayer == null || userPlayer.getPlayerState() == PlayerState.EMPTY) {
            stats.put("name" , "");
            stats.put("remainedTime", 0);
            stats.put("repeat", "No Repeat");
            stats.put("shuffle", false);
            stats.put("paused", true);

            commandNode.set("stats", stats);
            output.add(commandNode);
            return;
        }

        if (userPlayer.getPlayerState() == PlayerState.STOPPED) {
            stats.put("name", "");
        } else {
            if (userPlayer.getCurrPlaying().getType() == AudioType.SONG) {
                stats.put("name", userPlayer.getCurrPlaying().getName());
            } else if (userPlayer.getCurrPlaying().getType() == AudioType.PODCAST) {
                printNamePodcast(stats, userPlayer);
            } else {
                printNamePlaylist(stats, userPlayer);
            }
        }

        stats.put("remainedTime", userPlayer.getCurrPlaying().getRemainedTime());
        stats.put("repeat", userPlayer.getRepeatState().getLabel());
        stats.put("shuffle", userPlayer.isShuffle());

        stats.put("paused", userPlayer.getPlayerState() == PlayerState.PAUSED
                    || userPlayer.getPlayerState() == PlayerState.STOPPED);

        commandNode.set("stats", stats);

        output.add(commandNode);
    }

    private void printNamePodcast(ObjectNode stats, Player player) {
        Podcast playingPodcast = (Podcast) player.getCurrPlaying();
        int episodeIdx = playingPodcast.getPlayingEpisodeIdx();

        Episode playingEpisode = playingPodcast.getEpisodes().get(episodeIdx);

        stats.put("name", playingEpisode.getName());
    }

    private void printNamePlaylist(ObjectNode stats, Player player) {
        Playlist currPlaylist = (Playlist) player.getCurrPlaying();
        int songIdx = currPlaylist.getPlayingSongIndex();

        Song playingSong = currPlaylist.getSongs().get(songIdx);

        stats.put("name", playingSong.getName());
    }
}
