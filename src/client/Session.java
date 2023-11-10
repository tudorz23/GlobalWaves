package client;

import database.Database;

public class Session {
    private Database database;
    private int timestamp;

    /* Constructor */
    public Session(Database database) {
        this.timestamp = 0;
        this.database = database;
    }

    /* Getters and Setters */
    public Database getDatabase() {
        return database;
    }
    public void setDatabase(Database database) {
        this.database = database;
    }
    public int getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
