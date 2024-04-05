package com.example.markethibernate.gui.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class EmptyPane extends Pane {

    public EmptyPane() {
    }

    public Pane create(String message) {
        Pane pane = new Pane();
        pane.setPrefHeight(350);
        pane.setStyle("-fx-background-color: #FFFFFF;");

        Label content = new Label(message);
        content.setPrefSize(880, 350);
        content.setAlignment(javafx.geometry.Pos.CENTER);
        content.setTextAlignment(TextAlignment.CENTER);
        content.setFont(new Font("Arial", 14.0));

        pane.getChildren().add(content);

        return pane;
    }
}