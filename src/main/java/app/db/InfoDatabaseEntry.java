package main.java.app.db;

import main.java.app.gui.DashboardEntry;

import java.util.List;

public class InfoDatabaseEntry {
    private String username;
    private String password;
    private List<DashboardEntry> entries;

    public InfoDatabaseEntry() {}

    public InfoDatabaseEntry(String username, String password, List<DashboardEntry> entries) {
        this.username = username;
        this.password = password;
        this.entries = entries;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<DashboardEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DashboardEntry> entries) {
        this.entries = entries;
    }
}
