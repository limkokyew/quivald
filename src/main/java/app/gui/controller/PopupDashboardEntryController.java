package main.java.app.gui.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.app.gui.DashboardEntry;
import main.java.app.gui.event.DashboardEditEvent;
import main.java.app.gui.service.AuthenticationService;
import main.java.app.gui.service.DashboardService;
import main.java.app.gui.service.FileStorageService;

@SuppressWarnings("UnstableApiUsage")
public class PopupDashboardEntryController {
    private EventBus eventBus;
    private AuthenticationService authService;
    private DashboardService dashboardService;
    private FileStorageService fileService;

    @FXML private TextField nameInput;
    @FXML private TextField usernameInput;
    @FXML private PasswordField passwordInput;
    @FXML private TextField websiteInput;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Inject
    public PopupDashboardEntryController(
        AuthenticationService authService,
        DashboardService dashboardService,
        FileStorageService fileService,
        EventBus eventBus
    ) {
        this.authService = authService;
        this.dashboardService = dashboardService;
        this.fileService = fileService;
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    @FXML
    private void initialize() {
        addButton.setOnAction((event) -> add());
        cancelButton.setOnAction((event) -> ((Stage) nameInput.getScene().getWindow()).close());
    }

    private void add() {
        if (fileService.saveDashboardEntry(
                authService.getUser().getUserName(), new DashboardEntry(
                    nameInput.getText(), usernameInput.getText(), passwordInput.getText(), websiteInput.getText()
                )
            )
        ) {
            ((Stage) nameInput.getScene().getWindow()).close();
        }
    }

    private void edit(DashboardEntry entry) {
        DashboardEntry newEntry = new DashboardEntry(
                nameInput.getText(), usernameInput.getText(), passwordInput.getText(), websiteInput.getText()
        );

        fileService.editDashboardEntry(
                authService.getUser().getUserName(), entry, newEntry
        );

        ((Stage) nameInput.getScene().getWindow()).close();
    }

    @Subscribe
    private void handleDashboardEditEvent(DashboardEditEvent event) {
        DashboardEntry entry = event.getDashboardEntry();

        nameInput.setText(entry.getName());
        usernameInput.setText(entry.getUsername());
        passwordInput.setText(entry.getPassword());
        websiteInput.setText(entry.getWebsite());

        addButton.setText("BEARBEITEN");
        addButton.setOnAction((mouseEvent) -> edit(entry));
    }
}
