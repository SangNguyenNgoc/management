package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.UsageInfoService;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.gui.controller.CheckInController;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.TableViewController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.ButtonType;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.EmptyPane;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class ListCheckInState  extends AbstractState implements State {

    public ListCheckInState(HomeController homeController) {
        setHomeController(homeController);
    }

    private static final List<LabelHeader> CHECKIN_HEADER = List.of(
            new LabelHeader("ID người dùng",  140.0),
            new LabelHeader("Tên", 290.0),
            new LabelHeader("Số điện thoại", 200.0),
            new LabelHeader("Thời gian vào", 250.0)
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
            tableViewController.initTitle("Lịch sử vào");
            createTableHeader(CHECKIN_HEADER, tableViewController.getHeaderTable());
            List<UsageInfoEntity> checkInList = UsageInfoService.getInstance().findAllCheckIn();
            if (checkInList.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                tableViewController.getContentTable().getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            checkInList.forEach(item -> {
                HBox hbox = new HBox();
                hbox.setPrefHeight(45.0);
                hbox.setPrefWidth(200.0);
                Label label1 = createLabelContent(item.getPerson().getId().toString(), 140.0);
                Label label2 = createLabelContent(item.getPerson().getName(), 290.0);
                Label label3 = createLabelContent(item.getPerson().getPhoneNumber(), 200.0);
                Label label4 = createLabelContent(AppUtil.dateToString(item.getCheckinTime()), 250.0);
                hbox.getChildren().addAll(label1, label2, label3, label4);
                hbox.setOnMouseClicked(event -> {
                    System.out.println(item.getId());
                });
                createTableHover(hbox);
                tableViewController.getContentTable().getChildren().add(hbox);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initButton(HBox leftToolbar, HBox rightToolbar) {
        Button button = createButton(ButtonType.BLUE_BUTTON, "Check in");
        button.setPrefSize(90,25);
        rightToolbar.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(Component.CHECKIN_FORM.getValue()));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CheckInController controller = loader.getController();
            controller.setState(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
        });
    }

    @Override
    public void refresh() {
        homeController.initContent(this);
    }
}
