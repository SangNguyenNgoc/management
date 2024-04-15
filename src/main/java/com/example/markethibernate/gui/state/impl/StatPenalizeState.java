package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.StatisticController;
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

public class StatPenalizeState extends AbstractState implements State {

    public StatPenalizeState(HomeController homeController) {
        setHomeController(homeController);
    }

    @Override
    public void renderView(VBox content) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.STATISTIC.getValue()));
            Parent root = null;
            root = loader.load();
            StatisticController controller = loader.getController();
            controller.setTotalPenalize();
            controller.initStatPenalize();
            content.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initButton(HBox leftToolbar, HBox rightToolbar) {
        leftToolbar.getChildren().clear();
        rightToolbar.getChildren().clear();
        Button button = createButton(ButtonType.WHITE_BUTTON, "Thống kê");
        leftToolbar.getChildren().add(button);

        Button penalizeButton = createButton(ButtonType.BLUE_BUTTON, "Phiếu phạt");
        penalizeButton.setPrefSize(120, 30);
        penalizeButton.setUnderline(true);

        Button checkInButton = createButton(ButtonType.BLUE_BUTTON, "Lượt ra vào");
        checkInButton.setPrefSize(120, 30);
        checkInButton.setOnMouseClicked(event -> homeController.initContent(new StatCheckInState(homeController)));

        Button timeButton = createButton(ButtonType.BLUE_BUTTON, "Theo thời gian");
        timeButton.setPrefSize(120, 30);
        timeButton.setOnMouseClicked(event -> homeController.initContent(new StatDeviceByTimeState(homeController)));

        Button nameButton = createButton(ButtonType.BLUE_BUTTON, "Theo thiết bị");
        nameButton.setOnMouseClicked(event -> homeController.initContent(new StatDeviceByIdState(homeController)));
        nameButton.setPrefSize(120, 30);

        rightToolbar.getChildren().addAll(checkInButton, timeButton, nameButton, penalizeButton);
    }

    @Override
    public void refresh() {

    }
}
