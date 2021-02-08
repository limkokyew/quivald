package main.java.app.gui.event;

import main.java.app.gui.Route;

public class SceneChangeEvent {
    private Route route;

    public SceneChangeEvent(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }
}
