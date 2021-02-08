package main.java.app.gui.event;

import main.java.app.gui.DashboardEntry;

public class DashboardEditEvent {
    private DashboardEntry dashboardEntry;

    public DashboardEditEvent(DashboardEntry dashboardEntry) {
        this.dashboardEntry = dashboardEntry;
    }

    public DashboardEntry getDashboardEntry() {
        return dashboardEntry;
    }
}
