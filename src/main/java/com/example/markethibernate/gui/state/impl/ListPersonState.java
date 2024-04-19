package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.TableViewController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.gui.utils.EmptyPane;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPersonState extends AbstractState implements State {

    private String filter;

    public ListPersonState(HomeController homeController) {
        setHomeController(homeController);
    }

    public ListPersonState(HomeController homeController, String filter) {
        setHomeController(homeController);
        this.filter = filter;
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
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(Component.TABLE_CONTENT.getValue()));
            Parent root = loader.load();
            TableViewController tableViewController = loader.getController();
            content.getChildren().add(root);
            tableViewController.initTitle("Thành viên");
            createFilter(tableViewController.getRightHeader());
            createTableHeader(PERSON_HEADER, tableViewController.getHeaderTable());
            List<PersonEntity> persons = fetchPersons();
            if (persons == null || persons.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                tableViewController.getContentTable().getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            persons.forEach(item -> {
                HBox hbox = createRowTable(item);
                tableViewController.getContentTable().getChildren().add(hbox);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PersonEntity> fetchPersons() {
        if (filter == null || filter.isEmpty()) {
            return PersonService.getInstance().getAll();
        } else if (filter.length() == 2){
            return PersonService.getInstance().getByYear(filter);
        } else {
            PersonEntity person = PersonService.getInstance().getById(filter);
            return person == null ? null : List.of(person);
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
        homeController.initContent(new ListPersonState(homeController));
    }

    public HBox createRowTable(PersonEntity item) {
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
        return hbox;
    }

    public void createFilter(HBox hBox) {
        TextField textField = new TextField();
        textField.setPromptText("Lọc theo khóa");
        textField.setText(filter);
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                homeController.initContent(new ListPersonState(homeController, textField.getText()));
            }
        });
        hBox.getChildren().add(textField);
        Button deleteAll = createButton(ButtonType.RED_BUTTON, "Xóa tất cả");
        hBox.getChildren().add(deleteAll);
        if (filter == null ||filter.length() != 2 || AppUtil.parseId(filter) == null) {
            deleteAll.setDisable(true);
            return;
        }
        deleteAll.setOnMouseClicked(event -> {
            boolean confirm = DialogUtil.getInstance().showConfirm("Xác nhận", "Xác nhận muốn xóa tất cả thành viên này?");
            if (confirm) {
                int result = PersonService.getInstance().deletePersonByYear(textField.getText());
                if (result > 0) {
                    DialogUtil.getInstance().showAlert("Thông báo", "Xóa thành công", Alert.AlertType.INFORMATION);
                    refresh();
                } else {
                    DialogUtil.getInstance().showAlert("Thông báo", "Xóa thất bại, lỗi bất định", Alert.AlertType.INFORMATION);
                }
            }
        });
    }
}
