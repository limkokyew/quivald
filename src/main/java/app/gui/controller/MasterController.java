package main.java.app.gui.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.java.app.gui.Route;
import main.java.app.gui.event.SceneChangeEvent;

@SuppressWarnings("UnstableApiUsage")
public class MasterController extends Controller {
    private EventBus eventBus;

    @FXML private BorderPane mainWindow;
    @FXML private AnchorPane sidebar;

    @Inject
    public MasterController(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    @FXML
    private void initialize() {}

    @Subscribe
    private void handleSceneChangeEvent(SceneChangeEvent event) {
        Route route = event.getRoute();
        mainWindow.setCenter(route.getDestination());
        sidebar.getChildren().clear();

        if (route.getSidebar() != null) {
            Parent childSidebar = route.getSidebar();

            AnchorPane.setTopAnchor(childSidebar, 0.0);
            AnchorPane.setLeftAnchor(childSidebar, 0.0);
            AnchorPane.setRightAnchor(childSidebar, 0.0);
            AnchorPane.setBottomAnchor(childSidebar, 0.0);

            sidebar.getChildren().add(childSidebar);
        }
    }
}
