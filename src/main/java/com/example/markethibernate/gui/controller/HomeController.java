package com.example.markethibernate.gui.controller;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.gui.state.State;
import com.example.markethibernate.gui.state.impl.*;
import com.example.markethibernate.gui.utils.Component;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Button statisticButton;
    @FXML
    private ImageView statisticImage;
    @FXML
    private Button checkInButton;
    @FXML
    private ImageView checkInImage;
    @FXML
    private Pane content;
    @FXML
    private ImageView personImage;
    @FXML
    private ImageView deviceImage;
    @FXML
    private ImageView penaltyImage;
    @FXML
    private ImageView usageImage;
    @FXML
    private Button personButton;
    @FXML
    private Button deviceButton;
    @FXML
    private Button penaltyButton;
    @FXML
    private Button usageButton;

    private String active;

    public void setActive(String active) {
        this.active = active;
    }

    private void handleButtonClick(Button button, ImageView imageView, String imageUrl) {
        // Xử lý sự kiện khi nhấp vào nút
        resetButtonStyles();
        setActive(button.getId());
        button.getStyleClass().add("layout-button-isSelect");
        imageView.setImage(new Image(HelloApplication.class.getResource(imageUrl).toString()));

    }

    private void handleButtonHover(Button button, ImageView imageView, String imageUrl) {
        button.getStyleClass().add("layout-button-isSelect");
        imageView.setImage(new Image(HelloApplication.class.getResource(imageUrl).toString()));

    }

    private void resetButtonStyles() {
        // Thiết lập lại lớp của tất cả các nút về mặc định
        personButton.getStyleClass().clear();
        deviceButton.getStyleClass().clear();
        penaltyButton.getStyleClass().clear();
        usageButton.getStyleClass().clear();
        checkInButton.getStyleClass().clear();
        statisticButton.getStyleClass().clear();

        personButton.getStyleClass().add("layout-button");
        deviceButton.getStyleClass().add("layout-button");
        penaltyButton.getStyleClass().add("layout-button");
        usageButton.getStyleClass().add("layout-button");
        checkInButton.getStyleClass().add("layout-button");
        statisticButton.getStyleClass().add("layout-button");

        personImage.setImage(new Image(HelloApplication.class.getResource("image/person.png").toString()));
        deviceImage.setImage(new Image(HelloApplication.class.getResource("image/device.png").toString()));
        penaltyImage.setImage(new Image(HelloApplication.class.getResource("image/penalty.png").toString()));
        usageImage.setImage(new Image(HelloApplication.class.getResource("image/usage.png").toString()));
        checkInImage.setImage(new Image(HelloApplication.class.getResource("image/user-check.png").toString()));
        statisticImage.setImage(new Image(HelloApplication.class.getResource("image/statistic.png").toString()));
    }

    private void resetButtonStyles(Button button, ImageView imageView, String urlImg, String buttonName) {
        if(!active.equals(buttonName)) {
            button.getStyleClass().clear();
            button.getStyleClass().add("layout-button");
            imageView.setImage(new Image(HelloApplication.class.getResource(urlImg).toString()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleButtonClick(personButton, personImage, "image/person-active.png");
        // Bắt sự kiện khi khởi tạo controller
        personButton.setOnAction(event -> {
            handleButtonClick(personButton, personImage, "image/person-active.png");
            initContent(new ListPersonState(this));
        });
        deviceButton.setOnAction(event -> {
            handleButtonClick(deviceButton, deviceImage, "image/device-active.png");
            initContent(new ListDeviceState(this));
        });
        penaltyButton.setOnAction(event -> {
            handleButtonClick(penaltyButton, penaltyImage, "image/penalty-active.png");
            initContent(new ListPenalizeState(this));
        });
        usageButton.setOnAction(event -> {
            handleButtonClick(usageButton, usageImage, "image/usage-active.png");
            initContent(new ListUsageInfoState(this));
        });
        checkInButton.setOnAction(event -> {
            handleButtonClick(checkInButton, checkInImage, "image/user-check-active.png");
            initContent(new ListCheckInState(this));
        });
        statisticButton.setOnAction(event -> {
            handleButtonClick(statisticButton, statisticImage, "image/statistic-active.png");
            initContent(new StatCheckInState(this));
        });

        personButton.setOnMouseEntered(event -> handleButtonHover(personButton, personImage, "image/person-active.png"));
        deviceButton.setOnMouseEntered(event -> handleButtonHover(deviceButton, deviceImage, "image/device-active.png"));
        penaltyButton.setOnMouseEntered(event -> handleButtonHover(penaltyButton, penaltyImage, "image/penalty-active.png"));
        usageButton.setOnMouseEntered(event -> handleButtonHover(usageButton, usageImage, "image/usage-active.png"));
        checkInButton.setOnMouseEntered(event -> handleButtonHover(checkInButton, checkInImage, "image/user-check-active.png"));
        statisticButton.setOnMouseEntered(event -> handleButtonHover(statisticButton, statisticImage, "image/statistic-active.png"));


        personButton.setOnMouseExited(event -> resetButtonStyles(personButton, personImage, "image/person.png","personButton"));
        deviceButton.setOnMouseExited(event -> resetButtonStyles(deviceButton, deviceImage, "image/device.png","deviceButton"));
        penaltyButton.setOnMouseExited(event -> resetButtonStyles(penaltyButton, penaltyImage, "image/penalty.png","penaltyButton"));
        usageButton.setOnMouseExited(event -> resetButtonStyles(usageButton, usageImage, "image/usage.png","usageButton"));
        checkInButton.setOnMouseExited(event -> resetButtonStyles(checkInButton, checkInImage, "image/user-check.png","checkInButton"));
        statisticButton.setOnMouseExited(event -> resetButtonStyles(statisticButton, statisticImage, "image/statistic.png","statisticButton"));

        initContent(new ListPersonState(this));
    }

    public void initContent(State state) {
        try {
            content.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(Component.CONTENT_PAGE.getValue()));
            Parent root = null;
            root = loader.load();
            ContentController controller = loader.getController();
            controller.initContent(state);
            content.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}