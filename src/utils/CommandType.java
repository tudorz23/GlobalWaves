package utils;

public enum CommandType {
    SEARCH("search");

    private final String label;

    CommandType(String label) {
        this.label = label;
    }

    /**
     * Gets an enum Command type from the label String.
     * @param text String that will be compared to the label.
     * @return CommandType enum corresponding to the label.
     */
    public static CommandType fromString(String text) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.label.equals(text)) {
                return commandType;
            }
        }
        return null;
    }
}
