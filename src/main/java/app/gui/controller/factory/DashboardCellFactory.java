package main.java.app.gui.controller.factory;

import javafx.scene.control.TableColumn;
import main.java.app.gui.DashboardEntry;

public interface DashboardCellFactory {
    void createCellFactory(TableColumn<DashboardEntry, String> tableColumn);
    void createCellFactory(TableColumn<DashboardEntry, String> tableColumn, String mask);
}
