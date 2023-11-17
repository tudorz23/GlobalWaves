package utils;

public enum RepeatState {
    NO_REPEAT_PLAYLIST("No Repeat"),
    REPEAT_ALL_PLAYLIST("Repeat All"),
    REPEAT_CURR_SONG_PLAYLIST("Repeat Current Song"),
    NO_REPEAT("No Repeat"),
    REPEAT_ONCE("Repeat Once"),
    REPEAT_INFINITE("Repeat Infinite");

    private final String label;

    RepeatState(final String label) {
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
    public static RepeatState cycleState(final RepeatState prevState) {
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
            default -> {
                // Will never be reached.
                return prevState;
            }
        }

    }
}
