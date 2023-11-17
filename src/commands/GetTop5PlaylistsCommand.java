package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Playlist;
import fileio.input.CommandInput;
import fileio.output.PrinterTop5Playlists;
import utils.Visibility;
import java.util.ArrayList;
import static utils.Constants.MAX_PLAYLIST_RANK_NUMBER;

public final class GetTop5PlaylistsCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final ArrayNode output;

    /* Constructor */
    public GetTop5PlaylistsCommand(final Session session, final CommandInput commandInput,
                                   final ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterTop5Playlists printer = new PrinterTop5Playlists(session, output);

        ArrayList<Playlist> publicPlaylists = new ArrayList<>();
        for (Playlist playlist : session.getDatabase().getPlaylists()) {
            if (playlist.getVisibility() == Visibility.PUBLIC) {
                publicPlaylists.add(playlist);
            }
        }

        // Lambda expression to sort the playlists by the followers count, decreasingly.
        // Because playlists are added to the Database as they are created, they are
        // automatically sorted by "age" in case of equality for followers.
        publicPlaylists.sort((playlist1, playlist2) -> playlist2.getFollowersCnt()
                                - playlist1.getFollowersCnt());

        while (publicPlaylists.size() > MAX_PLAYLIST_RANK_NUMBER) {
            publicPlaylists.remove(publicPlaylists.size() - 1);
        }

        printer.print(publicPlaylists);
    }
}
