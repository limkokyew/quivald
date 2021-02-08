package main.java.app.gui.service;

import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import main.java.app.gui.DashboardEntry;

@Singleton
public class DashboardService {
    private ObservableList<DashboardEntry> selectedEntries = FXCollections.observableArrayList();

    public ObservableList<DashboardEntry> getSelectedEntries() {
        return selectedEntries;
    }

    public void setSelectedEntries(ObservableList<DashboardEntry> selectedEntries) {
        selectedEntries.addListener((ListChangeListener<DashboardEntry>) change -> {
            this.selectedEntries.setAll(change.getList());
        });
    }
}
