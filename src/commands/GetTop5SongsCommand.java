package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Song;
import fileio.input.CommandInput;
import fileio.output.PrinterTop5Songs;

import java.util.ArrayList;
import java.util.Comparator;

public class GetTop5SongsCommand implements ICommand {
    private Session session;
    private CommandInput commandInput;
    private ArrayNode output;

    /* Constructor */
    public GetTop5SongsCommand(Session session, CommandInput commandInput,
                               ArrayNode output) {
        this.session = session;
        this.commandInput = commandInput;
        this.output = output;
    }

    @Override
    public void execute() {
        session.setTimestamp(commandInput.getTimestamp());
        PrinterTop5Songs printer = new PrinterTop5Songs(session, output);

        ArrayList<Song> allSongs = new ArrayList<>();
        allSongs.addAll(session.getDatabase().getSongs());

        allSongs.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song2.getLikeCnt() - song1.getLikeCnt();
            }
        });

        while (allSongs.size() > 5) {
            allSongs.remove((allSongs.size() - 1));
        }

        printer.print(allSongs);
    }
}
