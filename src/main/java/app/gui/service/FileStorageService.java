package main.java.app.gui.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.app.gui.DashboardEntry;
import main.java.app.User;
import main.java.app.db.InfoDatabase;
import main.java.app.db.InfoDatabaseEntry;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static main.java.app.gui.constants.FileConstants.*;

@Singleton
public class FileStorageService {
    private InfoDatabase db;
    // private ObservableList<InfoDatabaseEntry> entries;
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private static final Logger logger = Logger.getLogger(FileStorageService.class.getName());

    public FileStorageService() {
        db = load();
        // entries = FXCollections.observableArrayList(db.getDatabaseEntries());
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "SameParameterValue"})
    private File assertFileExists(String filename) {
        File file = new File(String.format("%s/%s", APP_DIR, filename));

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    private InfoDatabase load() {
        File dbFile = assertFileExists(DB_FILENAME);
        InfoDatabase db;

        try {
            db = mapper.readValue(dbFile, InfoDatabase.class);
        } catch (IOException e) {
            db = new InfoDatabase(FXCollections.observableArrayList());
        }

        for (InfoDatabaseEntry entry : db.getDatabaseEntries()) {
            entry.setEntries(FXCollections.observableArrayList(entry.getEntries()));
        }

        return db;
    }

    private void save() {
        File dbFile = assertFileExists(DB_FILENAME);

        try {
            mapper.writeValue(dbFile, db);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save database to file.");
        }
    }

    public InfoDatabaseEntry getDbEntry(String username) {
        for (InfoDatabaseEntry entry : db.getDatabaseEntries()) {
            if (entry.getUsername().equals(username)) {
                return entry;
            }
        }

        return null;
    }

    public boolean saveUser(User user) {
        String username = user.getUserName();

        if (getDbEntry(username) != null) {
            logger.warning(String.format("Registration failed: User '%s' already exists.", username));
            return false;
        }

        InfoDatabaseEntry dbEntry = new InfoDatabaseEntry(
            username, user.getPassword(), FXCollections.observableArrayList()
        );

        db.getDatabaseEntries().add(dbEntry);
        // entries.add(dbEntry);
        save();

        return true;
    }

    public boolean saveDashboardEntry(String username, DashboardEntry dashboardEntry) {
        InfoDatabaseEntry userDbEntry = getDbEntry(username);
        String dashboardEntryName = dashboardEntry.getName();

        for (DashboardEntry entry : userDbEntry.getEntries()) {
            if (dashboardEntryName.equals(entry.getName())) {
                logger.warning(
                    String.format("Dashboard entry '%s' for user '%s' already exists.", dashboardEntryName, username)
                );
                return false;
            }
        }

        userDbEntry.getEntries().add(dashboardEntry);
        save();

        return true;
    }

    public void editDashboardEntry(String username, DashboardEntry oldEntry, DashboardEntry newEntry) {
        InfoDatabaseEntry userDbEntry = getDbEntry(username);
        List<DashboardEntry> userDbEntries = userDbEntry.getEntries();
        String oldEntryName = oldEntry.getName();

        int index = 0;
        for (DashboardEntry entry : userDbEntries) {
            if (oldEntryName.equals(entry.getName())) {
                break;
            }
            index++;
        }
        userDbEntries.remove(oldEntry);
        userDbEntries.add(index, newEntry);

        save();
    }

    /**
     * Deletes a user's DashboardEntry from the database.
     *
     * @param username Name of the user to whom the DashboardEntry belongs to.
     * @param dashboardEntry The DashboardEntry to be deleted.
     */
    public void deleteDashboardEntry(String username, DashboardEntry dashboardEntry) {
        InfoDatabaseEntry userDbEntry = getDbEntry(username);
        List<DashboardEntry> userDbEntries = userDbEntry.getEntries();
        String dashboardEntryName = dashboardEntry.getName();

        for (DashboardEntry entry : userDbEntries) {
            if (dashboardEntryName.equals(entry.getName())) {
                userDbEntries.remove(entry);
                save();
                break;
            }
        }
    }
}
