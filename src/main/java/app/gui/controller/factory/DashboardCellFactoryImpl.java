package main.java.app.gui.controller.factory;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseButton;
import main.java.app.gui.DashboardEntry;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class DashboardCellFactoryImpl implements DashboardCellFactory {
    private TableCell<DashboardEntry, String> createTableCell(String overrideItemText) {
        TableCell<DashboardEntry, String> tableCell = new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                String displayText = item;

                if (overrideItemText != null) {
                    displayText = overrideItemText;
                }
                setText(!empty ? displayText : null);
            }
        };

        tableCell.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                        new StringSelection(tableCell.getItem()), null
                );
            }
        });

        return tableCell;
    }

    public void createCellFactory(TableColumn<DashboardEntry, String> tableColumn) {
        tableColumn.setCellFactory((tc) -> createTableCell(null));
    }

    public void createCellFactory(TableColumn<DashboardEntry, String> tableColumn, String mask) {
        tableColumn.setCellFactory((tc) -> createTableCell(mask));
    }
}
