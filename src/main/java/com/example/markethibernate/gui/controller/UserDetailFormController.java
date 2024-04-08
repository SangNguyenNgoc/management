package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.utils.ValidateForm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class UserDetailFormController {

    @FXML
    private HBox buttons;
    @FXML
    private Button userDeleteButton;
    @FXML
    private Button userUpdateButton;
    @FXML
    private TextField idInput;
    @FXML
    private TextField departmentInput;
    @FXML
    private TextField numberPhoneInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField mailInput;
    @FXML
    private TextField majorInput;
    @FXML
    private Label idValidate;
    @FXML
    private Label departmentValidate;
    @FXML
    private Label numberPhoneValidate;
    @FXML
    private Label mailValidate;
    @FXML
    private Label nameValidate;
    @FXML
    private Label majorValidate;
    @FXML
    private HBox header;
    @FXML
    private HBox leftHeader;
    @FXML
    private HBox rightHeader;

    public void initValidate() {
        ValidateForm.addFocusListener(idInput, idValidate, PersonService.getInstance()::checkId);
        ValidateForm.addFocusListener(mailInput, mailValidate, PersonService.getInstance()::checkEmail);
        ValidateForm.addFocusListener(nameInput, nameValidate, PersonService.getInstance()::checkName);
        ValidateForm.addFocusListener(numberPhoneInput, numberPhoneValidate, PersonService.getInstance()::checkPhoneNumber);
        ValidateForm.addFocusListener(departmentInput, departmentValidate, PersonService.getInstance()::checkDepartment);
        ValidateForm.addFocusListener(majorInput, majorValidate, PersonService.getInstance()::checkProfession);
    }


    public void initPerson(Long id) {
        PersonEntity person = PersonService.getInstance().getById(id.toString());
        idInput.setText(person.getId().toString());
        nameInput.setText(person.getName());
        mailInput.setText(person.getEmail());
        departmentInput.setText(person.getDepartment());
        majorInput.setText(person.getProfession());
        numberPhoneInput.setText(person.getPhoneNumber());
    }

    public void initAddPersonButton() {
        userUpdateButton.setText("Lưu");
        userUpdateButton.setOnMouseClicked(event -> {
            System.out.println("save person");
        });
        buttons.getChildren().remove(1);
    }

    public void initUpdatePersonButton() {
        userUpdateButton.setText("Cập nhật");
        userUpdateButton.setOnMouseClicked(event -> {
            System.out.println("update person");
        });
    }

}
