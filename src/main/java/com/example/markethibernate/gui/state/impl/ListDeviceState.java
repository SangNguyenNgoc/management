package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.TableViewController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.EmptyPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ListDeviceState extends AbstractState implements State {

    public ListDeviceState(HomeController homeController) {
        setHomeController(homeController);
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
            tableViewController.titleTable.setText("Thiết bị");
            createTableHeader(DEVICE_HEADER, tableViewController.headerTable);
            List<DeviceEntity> devices = DeviceService.getInstance().getAll();
            if (devices.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                tableViewController.contentTable.getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
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
                    System.out.println(item.toString());
                });
                createTableHover(hbox);
                tableViewController.contentTable.getChildren().add(hbox);
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
            System.out.println("add device");
        });
    }

    @Override
    public void refresh() {

    }
}
