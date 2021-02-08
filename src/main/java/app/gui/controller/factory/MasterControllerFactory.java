package main.java.app.gui.controller.factory;

import javafx.scene.Parent;
import main.java.app.gui.controller.MasterController;

public interface MasterControllerFactory {
    MasterController create(Parent navigationBar);
}
