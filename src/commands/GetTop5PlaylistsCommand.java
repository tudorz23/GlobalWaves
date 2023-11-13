package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Playlist;
import fileio.input.CommandInput;
import fileio.output.PrinterTop5Playlists;
import utils.Visibility;

import java.util.ArrayList;
import java.util.Comparator;

public class GetTop5PlaylistsCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private ArrayNode output;

    /* Constructor */
    public GetTop5PlaylistsCommand(Session session, CommandInput commandInput,
                               ArrayNode output) {
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

        publicPlaylists.sort(new Comparator<Playlist>() {
            @Override
            public int compare(Playlist playlist1, Playlist playlist2) {
                return playlist2.getFollowersCnt() - playlist1.getFollowersCnt();
            }
        });

        while (publicPlaylists.size() > 5) {
            publicPlaylists.remove(publicPlaylists.size() - 1);
        }

        printer.print(publicPlaylists);
    }
}
