package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.bll.services.UsageInfoService;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ReturnDeviceController {
    @FXML
    private TextField inputField;
    @FXML
    private Button submitReturn;

    public void init(State state) {
        submitReturn.setOnMouseClicked(event -> {
            if(inputField.getText().isBlank()) {
                return;
            }
            UsageInfoEntity usageInfo = UsageInfoService.getInstance().returnDevice(inputField.getText());
            if (usageInfo != null) {
                DialogUtil.getInstance().showAlert("Thông báo", "Đã trả thiết bị.", Alert.AlertType.INFORMATION);
                state.refresh();
            }
            inputField.setText("");
        });

    }
}
