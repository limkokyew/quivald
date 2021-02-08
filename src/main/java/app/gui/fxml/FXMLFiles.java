package main.java.app.gui.fxml;

public enum FXMLFiles {
    LOGIN("/gui/login_window.fxml"),
    MASTER("/gui/main_window.fxml"),
    DASHBOARD("/gui/dashboard_window.fxml"),
    SIDEBAR_DASHBOARD("/gui/sidebar_dashboard.fxml"),
    NAVIGATION_BAR("/gui/navigation_bar.fxml"),
    POPUP_DASHBOARD_ENTRY("/gui/popup/add_dashboard_entry.fxml"),
    POPUP_DELETE_DASHBOARD_ENTRY("/gui/popup/delete_dashboard_entry.fxml");

    private String path;

    FXMLFiles(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
