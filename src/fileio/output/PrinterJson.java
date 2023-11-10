package fileio.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.User;
import fileio.input.CommandInput;
import fileio.input.FiltersInput;

import java.util.ArrayList;
import java.util.List;

public class PrinterJson {
    private final ObjectMapper mapper = new ObjectMapper();

    public void printUsers(ArrayList<User> users, ArrayNode output) {
        ArrayNode userArrayNode = getUserArrayNode(users);

        ObjectNode userField = mapper.createObjectNode();
        userField.set("users", userArrayNode);

        output.add(userField);
    }

    /**
     * Converts a user object to an ObjectNode for JSON printing.
     */
    private ObjectNode getUserNode(User user) {
        ObjectNode userNode = mapper.createObjectNode();

        userNode.put("username", user.getUsername());
        userNode.put("age", user.getAge());
        userNode.put("city", user.getCity());

        return userNode;
    }

    private ArrayNode getUserArrayNode(ArrayList<User> users) {
        ArrayNode userArrayNode = mapper.createArrayNode();

        for (User user : users) {
            ObjectNode userNode = getUserNode(user);
            userArrayNode.add(userNode);
        }

        return userArrayNode;
    }

    private ObjectNode getFiltersNode(FiltersInput filtersInput) {


        if (filtersInput == null) {
            return null;
        }
        ObjectNode filterNode = mapper.createObjectNode();
        filterNode.put("name", filtersInput.getName());
        filterNode.put("album", filtersInput.getAlbum());


        if (filtersInput.getTags() != null) {
            ArrayNode tags = mapper.createArrayNode();
            for (String tag : filtersInput.getTags()) {
                tags.add(tag);
            }

            filterNode.set("tags", tags);
        }

        filterNode.put("lyrics", filtersInput.getLyrics());
        filterNode.put("genre", filtersInput.getGenre());
        filterNode.put("releaseYear", filtersInput.getReleaseYear());
        filterNode.put("artist", filtersInput.getArtist());
        filterNode.put("owner", filtersInput.getOwner());

        return filterNode;
    }

    private ObjectNode getCommandNode(CommandInput commandInput) {
        ObjectNode commandNode = mapper.createObjectNode();

        commandNode.put("command", commandInput.getCommand());
        commandNode.put("username", commandInput.getUsername());
        commandNode.put("timestamp", commandInput.getTimestamp());
        commandNode.put("type", commandInput.getType());

        commandNode.set("filters", getFiltersNode(commandInput.getFilters()));

        commandNode.put("itemNumber", commandInput.getItemNumber());
        commandNode.put("playlistId", commandInput.getPlaylistId());
        commandNode.put("playlistName", commandInput.getPlaylistName());
        commandNode.put("seed", commandInput.getSeed());

        return commandNode;
    }

    private ArrayNode getCommandArrayNode(List<CommandInput> commands) {
        ArrayNode commandArray = mapper.createArrayNode();

        for (CommandInput commandInput : commands) {
            ObjectNode command = getCommandNode(commandInput);
            commandArray.add(command);
        }

        return commandArray;
    }

    public void printCommands(List<CommandInput> commands, ArrayNode output) {
        ArrayNode commandArrayNode = getCommandArrayNode(commands);

        ObjectNode commandField = mapper.createObjectNode();
        commandField.set("commands", commandArrayNode);

        output.add(commandField);
    }
}
