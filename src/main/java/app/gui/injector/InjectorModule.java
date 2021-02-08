package main.java.app.gui.injector;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import main.java.app.gui.controller.factory.DashboardCellFactory;
import main.java.app.gui.controller.factory.DashboardCellFactoryImpl;
import main.java.app.gui.controller.factory.MasterControllerFactory;
import main.java.app.gui.controller.factory.MasterControllerFactoryImpl;
import main.java.app.gui.service.AuthenticationService;
import main.java.app.gui.service.DashboardService;
import main.java.app.gui.service.FileStorageService;
import main.java.app.gui.service.NavigationService;

@SuppressWarnings("UnstableApiUsage")
public class InjectorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DashboardCellFactory.class).to(DashboardCellFactoryImpl.class);
        bind(MasterControllerFactory.class).to(MasterControllerFactoryImpl.class);
    }

    @Provides @Singleton
    public EventBus getEventBus() { return new EventBus(); }

    @Provides @Singleton
    public AuthenticationService getAuthenticationService(FileStorageService fileService) {
        return new AuthenticationService(fileService);
    }

    @Provides @Singleton
    public DashboardService getDashboardService() {
        return new DashboardService();
    }

    @Provides @Singleton
    public FileStorageService getFileStorageService() {
        return new FileStorageService();
    }

    @Provides @Singleton
    public NavigationService getNavigationService(EventBus eventBus) {
        return new NavigationService(eventBus);
    }
}
