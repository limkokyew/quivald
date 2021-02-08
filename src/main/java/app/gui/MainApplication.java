package main.java.app.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import main.java.app.gui.injector.InjectorModule;
import main.java.app.gui.service.NavigationService;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        final Injector injector = Guice.createInjector(new InjectorModule());
        NavigationService navService = injector.getInstance(NavigationService.class);
        navService.setInjector(injector);
        navService.createLoginStage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
