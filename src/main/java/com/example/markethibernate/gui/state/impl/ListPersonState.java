package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.TableViewController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.EmptyPane;
import com.example.markethibernate.gui.utils.IconType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListPersonState extends AbstractState implements State {

    public ListPersonState(HomeController homeController) {
        setHomeController(homeController);
    }

    private static final List<LabelHeader> PERSON_HEADER = List.of(
            new LabelHeader("ID",  60.0),
            new LabelHeader("Tên", 220.0),
            new LabelHeader("Khoa", 100.0),
            new LabelHeader("Chuyên ngành", 140.0),
            new LabelHeader("Email", 220.0),
            new LabelHeader("Điện thoại", 140.0)
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
            tableViewController.titleTable.setText("Thành viên");
            createTableHeader(PERSON_HEADER, tableViewController.headerTable);
            List<PersonEntity> persons = PersonService.getInstance().getAll();
            if (persons.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                content.getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            persons.forEach(item -> {
                HBox hbox = new HBox();
                hbox.setPrefHeight(45.0);
                hbox.setPrefWidth(200.0);
                Label label1 = createLabelContent(item.getId().toString(), 60.0);
                Label label2 = createLabelContent(item.getName(), 220.0);
                Label label3 = createLabelContent(item.getDepartment(), 100.0);
                Label label4 = createLabelContent(item.getProfession(), 140.0);
                Label label5 = createLabelContent(item.getEmail(), 220.0);
                Label label6 = createLabelContent(item.getPhoneNumber(), 140.0);
                hbox.getChildren().addAll(label1, label2, label3, label4, label5, label6);
                hbox.setOnMouseClicked(event -> {
                    System.out.println(item.getId());
                });
                tableViewController.contentTable.getChildren().add(hbox);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initAddButton(HBox leftToolbar, HBox rightToolbar) {
        Button button = createButton(ButtonType.BLUE_BUTTON, "Tạo mới");
        button.setPrefSize(90,25);
        rightToolbar.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            System.out.println("add person");
        });
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
