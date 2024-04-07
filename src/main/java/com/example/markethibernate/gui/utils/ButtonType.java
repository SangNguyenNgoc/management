package com.example.markethibernate.gui.utils;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum ButtonType {

    GREEN_BUTTON("green-button", "green-button-active"),
    BLUE_BUTTON("blue-button", "blue-button");


    private final String normalStyle;
    private final String activeStyle;

    // Constructor
    ButtonType(String normalStyle, String activeStyle) {
        this.normalStyle = normalStyle;
        this.activeStyle = activeStyle;
    }

}
