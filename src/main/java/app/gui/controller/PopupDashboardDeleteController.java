package main.java.app.gui.controller;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.app.gui.DashboardEntry;
import main.java.app.gui.service.AuthenticationService;
import main.java.app.gui.service.DashboardService;
import main.java.app.gui.service.FileStorageService;

@SuppressWarnings("UnstableApiUsage")
public class PopupDashboardDeleteController {
    private EventBus eventBus;
    private AuthenticationService authService;
    private DashboardService dashboardService;
    private FileStorageService fileService;

    @FXML private Button confirmButton;
    @FXML private Button declineButton;
    @FXML private TextField displayName;
    @FXML private TextField displayUsername;
    @FXML private TextField displayWebsite;

    @Inject
    public PopupDashboardDeleteController(
        EventBus eventBus,
        AuthenticationService authService,
        DashboardService dashboardService,
        FileStorageService fileService
    ) {
        this.authService = authService;
        this.dashboardService = dashboardService;
        this.fileService = fileService;
        this.eventBus = eventBus;
    }

    @FXML
    private void initialize() {
        DashboardEntry selectedEntry = dashboardService.getSelectedEntries().get(0);
        displayName.setText(selectedEntry.getName());
        displayUsername.setText(selectedEntry.getUsername());
        displayWebsite.setText(selectedEntry.getWebsite());

        confirmButton.setOnAction((event -> {
            close();
            fileService.deleteDashboardEntry(authService.getUser().getUserName(), selectedEntry);
        }));

        declineButton.setOnAction((event -> close()));
    }

    private void close() {
        ((Stage) confirmButton.getScene().getWindow()).close();
    }
}
