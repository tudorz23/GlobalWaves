package fileio.input;

public final class CommandInput {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private FiltersInput filters;
    private int itemNumber;
    private int playlistId;
    private String playlistName;
    private int seed;

    /* Constructor */
    public CommandInput() { }

    /* Getters and Setters */
    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public FiltersInput getFilters() {
        return filters;
    }

    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(final int seed) {
        this.seed = seed;
    }
}
