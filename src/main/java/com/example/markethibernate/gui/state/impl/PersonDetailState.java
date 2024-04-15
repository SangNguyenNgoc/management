package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
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

public class PersonDetailState extends AbstractState implements State {

    private final Long personId;

    public PersonDetailState(HomeController homeController, Long personId) {
        setHomeController(homeController);
        this.personId = personId;
    }

    @Override
    public void renderView(VBox content) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.USER_DETAIL_FORM.getValue()));
            Parent root = null;
            root = loader.load();
            UserDetailFormController controller = loader.getController();
            controller.initPerson(personId);
            controller.initValidate();
            controller.initUpdatePersonButton(homeController);
            content.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initButton(HBox leftToolbar, HBox rightToolbar) {
        leftToolbar.getChildren().clear();
        Button backButton = createButton(ButtonType.WHITE_BUTTON, "Quay lại");
        backButton.setOnMouseClicked(event -> homeController.initContent(new ListPersonState(homeController)));
        leftToolbar.getChildren().add(backButton);

        Button usageButton = createButton(ButtonType.BLUE_BUTTON, "Lập phiếu mượn");
        rightToolbar.getChildren().add(usageButton);
        usageButton.setOnMouseClicked(event -> {
            homeController.initContent(new ListDeviceToUseState(homeController, personId));
        });

        Button penalizeButton = createButton(ButtonType.YELLOW_BUTTON, "Phạt");
        penalizeButton.setPrefSize(90,35);
        penalizeButton.setOnMouseClicked(event -> {
            homeController.initContent(new AddPenalizeState(homeController, personId));
        });
        rightToolbar.getChildren().add(penalizeButton);

    }

    @Override
    public void refresh() {

    }
}
