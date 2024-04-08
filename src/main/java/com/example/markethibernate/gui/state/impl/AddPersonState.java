package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.UserDetailFormController;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.IconType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddPersonState extends AbstractState implements State {

    public AddPersonState(HomeController homeController) {
        setHomeController(homeController);
    }


    @Override
    public void renderView(VBox content) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.USER_DETAIL_FORM.getValue()));
            Parent root = null;
            root = loader.load();
            UserDetailFormController controller = loader.getController();
            controller.initValidate();
            controller.initAddPersonButton();
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

        Button openExcelButton = createButton(ButtonType.GREEN_BUTTON, "Import ", IconType.EXCEL);
        openExcelButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn file Excel");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx, *.xls)", "*.xlsx", "*.xls");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                System.out.println("Tệp Excel được chọn: " + selectedFile.getAbsolutePath());
            } else {
                System.out.println("Không có tệp Excel nào được chọn.");
            }
        });
        rightToolbar.getChildren().add(openExcelButton);
    }

    @Override
    public void refresh() {
    }
}
