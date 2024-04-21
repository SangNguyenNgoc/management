package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.bll.services.PenalizeService;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.gui.state.impl.ListDeviceState;
import com.example.markethibernate.gui.state.impl.PersonDetailState;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class PenalizeDetailController {

    @FXML
    private Button penaltyDeleteButton;
    @FXML
    private Button penaltyUpdateButton;
    @FXML
    private TextField userInput;
    @FXML
    private ComboBox<String> penaltyInput;
    @FXML
    private ComboBox<String> statusInput;
    @FXML
    private TextField numberPhoneInput;
    @FXML
    private TextField timeInput;
    @FXML
    private TextField feeInput;
    @FXML
    private Label userValidate;
    @FXML
    private Label penaltyValidate;
    @FXML
    private Label statusValidate;
    @FXML
    private Label timeValidate;
    @FXML
    private Label numberPhoneValidate;
    @FXML
    private Label feeValidate;

    private static final List<String> penalizeList = List.of(
            "Khóa thẻ 1 tháng",
            "Khóa thẻ 2 tháng",
            "Khóa thẻ vĩnh viễn",
            "Bồi thường",
            "Khóa thẻ 1 tháng và bồi thường"
    );

    private static final List<String> statusList = List.of(
            "Đang thi hành",
            "Đã hoàn thành"
    );


    public void init(Long penalizeId) {
        initComboBox();
        PenalizeEntity penalize = PenalizeService.getInstance().getByIdAndPerson(penalizeId.toString());
        userInput.setDisable(true);
        userInput.setText(penalize.getPerson().getId() + " - " + penalize.getPerson().getName());
        numberPhoneInput.setDisable(true);
        numberPhoneInput.setText(penalize.getPerson().getPhoneNumber());
        timeInput.setDisable(true);
        timeInput.setText(AppUtil.dateToString(penalize.getDate()));
        penaltyInput.getSelectionModel().select(penalize.getType());
        statusInput.getSelectionModel().select(penalize.getStatus() ? statusList.get(0) : statusList.get(1));
        feeInput.setText(penalize.getPayment() == null ? "Không có" :  penalize.getPayment().toString());
        penaltyUpdateButton.setOnMouseClicked(event -> {
            int select = penaltyInput.getSelectionModel().getSelectedIndex();
            System.out.println(select);
            if (select == 3 || select == 4) {
                String validate = PenalizeService.getInstance().checkPayment(feeInput.getText());
                if(!validate.isBlank()) {
                    feeValidate.setText("*" + validate);
                    return;
                }
            } else {
                feeInput.setText(null);
            }
            PenalizeEntity penalizeEntity = PenalizeService.getInstance().update(
                    penalize.getId().toString(),
                    penaltyInput.getSelectionModel().getSelectedItem(),
                    feeInput.getText(),
                    statusInput.getSelectionModel().isSelected(0)
            );
            if(penalizeEntity != null) {
                DialogUtil.getInstance().showAlert("Thông báo", "Cập nhật phiếu phạt thành công", Alert.AlertType.INFORMATION);
                feeValidate.setText("");
            }
        });
        penaltyDeleteButton.setOnMouseClicked(event -> {
            boolean delete = DialogUtil.getInstance().showConfirm(
                    "Xóa phiếu phạt", "Xác nhận muốn xóa phiếu phạt này?");
            if(delete) {
                boolean result = PenalizeService.getInstance().deleteById(penalizeId);
                if (result) {
                    DialogUtil.getInstance().showAlert("Thông báo", "Xóa phiếu phạt thành công", Alert.AlertType.INFORMATION);
                }
            }
        });
    }

    private void initComboBox() {
        penalizeList.forEach(item -> {
            penaltyInput.getItems().add(item);
        });
        statusList.forEach(item -> {
            statusInput.getItems().add(item);
        });
    }
}
