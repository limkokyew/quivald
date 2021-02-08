package main.java.app.gui;

public class DashboardEntry {
    private String name;
    private String username;
    private String password;
    private String website;

    public DashboardEntry() {}

    public DashboardEntry(String name, String username, String password, String website) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getWebsite() {
        return website;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
