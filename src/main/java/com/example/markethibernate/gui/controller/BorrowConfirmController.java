package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.bll.services.UsageInfoService;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.state.impl.ListDeviceToUseState;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class BorrowConfirmController {

    @FXML
    public HBox buttons;
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label deviceLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    private Stage stage;

    private DeviceEntity device;

    private PersonEntity person;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initContent(Stage stage, Long deviceId, Long personId, ListDeviceToUseState state) {
        device = DeviceService.getInstance().getById(deviceId.toString());
        person = PersonService.getInstance().getById(personId.toString());
        idLabel.setText(person.getId().toString());
        nameLabel.setText(person.getName());
        deviceLabel.setText(device.getDescription() + " - Mã số: " + device.getId());
        timeLabel.setText(AppUtil.dateToString(LocalDateTime.now()));
        submitButton.setOnMouseClicked(event -> submit(person.getId(), device.getId(), state));
        cancelButton.setOnMouseClicked(event -> cancel(state));
        setStage(stage);
    }

    public void submit(Long personId, Long deviceId, ListDeviceToUseState state) {
        UsageInfoEntity usageInfo = UsageInfoService.getInstance().borrowDevice(personId.toString(), deviceId.toString());
        if (usageInfo != null) {
            DialogUtil.getInstance().showAlert(
                    "Thống báo",
                    "Mượn thiết bị thành công, hãy quét mã trên phiếu mượn khi trả lại thiết bị.",
                    Alert.AlertType.INFORMATION);
        }
        stage.close();
        state.refresh();
    }

    public void cancel( ListDeviceToUseState state) {
        stage.close();
        state.refresh();
    }
}
