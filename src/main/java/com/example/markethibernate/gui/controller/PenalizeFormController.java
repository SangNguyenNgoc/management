package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.services.PenalizeService;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.gui.state.impl.PersonDetailState;
import com.example.markethibernate.gui.utils.DialogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class PenalizeFormController {
    @FXML
    private ComboBox<String> penalizeInput;
    @FXML
    private Button penaltyCreateButton;
    @FXML
    private TextField feeInput;
    @FXML
    private Label penaltyValidate;
    @FXML
    private Label feeValidate;
    @FXML
    private HBox header;
    @FXML
    private HBox leftHeader;
    @FXML
    private HBox rightHeader;

    private static final List<String> penalizeList = List.of(
            "Khóa thẻ 1 tháng",
            "Khóa thẻ 2 tháng",
            "Khóa thẻ vĩnh viễn",
            "Bồi thường",
            "Khóa thẻ 1 tháng và bồi thường"
    );

    public void initToCreatePenalize(HomeController controller, Long personId) {
        penalizeList.forEach(item -> penalizeInput.getItems().add(item));
        penaltyCreateButton.setOnMouseClicked(event -> {
            int select = penalizeInput.getSelectionModel().getSelectedIndex();
            if (select == 3 || select == 4) {
                String validate = PenalizeService.getInstance().checkPayment(feeInput.getText());
                if(!validate.isBlank()) {
                    feeValidate.setText("*" + validate);
                    return;
                }
            }
            PenalizeEntity penalize = PenalizeService.getInstance().create(
                    personId,
                    penalizeInput.getSelectionModel().getSelectedItem(),
                    feeInput.getText()
            );
            if(penalize != null) {
                DialogUtil.getInstance().showAlert("Thông báo", "Lập phiếu phạt thành công", Alert.AlertType.INFORMATION);
                controller.initContent(new PersonDetailState(controller, personId));
            }
        });
    }

}
