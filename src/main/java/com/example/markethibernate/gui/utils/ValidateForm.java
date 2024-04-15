package com.example.markethibernate.gui.utils;

import com.example.markethibernate.bll.dtos.EmailChecked;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.Function;

public class ValidateForm {

    public static void addFocusListener(TextField textField, Label validationLabel, Function<String, String> validationFunction) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String validate = validationFunction.apply(textField.getText());
                validationLabel.setText(validate.isBlank() ? "" : "*" + validate);
            } else {
                validationLabel.setText("");
            }
        });
    }

    public static void addFocusListener(TextField textField, EmailChecked checked, Label validationLabel, Function<EmailChecked, String> validationFunction) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            checked.setEmail(textField.getText());
            if (!newValue) {
                String validate = validationFunction.apply(checked);
                validationLabel.setText(validate.isBlank() ? "" : "*" + validate);
            } else {
                validationLabel.setText("");
            }
        });
    }

    public static void validate(TextField textField, Label validationLabel, Function<String, String> validationFunction) {
        String validate = validationFunction.apply(textField.getText());
        validationLabel.setText(validate.isBlank() ? "" : "*" + validate);
    }

    public static void validate(TextField textField, EmailChecked checked, Label validationLabel, Function<EmailChecked, String> validationFunction) {
        checked.setEmail(textField.getText());
        String validate = validationFunction.apply(checked);
        validationLabel.setText(validate.isBlank() ? "" : "*" + validate);
    }
}
