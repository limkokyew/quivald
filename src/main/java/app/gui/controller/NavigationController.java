package main.java.app.gui.controller;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import main.java.app.gui.service.AuthenticationService;
import main.java.app.gui.service.NavigationService;

import java.util.logging.Logger;

public class NavigationController extends Controller {
    private AuthenticationService authService;
    private NavigationService navigationService;

    @FXML private Button dashboardButton;
    @FXML private MenuButton userButton;

    private static final Logger logger = Logger.getLogger(NavigationController.class.getName());

    @Inject
    public NavigationController(AuthenticationService authService, NavigationService navigationService) {
        this.authService = authService;
        this.navigationService = navigationService;
    }

    @FXML
    private void initialize() {
        userButton.setText(authService.getUser().getUserName().substring(0, 1).toUpperCase());
    }

    @FXML
    private void logout() {
        authService.revokeAuthentication();
        Stage currentStage = (Stage) dashboardButton.getScene().getWindow();
        navigationService.closeStage(currentStage);
        navigationService.createLoginStage();
    }
}
