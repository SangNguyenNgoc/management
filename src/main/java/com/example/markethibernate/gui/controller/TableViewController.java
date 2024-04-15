package com.example.markethibernate.gui.controller;

import com.example.markethibernate.gui.state.State;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class TableViewController {

    @FXML
    private Label titleTable;
    @FXML
    private HBox headerTable;
    @FXML
    private VBox contentTable;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox rightHeader;

    public void initTitle(String text) {
        titleTable.setText(text);
    }

}
