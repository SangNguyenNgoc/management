package com.example.markethibernate.gui.utils;

import lombok.Getter;

@Getter
public enum IconType {

    EXCEL("image/excel.png", "image/excel-active.png");

    private final String normalStyle;
    private final String activeStyle;

    // Constructor
    IconType(String normalStyle, String activeStyle) {
        this.normalStyle = normalStyle;
        this.activeStyle = activeStyle;
    }
}
