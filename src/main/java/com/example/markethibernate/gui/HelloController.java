package com.example.markethibernate.gui;

import com.example.markethibernate.HelloApplication;
import com.example.markethibernate.utils.HibernateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
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

    private void handleButtonClick(Button button, ImageView imageView, String imageUrl) {
        // Xử lý sự kiện khi nhấp vào nút
        resetButtonStyles();
        button.getStyleClass().add("layout-button-isSelect");
        imageView.setImage(new Image(HelloApplication.class.getResource(imageUrl).toString()));

    }

    private void resetButtonStyles() {
        // Thiết lập lại lớp của tất cả các nút về mặc định
        personButton.getStyleClass().clear();
        deviceButton.getStyleClass().clear();
        penaltyButton.getStyleClass().clear();
        usageButton.getStyleClass().clear();

        personButton.getStyleClass().add("layout-button");
        deviceButton.getStyleClass().add("layout-button");
        penaltyButton.getStyleClass().add("layout-button");
        usageButton.getStyleClass().add("layout-button");

        personImage.setImage(new Image(HelloApplication.class.getResource("image/person.png").toString()));
        deviceImage.setImage(new Image(HelloApplication.class.getResource("image/device.png").toString()));
        penaltyImage.setImage(new Image(HelloApplication.class.getResource("image/penalty.png").toString()));
        usageImage.setImage(new Image(HelloApplication.class.getResource("image/usage.png").toString()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bắt sự kiện khi khởi tạo controller
        personButton.setOnAction(event -> handleButtonClick(personButton, personImage, "image/person-active.png"));
        deviceButton.setOnAction(event -> handleButtonClick(deviceButton, deviceImage, "image/device-active.png"));
        penaltyButton.setOnAction(event -> handleButtonClick(penaltyButton, penaltyImage, "image/penalty-active.png"));
        usageButton.setOnAction(event -> handleButtonClick(usageButton, usageImage, "image/usage-active.png"));
    }
}