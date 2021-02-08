package main.java.app.gui.controller;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import main.java.app.gui.DashboardEntry;
import main.java.app.gui.controller.factory.DashboardCellFactory;
import main.java.app.gui.event.DashboardEditEvent;
import main.java.app.gui.fxml.FXMLFiles;
import main.java.app.gui.service.AuthenticationService;
import main.java.app.gui.service.DashboardService;
import main.java.app.gui.service.FileStorageService;
import main.java.app.gui.service.NavigationService;

import java.util.logging.Logger;

@SuppressWarnings("UnstableApiUsage")
public class DashboardController extends Controller {
    private AuthenticationService authService;
    private DashboardCellFactory cellFactory;
    private DashboardService dashboardService;
    private FileStorageService fileService;
    private NavigationService navigationService;
    private EventBus eventBus;

    @FXML private TableView<DashboardEntry> dashboardTable;
    @FXML private TableColumn<DashboardEntry, String> nameCol;
    @FXML private TableColumn<DashboardEntry, String> usernameCol;
    @FXML private TableColumn<DashboardEntry, String> passwordCol;
    @FXML private TableColumn<DashboardEntry, String> websiteCol;

    private static final Logger logger = Logger.getLogger(DashboardController.class.getName());

    @Inject
    public DashboardController(
        AuthenticationService authService,
        DashboardCellFactory cellFactory,
        DashboardService dashboardService,
        FileStorageService fileService,
        NavigationService navigationService,
        EventBus eventBus
    ) {
        this.authService = authService;
        this.dashboardService = dashboardService;
        this.cellFactory = cellFactory;
        this.fileService = fileService;
        this.navigationService = navigationService;
        this.eventBus = eventBus;
    }

    private void initializeContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Eintrag bearbeiten");
        MenuItem deleteItem = new MenuItem("Eintrag löschen");

        editItem.setOnAction((event) -> {
            if (dashboardTable.getSelectionModel().getSelectedItem() != null) {
                navigationService.createPopupWindow(
                    (Stage) dashboardTable.getScene().getWindow(), FXMLFiles.POPUP_DASHBOARD_ENTRY, true
                );
                eventBus.post(new DashboardEditEvent(dashboardTable.getSelectionModel().getSelectedItem()));
            }
        });
        deleteItem.setOnAction((event -> navigationService.createPopupWindow(
            (Stage) dashboardTable.getScene().getWindow(), FXMLFiles.POPUP_DELETE_DASHBOARD_ENTRY, false
        )));

        contextMenu.getItems().add(editItem);
        contextMenu.getItems().add(deleteItem);

        dashboardTable.setRowFactory((tableView) -> {
            TableRow<DashboardEntry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        contextMenu.show(dashboardTable, event.getScreenX(), event.getScreenY());
                    } else if (event.getButton() == MouseButton.PRIMARY) {
                        contextMenu.hide();
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void initialize() {
        ObservableList<DashboardEntry> entries = (ObservableList<DashboardEntry>) fileService.getDbEntry(
            authService.getUser().getUserName()
        ).getEntries();
        FilteredList<DashboardEntry> filteredEntries = new FilteredList<>(entries);

        dashboardTable.setItems(filteredEntries);
        dashboardService.setSelectedEntries(dashboardTable.getSelectionModel().getSelectedItems());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        websiteCol.setCellValueFactory(new PropertyValueFactory<>("website"));

        cellFactory.createCellFactory(nameCol);
        cellFactory.createCellFactory(usernameCol);
        cellFactory.createCellFactory(passwordCol, "••••••••••");
        cellFactory.createCellFactory(websiteCol);

        initializeContextMenu();
    }
}
