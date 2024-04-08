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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ListPersonState extends AbstractState implements State {

    public ListPersonState(HomeController homeController) {
        setHomeController(homeController);
    }

    private static final List<LabelHeader> PERSON_HEADER = List.of(
            new LabelHeader("ID",  140.0),
            new LabelHeader("Tên", 190.0),
            new LabelHeader("Khoa", 100.0),
            new LabelHeader("Chuyên ngành", 140.0),
            new LabelHeader("Email", 190.0),
            new LabelHeader("Điện thoại", 120.0)
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
                tableViewController.contentTable.getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            persons.forEach(item -> {
                HBox hbox = new HBox();
                hbox.setPrefHeight(45.0);
                hbox.setPrefWidth(200.0);
                Label label1 = createLabelContent(item.getId().toString(), 140.0);
                Label label2 = createLabelContent(item.getName(), 190.0);
                Label label3 = createLabelContent(item.getDepartment(), 100.0);
                Label label4 = createLabelContent(item.getProfession(), 140.0);
                Label label5 = createLabelContent(item.getEmail(), 190.0);
                Label label6 = createLabelContent(item.getPhoneNumber(), 120.0);
                hbox.getChildren().addAll(label1, label2, label3, label4, label5, label6);
                hbox.setOnMouseClicked(event -> {
                    homeController.initContent(new PersonDetailState(homeController, item.getId()));
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
            homeController.initContent(new AddPersonState(homeController));
        });
    }


    @Override
    public void refresh() {

    }
}
