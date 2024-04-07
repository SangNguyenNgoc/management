package com.example.markethibernate.gui.utils;

import lombok.Getter;

@Getter
public enum Component {

    HOME_PAGE("view/hello-view.fxml"),
    CONTENT_PAGE("view/content-view.fxml"),
    TABLE_CONTENT("view/table-view.fxml"),
    CHECKIN_FORM("view/checkin.fxml");

    private final String value;

    Component(String value) {
        this.value = value;
    }
}
