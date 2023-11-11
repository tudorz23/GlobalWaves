package utils;

public enum Criteria {
    BEFORE,
    AFTER;

    public static Criteria fromString(String text) {
        if (text.charAt(0) == '<') {
            return BEFORE;
        }

        return AFTER;
    }
}
