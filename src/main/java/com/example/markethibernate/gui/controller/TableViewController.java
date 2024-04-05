package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.services.DeviceService;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.utils.EmptyPane;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class TableViewController {

    @FXML
    private Label titleTable;
    @FXML
    private HBox headerTable;
    @FXML
    private VBox contentTable;
    @FXML
    private ScrollPane scrollPane;

    private static final List<LabelHeader> PERSON_HEADER = List.of(
            new LabelHeader("ID",  60.0),
            new LabelHeader("Tên", 220.0),
            new LabelHeader("Khoa", 100.0),
            new LabelHeader("Chuyên ngành", 140.0),
            new LabelHeader("Email", 220.0),
            new LabelHeader("Điện thoại", 140.0)
    );

    private static final List<LabelHeader> DEVICE_HEADER = List.of(
            new LabelHeader("ID",  160.0),
            new LabelHeader("Tên", 360.0),
            new LabelHeader("Mô tả", 360.0)
    );

    public void initListPerson() {
        titleTable.setText("Thành viên");
        createTableHeader(PERSON_HEADER);
        renderListPerson();
    }

    public void initListDevice() {
        titleTable.setText("Thiết bị");
        createTableHeader(DEVICE_HEADER);
        renderListDevice();
    }

    private void createTableHeader(List<LabelHeader> labelHeaders) {
        labelHeaders.forEach(item -> {
            Label label = createLabelHeader(item.getText(), item.getWidth());
            headerTable.getChildren().add(label);
        });
    }

    private Label createLabelHeader(String text, double prefWidth) {
        Label label = new Label(text);
        label.setPrefHeight(45.0);
        label.setPrefWidth(prefWidth);
        label.setTextFill(Color.GRAY);
        label.setPadding(new Insets(0, 0, 0, 10.0));
        label.setFont(new Font(14.0));
        return label;
    }

    private Label createLabelContent(String text, double prefWidth) {
        Label label = new Label(text);
        label.setPrefHeight(45.0);
        label.setPrefWidth(prefWidth);
        label.setTextFill(Color.web("#333333"));
        label.setPadding(new Insets(0, 0, 0, 10.0));
        label.setFont(new Font(14.0));
        return label;
    }

    private void renderListPerson() {
        List<PersonEntity> persons = PersonService.getInstance().getAll();
        if (persons.isEmpty()) {
            EmptyPane emptyPane = new EmptyPane();
            contentTable.getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
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
                System.out.println(item.toString());
            });
            contentTable.getChildren().add(hbox);
        });
    }

    private void renderListDevice() {
        List<DeviceEntity> devices = DeviceService.getInstance().getAll();
        if (devices.isEmpty()) {
            EmptyPane emptyPane = new EmptyPane();
            contentTable.getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
            return;
        }
        devices.forEach(item -> {
            HBox hbox = new HBox();
            hbox.setPrefHeight(45.0);
            hbox.setPrefWidth(200.0);
            Label label1 = createLabelContent(item.getId().toString(), 160.0);
            Label label2 = createLabelContent(item.getName(), 360.0);
            Label label3 = createLabelContent(item.getDescription(), 360.0);
            hbox.getChildren().addAll(label1, label2, label3);
            hbox.setOnMouseClicked(event -> {
                System.out.println(item.toString());
            });
            contentTable.getChildren().add(hbox);
        });
    }

}
