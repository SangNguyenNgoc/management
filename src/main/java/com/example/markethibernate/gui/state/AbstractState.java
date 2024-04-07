package com.example.markethibernate.gui.state;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.IconType;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public abstract class AbstractState {

    protected HomeController homeController;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public void createTableHeader(List<LabelHeader> labelHeaders, HBox headerTable) {
        labelHeaders.forEach(item -> {
            Label label = createLabelHeader(item.getText(), item.getWidth());
            headerTable.getChildren().add(label);
        });
    }

    public Label createLabelHeader(String text, double prefWidth) {
        Label label = new Label(text);
        label.setPrefHeight(45.0);
        label.setPrefWidth(prefWidth);
        label.setTextFill(Color.GRAY);
        label.setPadding(new Insets(0, 0, 0, 10.0));
        label.setFont(new Font(14.0));
        return label;
    }

    public Label createLabelContent(String text, double prefWidth) {
        Label label = new Label(text);
        label.setPrefHeight(45.0);
        label.setPrefWidth(prefWidth);
        label.setTextFill(Color.web("#333333"));
        label.setPadding(new Insets(0, 0, 0, 10.0));
        label.setFont(new Font(14.0));
        return label;
    }

    public Button createButton(ButtonType type, String text, IconType icon) {
        Button button = new Button(text);
        button.getStylesheets().add(HelloApplication.class.getResource("style/button.css").toString());
        button.setGraphic(new ImageView(new Image(HelloApplication.class.getResource(icon.getNormalStyle()).toString())));
        button.getStyleClass().add(type.getNormalStyle());
        button.setOnMouseEntered(event -> {
            button.getStyleClass().remove(type.getNormalStyle());
            button.getStyleClass().add(type.getActiveStyle());
            button.setGraphic(new ImageView(new Image(HelloApplication.class.getResource(icon.getActiveStyle()).toString())));
            button.setCursor(Cursor.HAND);
        });
        button.setOnMouseExited(event -> {
            button.getStyleClass().remove(type.getActiveStyle());
            button.getStyleClass().add(type.getNormalStyle());
            button.setGraphic(new ImageView(new Image(HelloApplication.class.getResource(icon.getNormalStyle()).toString())));
        });
        return button;
    }

    public Button createButton(ButtonType type, String text) {
        Button button = new Button(text);
        button.getStylesheets().add(HelloApplication.class.getResource("style/button.css").toString());
        button.getStyleClass().add(type.getNormalStyle());
        button.setOnMouseEntered(event -> {
            button.getStyleClass().remove(type.getNormalStyle());
            button.getStyleClass().add(type.getActiveStyle());
            button.setCursor(Cursor.HAND);
        });
        button.setOnMouseExited(event -> {
            button.getStyleClass().remove(type.getActiveStyle());
            button.getStyleClass().add(type.getNormalStyle());
        });
        return button;
    }

}
