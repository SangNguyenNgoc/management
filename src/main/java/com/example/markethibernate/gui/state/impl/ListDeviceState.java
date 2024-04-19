package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ListDeviceState extends AbstractState implements State {

    private String filter;

    public ListDeviceState(HomeController homeController) {
        setHomeController(homeController);
    }

    public ListDeviceState(HomeController homeController, String filter) {
        setHomeController(homeController);
        this.filter = filter;
    }


    private static final List<LabelHeader> DEVICE_HEADER = List.of(
            new LabelHeader("ID",  160.0),
            new LabelHeader("Tên", 360.0),
            new LabelHeader("Mô tả", 360.0)
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
            List<DeviceEntity> devices = fetchDevices();
            if (devices == null || devices.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                tableViewController.getContentTable().getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            devices.forEach(item -> {
                HBox hbox = new HBox();
                hbox.setPrefHeight(45.0);
                hbox.setPrefWidth(200.0);
                Label label1 = createLabelContent(item.getId().toString(), 160.0);
                Label label2 = createLabelContent(item.getName(), 360.0);
                Label label3 = createLabelContent(item.getDescription(), 360.0);
                hbox.getChildren().addAll(label1, label2, label3);
                hbox.setOnMouseClicked(event -> {
                    homeController.initContent(new DeviceDetailState(homeController, item.getId()));
                });
                createTableHover(hbox);
                tableViewController.getContentTable().getChildren().add(hbox);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initButton(HBox leftToolbar, HBox rightToolbar) {
        Button button = createButton(ButtonType.BLUE_BUTTON, "Tạo mới");
        button.setPrefSize(90,25);
        rightToolbar.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            homeController.initContent(new AddDeviceState(homeController));
        });
    }

    @Override
    public void refresh() {
        homeController.initContent(new ListDeviceState(homeController));
    }

    private List<DeviceEntity> fetchDevices() {
        if (filter == null || filter.isEmpty()) {
            return DeviceService.getInstance().getAll();
        } else if (filter.length() == 1){
            return DeviceService.getInstance().getByType(filter);
        } else {
            DeviceEntity device = DeviceService.getInstance().getById(filter);
            return device == null ? null : List.of(device);
        }
    }

    public void createFilter(HBox hBox) {
        TextField textField = new TextField();
        textField.setPromptText("Lọc theo loại");
        textField.setText(filter);
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                homeController.initContent(new ListDeviceState(homeController, textField.getText()));
            }
        });
        hBox.getChildren().add(textField);
        Button deleteAll = createButton(ButtonType.RED_BUTTON, "Xóa tất cả");
        hBox.getChildren().add(deleteAll);
        if (filter == null || filter.length() != 1 || AppUtil.parseId(filter) == null) {
            deleteAll.setDisable(true);
            return;
        }
        deleteAll.setOnMouseClicked(event -> {
            boolean confirm = DialogUtil.getInstance().showConfirm("Xác nhận", "Xác nhận muốn xóa tất cả thiết bị này?");
            if (confirm) {
                int result = DeviceService.getInstance().deleteDeviceByType(textField.getText());
                if (result > 0) {
                    DialogUtil.getInstance().showAlert("Thông báo", "Xóa thành công", Alert.AlertType.INFORMATION);
                    refresh();
                } else {
                    DialogUtil.getInstance().showAlert("Thông báo", "Xóa thất bại, lỗi bất định", Alert.AlertType.INFORMATION);
                }
            }
        });
    }
}
