package main.java.app.db;

import java.util.List;

public class InfoDatabase {
    private List<InfoDatabaseEntry> databaseEntries;

    public InfoDatabase() {}

    public InfoDatabase(List<InfoDatabaseEntry> databaseEntries) {
        this.databaseEntries = databaseEntries;
    }

    public List<InfoDatabaseEntry> getDatabaseEntries() {
        return databaseEntries;
    }
}
