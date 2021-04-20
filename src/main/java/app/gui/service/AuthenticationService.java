package main.java.app.gui.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import main.java.app.User;
import main.java.app.db.InfoDatabaseEntry;

import java.util.logging.Logger;

@Singleton
public class AuthenticationService {
    private User authenticatedUser;
    private final BooleanProperty authenticated = new SimpleBooleanProperty(false);
    private final FileStorageService fileService;

    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    @Inject
    public AuthenticationService(FileStorageService fileService) {
        this.fileService = fileService;
    }

    public BooleanProperty getAuthenticationProperty() { return authenticated; }

    public boolean register(String username, String password) {
        User newUser = new User(username, password);
        if (fileService.saveUser(newUser)) {
            authenticatedUser = newUser;
            authenticated.set(true);
            return true;
        }

        return false;
    }

    public boolean authenticate(String username, String password) {
        InfoDatabaseEntry databaseEntry = fileService.getDbEntry(username);

        if (databaseEntry != null && password.equals(databaseEntry.getPassword())) {
            authenticated.set(true);
            authenticatedUser = new User(username, password);
            logger.info("Authentication successful.");
            return true;
        } else {
            logger.info("Authentication failed.");
            return false;
        }
    }

    public void revokeAuthentication() {
        authenticatedUser = null;
        authenticated.set(false);
    }

    public User getUser() {
        return authenticatedUser;
    }
}
