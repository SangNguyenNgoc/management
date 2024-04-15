package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.dtos.EmailChecked;
import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.state.impl.ListDeviceState;
import com.example.markethibernate.gui.state.impl.ListPersonState;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.gui.utils.ValidateForm;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class DeviceDetailFormController {
    @FXML
    private HBox buttons;
    @FXML
    private Button deviceDeleteButton;
    @FXML
    private Button deviceUpdateButton;
    @FXML
    private TextField nameInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private Label nameValidate;
    @FXML
    private Label descriptionValidate;
    @FXML
    private HBox header;
    @FXML
    private HBox leftHeader;
    @FXML
    private HBox rightHeader;
    @FXML
    private TextField idInput;
    @FXML
    private Label idValidate;

    public void initValidate() {
        ValidateForm.addFocusListener(idInput, idValidate, DeviceService.getInstance()::checkId);
        ValidateForm.addFocusListener(nameInput, nameValidate, DeviceService.getInstance()::checkName);
        descriptionInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String validate = DeviceService.getInstance().checkDescription(descriptionInput.getText());
                descriptionValidate.setText(validate.isBlank() ? "" : "*" + validate);
            } else {
                descriptionValidate.setText("");
            }
        });
    }

    public void initDevice(Long id) {
        DeviceEntity device = DeviceService.getInstance().getById(id.toString());
        idInput.setText(device.getId().toString());
        nameInput.setText(device.getName());
        descriptionInput.setText(device.getDescription());
    }

    public void submitValidate() {
        ValidateForm.validate(nameInput, nameValidate, DeviceService.getInstance()::checkName);
        String validate = DeviceService.getInstance().checkDescription(descriptionInput.getText());
        descriptionValidate.setText(validate.isBlank() ? "" : "*" + validate);
    }

    public void initAddDeviceButton() {
        deviceUpdateButton.setText("Lưu");
        deviceUpdateButton.setOnMouseClicked(event -> {
            submitValidate();
            DeviceEntity device = DeviceService.getInstance().createDevice(
                    idInput.getText(),
                    nameInput.getText(),
                    descriptionInput.getText(),
                    true
            );
            if (device != null) {
                DialogUtil.getInstance().showAlert("Thông báo", "Thêm thiết bị thành công", Alert.AlertType.INFORMATION);
                clearInput();
            }
        });
        buttons.getChildren().remove(1);
    }

    public void initUpdateDeviceButton(HomeController controller) {
        deviceUpdateButton.setText("Cập nhật");
        idInput.setDisable(true);
        deviceUpdateButton.setOnMouseClicked(event -> {
            submitValidate();
            DeviceEntity device = DeviceService.getInstance().updateDevice(
                    idInput.getText(),
                    nameInput.getText(),
                    descriptionInput.getText()
            );
            if (device != null) {
                DialogUtil.getInstance().showAlert("Thông báo", "Cập nhật thiết bị thành công", Alert.AlertType.INFORMATION);
            }
        });
        deviceDeleteButton.setOnMouseClicked(event -> {
            boolean delete = DialogUtil.getInstance().showConfirm(
                    "Xóa thiết bị", "Xác nhận muốn xóa thiết bị này?");
            if(delete) {
                boolean result = DeviceService.getInstance().deleteDeviceById(idInput.getText());
                if (result) {
                    DialogUtil.getInstance().showAlert("Thông báo", "Xóa thiết bị thành công", Alert.AlertType.INFORMATION);
                    controller.initContent(new ListDeviceState(controller));
                }
            }
        });
    }

    public void clearInput() {
        idInput.setText("");
        nameInput.setText("");
        descriptionInput.setText("");
    }

}
