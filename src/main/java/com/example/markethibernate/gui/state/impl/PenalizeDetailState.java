package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.PenalizeDetailController;
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

public class PenalizeDetailState extends AbstractState implements State {

    private final Long penalizeId;

    public PenalizeDetailState(HomeController homeController, Long penalizeId) {
        setHomeController(homeController);
        this.penalizeId = penalizeId;
    }

    @Override
    public void renderView(VBox content) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.PENALIZE_DETAIL_FORM.getValue()));
            Parent root = null;
            root = loader.load();
            PenalizeDetailController controller = loader.getController();
            controller.init(penalizeId);
            content.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initButton(HBox leftToolbar, HBox rightToolbar) {
        leftToolbar.getChildren().clear();
        Button backButton = createButton(ButtonType.WHITE_BUTTON, "Quay lại");
        backButton.setOnMouseClicked(event -> homeController.initContent(new ListPenalizeState(homeController)));
        leftToolbar.getChildren().add(backButton);
    }

    @Override
    public void refresh() {

    }
}
