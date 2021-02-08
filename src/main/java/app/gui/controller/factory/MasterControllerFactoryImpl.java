package main.java.app.gui.controller.factory;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.scene.Parent;
import main.java.app.gui.controller.MasterController;

public class MasterControllerFactoryImpl implements MasterControllerFactory {
    private EventBus eventBus;

    @Inject
    public MasterControllerFactoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public MasterController create(Parent navigationBar) {
        return new MasterController(eventBus, navigationBar);
    }
}
