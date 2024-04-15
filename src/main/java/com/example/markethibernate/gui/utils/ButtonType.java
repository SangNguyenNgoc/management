package com.example.markethibernate.gui.utils;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum ButtonType {

    GREEN_BUTTON("green-button", "green-button-active"),
    BLUE_BUTTON("blue-button", "blue-button"),
    YELLOW_BUTTON("yellow-button", "yellow-button"),
    WHITE_BUTTON("white-button","white-button"),
    RED_BUTTON("red-button", "red-button-active");


    private final String normalStyle;
    private final String activeStyle;

    // Constructor
    ButtonType(String normalStyle, String activeStyle) {
        this.normalStyle = normalStyle;
        this.activeStyle = activeStyle;
    }

}
