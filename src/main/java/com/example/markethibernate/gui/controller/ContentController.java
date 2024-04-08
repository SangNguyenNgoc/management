package com.example.markethibernate.gui.controller;

import com.example.markethibernate.gui.state.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContentController {

    @FXML
    private Button leftButton;
    @FXML
    private VBox vBoxParent;
    @FXML
    private HBox toolbar;
    @FXML
    private HBox leftToolbar;
    @FXML
    private HBox rightToolbar;


    public void initContent(State state) {
        state.renderView(vBoxParent);
        state.initButton(leftToolbar, rightToolbar);
    }

}
