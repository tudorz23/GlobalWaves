package utils;

public enum RepeatState {
    NO_REPEAT_PLAYLIST("no repeat"),
    REPEAT_ALL_PLAYLIST("repeat all"),
    REPEAT_CURR_SONG_PLAYLIST("repeat current song"),
    NO_REPEAT("no repeat"),
    REPEAT_ONCE("repeat once"),
    REPEAT_INFINITE("repeat infinite");

    private final String label;

    RepeatState(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Cycles the current state of the enum.
     * @param prevState State before cycling.
     * @return Following state.
     */
    public RepeatState cycleState(RepeatState prevState) {
        switch (prevState) {
            case NO_REPEAT_PLAYLIST -> {
                return REPEAT_ALL_PLAYLIST;
            }
            case REPEAT_ALL_PLAYLIST -> {
                return REPEAT_CURR_SONG_PLAYLIST;
            }
            case REPEAT_CURR_SONG_PLAYLIST -> {
                return NO_REPEAT_PLAYLIST;
            }
            case NO_REPEAT -> {
                return REPEAT_ONCE;
            }
            case REPEAT_ONCE -> {
                return REPEAT_INFINITE;
            }
            case REPEAT_INFINITE -> {
                return NO_REPEAT;
            }
        }
        // Will never be reached.
        return prevState;
    }
}
