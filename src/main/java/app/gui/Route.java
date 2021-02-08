package main.java.app.gui;

import javafx.scene.Parent;

public class Route {
    private Parent destination;
    private Parent sidebar;

    public Route(Parent destination) {
        this.destination = destination;
    }

    public Route(Parent destination, Parent sidebar) {
        this.destination = destination;
        this.sidebar = sidebar;
    }

    public Parent getDestination() {
        return destination;
    }

    public Parent getSidebar() {
        return sidebar;
    }
}
