package main.java.app.gui.service;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.app.gui.PopupStage;
import main.java.app.gui.Route;
import main.java.app.gui.controller.factory.MasterControllerFactoryImpl;
import main.java.app.gui.event.SceneChangeEvent;
import main.java.app.gui.fxml.FXMLFiles;

import java.io.IOException;

import static main.java.app.gui.constants.GuiConstants.*;

@SuppressWarnings("UnstableApiUsage")
@Singleton
public class NavigationService {
    private EventBus eventBus;
    private Injector injector;

    @Inject
    public NavigationService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    private Stage createStage(String title, boolean resizable) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(resizable);
        return stage;
    }

    private Parent loadFxml(FXMLFiles fxmlFile) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile.getPath()));
        loader.setControllerFactory(injector::getInstance);
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    public void createLoginStage() {
        Stage loginStage = createStage(String.format("Login - %s", APP_TITLE), false);
        loginStage.getIcons().add(new Image(getClass().getResource("/icons/logo_stage.png").toExternalForm()));
        loginStage.setScene(new Scene(loadFxml(FXMLFiles.LOGIN), LOGIN_WIDTH, LOGIN_HEIGHT));
        loginStage.show();
    }

    public void createMasterStage(FXMLFiles initialFxmlFile, FXMLFiles initialSidebarFile) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFiles.MASTER.getPath()));
        loader.setController(injector.getInstance(MasterControllerFactoryImpl.class)
              .create(loadFxml(FXMLFiles.NAVIGATION_BAR)));

        Stage masterStage = createStage(String.format("Dashboard - %s", APP_TITLE), true);
        masterStage.getIcons().add(new Image(getClass().getResource("/icons/logo_stage.png").toExternalForm()));
        try {
            masterStage.setScene(new Scene(loader.load(), MAIN_WIDTH, MAIN_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        eventBus.post(new SceneChangeEvent(new Route(loadFxml(initialFxmlFile), loadFxml(initialSidebarFile))));
        masterStage.show();
    }

    public void changeScene(FXMLFiles fxmlFile) {
        eventBus.post(new SceneChangeEvent(new Route(loadFxml(fxmlFile))));
    }

    public void changeScene(FXMLFiles fxmlFile, FXMLFiles fxmlSidebarfile) {
        eventBus.post(new SceneChangeEvent(new Route(loadFxml(fxmlFile), loadFxml(fxmlSidebarfile))));
    }

    public PopupStage createPopupWindow(Stage parentStage, FXMLFiles fxmlFile, boolean includeCloseButton) {
        return new PopupStage(parentStage, loadFxml(fxmlFile), includeCloseButton);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
