package utils;

public enum Visibility {
    PUBLIC("public"),
    PRIVATE("private");

    private final String label;

    Visibility(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
