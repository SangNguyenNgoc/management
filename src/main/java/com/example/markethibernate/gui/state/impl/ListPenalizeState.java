package com.example.markethibernate.gui.state.impl;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.bll.services.PenalizeService;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.gui.controller.HomeController;
import com.example.markethibernate.gui.controller.TableViewController;
import com.example.markethibernate.gui.model.LabelHeader;
import com.example.markethibernate.gui.state.AbstractState;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.utils.Component;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.gui.utils.EmptyPane;
import com.example.markethibernate.utils.AppUtil;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ListPenalizeState extends AbstractState implements State {

    public ListPenalizeState(HomeController homeController) {
        setHomeController(homeController);
    }

    private static final List<LabelHeader> PENALIZE_HEADER = List.of(
            new LabelHeader("ID",  60.0),
            new LabelHeader("Tên", 200.0),
            new LabelHeader("Hình phạt", 220.0),
            new LabelHeader("Phí bồi thường", 120.0),
            new LabelHeader("Ngày lập phiếu", 160),
            new LabelHeader("Trạng thái", 120.0)
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
            tableViewController.initTitle("Phiếu phạt");
            createTableHeader(PENALIZE_HEADER, tableViewController.getHeaderTable());
            List<PenalizeEntity> penalizes = PenalizeService.getInstance().getALl();
            if (penalizes.isEmpty()) {
                EmptyPane emptyPane = new EmptyPane();
                tableViewController.getContentTable().getChildren().add(emptyPane.create("Chưa có dữ liệu!"));
                return;
            }
            penalizes.forEach(item -> {
                HBox hbox = new HBox();
                hbox.setPrefHeight(45.0);
                hbox.setPrefWidth(200.0);
                Label label1 = createLabelContent(item.getId().toString(), 60.0);
                Label label2 = createLabelContent(item.getPerson().getName(), 200.0);
                Label label3 = createLabelContent(item.getType(), 220.0);
                Label label4 = createLabelContent(item.getPayment() == null ? "Không có" : AppUtil.moneyToString(item.getPayment()), 120.0);
                Label label5 = createLabelContent(AppUtil.dateToString(item.getDate()), 160.0);
                Pane label6 = initStatus(item);
                hbox.getChildren().addAll(label1, label2, label3, label4, label5, label6);
                hbox.setOnMouseClicked(event -> {
                    homeController.initContent(new PenalizeDetailState(homeController, item.getId()));
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

    }

    private VBox initStatus(PenalizeEntity penalize) {
        VBox vBox = new VBox();
        vBox.setPrefWidth(120.0);
        vBox.setAlignment(Pos.CENTER);
        Button btn = new Button(penalize.getStatus() ? "Đang thi hành" : "Đã hoàn thành");
        btn.setBackground(Background.fill(Color.WHITE));
        btn.setPrefSize(100, 30);
        if (penalize.getStatus()) {
            btn.setStyle("-fx-text-fill: #cd4242; -fx-border-color: #cd4242; -fx-border-radius: 6; -fx-border-width: 2");
        } else {
            btn.setStyle("-fx-text-fill: green; -fx-border-color: green; -fx-border-radius: 6; -fx-border-width: 2");
        }
        btn.setOnMouseEntered(event -> {
            btn.setUnderline(true);
            btn.setCursor(Cursor.HAND);
        });
        btn.setOnMouseExited(event -> btn.setUnderline(false));
        btn.setOnMouseClicked(event -> {
            Optional<PenalizeEntity> penalizeEntity = PenalizeService.getInstance().updateStatus(penalize.getId().toString());
            if (penalizeEntity.isEmpty()) {
                DialogUtil.getInstance().showAlert("Lỗi","Cập nhật trạng thái không thành công", Alert.AlertType.ERROR);
            }
            refresh();
        });
        vBox.getChildren().add(btn);
        return vBox;
    }

    @Override
    public void refresh() {
        homeController.initContent(this);
    }
}
