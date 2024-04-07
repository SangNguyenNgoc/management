package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.services.UsageInfoService;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CheckInController {

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label label;
    @FXML
    private TextField inputField;
    @FXML
    private Button find;

    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public void findPersonByMouse(MouseEvent event) {
        String id = inputField.getText();
        if(id.isEmpty()) {
            return;
        }
        checkIn(id);
    }

    public void findPersonByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            findPersonByMouse(null);
        }
    }

    public void checkIn(String id) {
        UsageInfoEntity usageInfo = UsageInfoService.getInstance().checkIn(inputField.getText());
        if(usageInfo == null) {
            DialogUtil.getInstance().showAlert("Thông báo", "Không tìm thấy thành viên", Alert.AlertType.INFORMATION);
        } else {
            this.id.setText("Mã TV: " + usageInfo.getPerson().getId().toString());
            this.name.setText("Tên TV: " + usageInfo.getPerson().getName());
            state.refresh();
        }
        inputField.clear();
    }


}
