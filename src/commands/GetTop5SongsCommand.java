package commands;

import client.Session;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Song;
import fileio.input.CommandInput;
import fileio.output.PrinterTop5Songs;
import java.util.ArrayList;
import static utils.Constants.MAX_SONG_RANK_NUMBER;

public final class GetTop5SongsCommand implements ICommand {
    private final Session session;
    private final CommandInput commandInput;
    private final ArrayNode output;

    /* Constructor */
    public GetTop5SongsCommand(final Session session, final CommandInput commandInput,
                               final ArrayNode output) {
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

        // Lambda expression to sort the songs by the likes number, decreasingly.
        allSongs.sort((song1, song2) -> song2.getLikeCnt() - song1.getLikeCnt());

        while (allSongs.size() > MAX_SONG_RANK_NUMBER) {
            allSongs.remove((allSongs.size() - 1));
        }

        printer.print(allSongs);
    }
}
