package com.example.markethibernate.gui.controller;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.ContentState;
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

    private ContentState state;


    public void setState(ContentState state) {
        this.state = state;
    }

    public void initListPersons() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.TABLE_CONTENT.getValue()));
            Parent root = null;
            root = loader.load();
            TableViewController tableViewController = loader.getController();
            tableViewController.initListPerson();
            vBoxParent.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initAddButton();
    }

    public void initListDevices() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.TABLE_CONTENT.getValue()));
            Parent root = null;
            root = loader.load();
            TableViewController tableViewController = loader.getController();
            tableViewController.initListDevice();
            vBoxParent.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initAddButton();
    }

    public void init(ContentState contentState) {
        setState(contentState);
        switch (state) {
            case LIST_PERSONS -> initListPersons();
            case LIST_DEVICES -> initListDevices();
        }
    }

    public void initAddButton() {
        Button button = new Button("Tạo mới");
        button.setStyle(
                "-fx-background-color: #2563EB; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14; " +
                        "-fx-background-radius: 8"
        );
        button.setPrefSize(90,30);
        rightToolbar.getChildren().add(button);
        button.setOnMouseClicked(event -> {

        });
    }
}
