package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.UserDetailFormController;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.gui.utils.IconType;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        openExcelButton.setOnMouseClicked(event -> handleImportExcel());
        rightToolbar.getChildren().add(openExcelButton);
    }

    @Override
    public void refresh() {
    }

    private void handleImportExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file Excel");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx, *.xls)", "*.xlsx", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String result = PersonService.getInstance().saveFromExcel(selectedFile);
            if(result == null) {
                DialogUtil.getInstance().showAlert("Lỗi", "Import file thất bại, đã có lỗi xảy ra.", Alert.AlertType.ERROR);
            }
            if (result.isBlank()) {
                DialogUtil.getInstance().showAlert("Thông báo", "Import file thành công.", Alert.AlertType.INFORMATION);
            } else {
                DialogUtil.getInstance().showAlert("Lỗi", "Lỗi định dạng dữ liệu ở các dòng: " + result, Alert.AlertType.ERROR);
            }
        } else {
            System.out.println("Không có tệp Excel nào được chọn.");
        }
    }
}
