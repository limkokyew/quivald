package main.java.app.gui.controller;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.app.gui.fxml.FXMLFiles;
import main.java.app.gui.service.NavigationService;

@SuppressWarnings("UnstableApiUsage")
public class DashboardSidebarController extends Controller {
    private EventBus eventBus;
    private NavigationService navigationService;

    @FXML private Button addEntryButton;
    @FXML private Button openGeneratorButton;

    @Inject
    public DashboardSidebarController(NavigationService navigationService, EventBus eventBus) {
        this.navigationService = navigationService;
        this.eventBus = eventBus;
    }

    @FXML
    private void initialize() {
        addEntryButton.setOnAction((event) -> navigationService.createPopupWindow(
            (Stage) addEntryButton.getScene().getWindow(), FXMLFiles.POPUP_DASHBOARD_ENTRY, true
        ));
    }
}
