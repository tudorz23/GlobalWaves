package utils;

public enum CommandType {
    SEARCH("search"),
    SELECT("select"),
    LOAD("load"),
    PLAY_PAUSE("playPause"),
    REPEAT("repeat"),
    SHUFFLE("shuffle"),
    FORWARD("forward"),
    BACKWARD("backward"),
    LIKE("like"),
    NEXT("next"),
    PREV("prev"),
    ADD_REMOVE_IN_PLAYLIST("addRemoveInPlaylist"),
    STATUS("status"),
    CREATE_PLAYLIST("createPlaylist"),
    SWITCH_VISIBILITY("switchVisibility"),
    FOLLOW("follow"),
    SHOW_PLAYLISTS("showPlaylists"),
    SHOW_PREFERRED_SONGS("showPreferredSongs"),
    GET_TOP5_SONGS("getTop5Songs"),
    GET_TOP5_PLAYLISTS("getTop5Playlists");

    private final String label;

    CommandType(final String label) {
        this.label = label;
    }

    /**
     * Gets a CommandType enum from the label String.
     * @param text String that will be compared to the label.
     * @return CommandType enum corresponding to the label.
     */
    public static CommandType fromString(final String text) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.label.equals(text)) {
                return commandType;
            }
        }
        return null;
    }
}
