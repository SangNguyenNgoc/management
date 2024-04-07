package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.UsageInfoService;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.TableViewController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.EmptyPane;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ListUsageInfoState extends AbstractState implements State {

    public ListUsageInfoState(HomeController homeController) {
        setHomeController(homeController);
    }

    private static final List<LabelHeader> USAGE_INFO_HEADER = List.of(
            new LabelHeader("ID",  60.0),
            new LabelHeader("Tên", 220.0),
            new LabelHeader("Số điện thoại", 100.0),
            new LabelHeader("Thiết bị mượn", 220.0),
            new LabelHeader("Ngày mượn", 140.0),
            new LabelHeader("Ngày trả", 140.0)
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
            tableViewController.titleTable.setText("Phiếu mượn");
            createTableHeader(USAGE_INFO_HEADER, tableViewController.headerTable);
            List<UsageInfoEntity> usageInfos = UsageInfoService.getInstance().findAll();
            if (usageInfos.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                content.getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            usageInfos.forEach(item -> {
                HBox hbox = new HBox();
                hbox.setPrefHeight(45.0);
                hbox.setPrefWidth(200.0);
                Label label1 = createLabelContent(item.getId().toString(), 60.0);
                Label label2 = createLabelContent(item.getPerson().getName(), 220.0);
                Label label3 = createLabelContent(item.getPerson().getPhoneNumber(), 100.0);
                Label label4 = createLabelContent(item.getDevice().getName(), 220.0);
                Label label5 = createLabelContent(AppUtil.dateToString(item.getBorrowTime()), 140.0);
                Label label6 = createLabelContent(AppUtil.dateToString(item.getReturnTime()), 140.0);
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

    }

    @Override
    public void refresh() {

    }
}
