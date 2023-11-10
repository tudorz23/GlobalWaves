package client;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import fileio.input.LibraryInput;

public class AdminInteraction {


    private final LibraryInput libraryInput;
    private final CommandInput commandInput;
    private ArrayNode output;

    /* Constructor */
    public AdminInteraction(LibraryInput libraryInput, CommandInput commandInput, ArrayNode output) {
        this.libraryInput = libraryInput;
        this.commandInput = commandInput;
        this.output = output;
    }
}
