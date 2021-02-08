package main.java.app.gui;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupStage {
    private final Stage childStage;
    private double startX;
    private double startY;

    public PopupStage(Stage parentStage, Parent popup, boolean includeCloseButton) {
        AnchorPane contentPane = new AnchorPane();
        contentPane.getStyleClass().add("content-pane");
        contentPane.getStylesheets().add(getClass().getResource("/gui/popup/shared.css").toExternalForm());
        contentPane.getChildren().add(popup);

        childStage = new Stage(StageStyle.UNDECORATED);
        childStage.initModality(Modality.APPLICATION_MODAL);

        contentPane.setOnMousePressed(event -> {
            startX = event.getSceneX();
            startY = event.getSceneY();
        });
        contentPane.setOnMouseDragged(event -> {
            childStage.setX(event.getScreenX() - startX);
            childStage.setY(event.getScreenY() - startY);
        });

        if (includeCloseButton) {
            Button closeButton = new Button();
            closeButton.getStyleClass().add("close-button");
            closeButton.setCursor(Cursor.HAND);
            AnchorPane.setTopAnchor(closeButton, 25.0);
            AnchorPane.setRightAnchor(closeButton, 25.0);
            contentPane.getChildren().add(closeButton);
            closeButton.setOnAction((event) -> close());
        }

        childStage.initOwner(parentStage);
        childStage.setScene(new Scene(contentPane));
        childStage.show();
    }

    public void close() {
        childStage.close();
    }
}
