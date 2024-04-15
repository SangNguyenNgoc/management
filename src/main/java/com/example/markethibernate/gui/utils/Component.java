package com.example.markethibernate.gui.utils;

import lombok.Getter;

@Getter
public enum Component {

    HOME_PAGE("view/hello-view.fxml"),
    CONTENT_PAGE("view/content-view.fxml"),
    TABLE_CONTENT("view/table-view.fxml"),
    CHECKIN_FORM("view/checkin.fxml"),
    USER_DETAIL_FORM("view/user-detail-form.fxml"),
    DEVICE_DETAIL_FORM("view/device-detail-form.fxml"),
    PENALIZE_CREATE_FORM("view/penalty-create-form.fxml"),
    PENALIZE_DETAIL_FORM("view/penalty-detail-form.fxml"),
    BORROW_CONFIRM("view/borrow-confirm.fxml"),
    RETURN_DEVICE("view/return-device.fxml"),
    STATISTIC("view/statistic.fxml");

    private final String value;

    Component(String value) {
        this.value = value;
    }
}
