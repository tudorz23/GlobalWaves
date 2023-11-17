package utils;

public enum Visibility {
    PUBLIC("public"),
    PRIVATE("private");

    private final String label;

    Visibility(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * @param currVisibility visibility before cycling.
     * @return PUBLIC, if current visibility is PRIVATE, and otherwise.
     */
    public static Visibility cycleVisibility(final Visibility currVisibility) {
        if (currVisibility == PUBLIC) {
            return PRIVATE;
        }

        return PUBLIC;
    }
}
