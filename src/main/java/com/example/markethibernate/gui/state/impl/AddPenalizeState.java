package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.PenalizeFormController;
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

public class AddPenalizeState extends AbstractState implements State {

    private final Long personId;

    public AddPenalizeState(HomeController homeController, Long personId) {
        this.homeController = homeController;
        this.personId = personId;
    }

    @Override
    public void renderView(VBox content) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.PENALIZE_CREATE_FORM.getValue()));
            Parent root = null;
            root = loader.load();
            PenalizeFormController controller = loader.getController();
            controller.initToCreatePenalize(homeController, personId);
            content.getChildren().add(root);
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

    }
}
