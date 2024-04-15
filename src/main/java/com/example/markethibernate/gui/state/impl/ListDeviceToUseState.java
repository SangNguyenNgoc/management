package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.gui.controller.BorrowConfirmController;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.TableViewController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.gui.utils.EmptyPane;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class ListDeviceToUseState extends AbstractState implements State {

    private final Long personId;

    private String filter;

    public ListDeviceToUseState(HomeController homeController, Long personId, String filter) {
        setHomeController(homeController);
        this.personId = personId;
        this.filter = filter;
    }

    public ListDeviceToUseState(HomeController homeController, Long personId) {
        setHomeController(homeController);
        this.personId = personId;
    }

    private static final List<LabelHeader> DEVICE_HEADER = List.of(
            new LabelHeader("ID",  160.0),
            new LabelHeader("Tên", 280.0),
            new LabelHeader("Mô tả", 320.0),
            new LabelHeader("Tình trạng", 120.0)
    );
    @Override
    public void renderView(VBox content) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.TABLE_CONTENT.getValue()));
            Parent root = null;
            root = loader.load();
            TableViewController tableViewController = loader.getController();
            content.getChildren().add(root);
            tableViewController.initTitle("Thiết bị");
            createTableHeader(DEVICE_HEADER, tableViewController.getHeaderTable());
            createFilter(tableViewController.getRightHeader());
            List<DeviceEntity> devices = DeviceService.getInstance().getAllAndUsage(filter);
            if (devices.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                tableViewController.getContentTable().getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            devices.forEach(item -> {
                HBox hbox = new HBox();
                hbox.setPrefHeight(45.0);
                hbox.setPrefWidth(200.0);
                Label label1 = createLabelContent(item.getId().toString(), 160.0);
                Label label2 = createLabelContent(item.getName(), 280.0);
                Label label3 = createLabelContent(item.getDescription(), 320.0);
                Label label4;
                if(DeviceService.getInstance().checkUse(item)) {
                    label4 = createLabelContent("Đang được mượn", 120.0);
                    label4.setStyle("-fx-text-fill: #cd4242");
                } else {
                    label4 = createLabelContent("Có thể mượn", 120.0);
                    label4.setStyle("-fx-text-fill: green");
                    hbox.setOnMouseClicked(event -> borrowDevice(item));
                }
                hbox.getChildren().addAll(label1, label2, label3, label4);
                createTableHover(hbox);
                tableViewController.getContentTable().getChildren().add(hbox);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initButton(HBox leftToolbar, HBox rightToolbar) {
        leftToolbar.getChildren().clear();
        Button backButton = createButton(ButtonType.WHITE_BUTTON, "Quay lại");
        backButton.setOnMouseClicked(event -> homeController.initContent(new PersonDetailState(homeController, personId)));
        leftToolbar.getChildren().add(backButton);

    }

    @Override
    public void refresh() {
        homeController.initContent(this);
    }

    private void borrowDevice(DeviceEntity device) {
        boolean confirm = DialogUtil.getInstance()
                .showConfirm("Xác nhận", "Xác nhận mượn thiết bị có mã số: " + device.getId() + "?");
        if(confirm) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(Component.BORROW_CONFIRM.getValue()));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 400, 500);
                Stage stage = new Stage();
                stage.setTitle("Xác nhận mượn");
                stage.initStyle(StageStyle.DECORATED);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                BorrowConfirmController controller = fxmlLoader.getController();
                controller.initContent(stage, device.getId(), personId, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    private List<DeviceEntity> fetchDevices() {
        if (filter == null || filter.isEmpty()) {
            return DeviceService.getInstance().getAll();
        } else {
            return DeviceService.getInstance().getByType(filter);
        }
    }

    public void createFilter(HBox hBox) {
        TextField textField = new TextField();
        textField.setPromptText("Lọc theo loại");
        textField.setText(filter);
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                homeController.initContent(new ListDeviceToUseState(homeController, personId, textField.getText()));
            }
        });
        hBox.getChildren().add(textField);
    }
}
