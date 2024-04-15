package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.controller.DeviceDetailFormController;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.UserDetailFormController;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DeviceDetailState extends AbstractState implements State {

    private final Long deviceId;

    public DeviceDetailState(HomeController homeController, Long deviceId) {
        setHomeController(homeController);
        this.deviceId = deviceId;
    }
    @Override
    public void renderView(VBox content) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.DEVICE_DETAIL_FORM.getValue()));
            Parent root = null;
            root = loader.load();
            DeviceDetailFormController controller = loader.getController();
            controller.initDevice(deviceId);
            controller.initValidate();
            controller.initUpdateDeviceButton(homeController);
            content.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initButton(HBox leftToolbar, HBox rightToolbar) {
        leftToolbar.getChildren().clear();
        Button backButton = createButton(ButtonType.WHITE_BUTTON, "Quay lại");
        backButton.setOnMouseClicked(event -> homeController.initContent(new ListDeviceState(homeController)));
        leftToolbar.getChildren().add(backButton);
    }

    @Override
    public void refresh() {

    }
}
