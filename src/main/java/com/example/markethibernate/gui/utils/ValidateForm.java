package com.example.markethibernate.gui.utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.Function;

public class ValidateForm {

    public static void addFocusListener(TextField textField, Label validationLabel, Function<String, String> validationFunction) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String validate = validationFunction.apply(textField.getText());
                validationLabel.setText("*" + validate);
            } else {
                validationLabel.setText("");
            }
        });
    }
}
