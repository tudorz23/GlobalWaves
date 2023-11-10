package client;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Database;
import database.Podcast;
import database.Song;
import database.User;
import fileio.input.*;
import fileio.output.PrinterJson;

import java.util.List;

public class AdminInteraction {
    private Database database;
    private final LibraryInput libraryInput;
    private final List<CommandInput> commandList;
    private ArrayNode output;

    /* Constructor */
    public AdminInteraction(LibraryInput libraryInput, List<CommandInput> commandList, ArrayNode output) {
        this.libraryInput = libraryInput;
        this.commandList = commandList;
        this.output = output;
    }

    /**
     * Entry point to the program.
     */
    public void startAdminInteraction() {
        prepareDatabase();
        printDatabase();
    }

    /**
     * Populates the database with data taken from the input.
     */
    private void prepareDatabase() {
        this.database = new Database();

        for (SongInput songInput : libraryInput.getSongs()) {
            Song song = new Song(songInput);
            database.addSong(song);
        }

        for (PodcastInput podcastInput : libraryInput.getPodcasts()) {
            Podcast podcast = new Podcast(podcastInput);
            database.addPodcast(podcast);
        }

        for (UserInput userInput : libraryInput.getUsers()) {
            User user =  new User(userInput);
            database.addUser(user);
        }
    }

    private void printDatabase() {
        PrinterJson printerJson = new PrinterJson();
        printerJson.printUsers(database.getUsers(), output);

        printerJson.printCommands(commandList, output);
    }
}
