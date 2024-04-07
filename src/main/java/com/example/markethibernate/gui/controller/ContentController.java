package com.example.markethibernate.gui.controller;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.Component;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
        state.initAddButton(leftToolbar, rightToolbar);
    }

}
