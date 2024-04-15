package com.example.markethibernate.gui.controller;

import com.example.markethibernate.bll.dtos.EmailChecked;
import com.example.markethibernate.bll.services.PersonService;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.gui.state.impl.ListPersonState;
import com.example.markethibernate.gui.utils.DialogUtil;
import com.example.markethibernate.gui.utils.ValidateForm;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        ValidateForm.addFocusListener(
                mailInput,
                EmailChecked.builder().id(idInput.getText()).build(),
                mailValidate,
                PersonService.getInstance()::checkEmail
        );
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
            submitValidate();
            PersonEntity person = PersonService.getInstance().addPerson(
                    idInput.getText(),
                    nameInput.getText(),
                    mailInput.getText(),
                    idInput.getText(),
                    departmentInput.getText(),
                    majorInput.getText(),
                    numberPhoneInput.getText()
            );
            if (person != null) {
                DialogUtil.getInstance().showAlert("Thông báo", "Thêm thành viên thành công", Alert.AlertType.INFORMATION);
                clearInput();
            }
        });
        buttons.getChildren().remove(1);
    }

    public void initUpdatePersonButton(HomeController controller) {
        userUpdateButton.setText("Cập nhật");
        idInput.setDisable(true);
        userUpdateButton.setOnMouseClicked(event -> {
            submitValidate();
            PersonEntity person = PersonService.getInstance().updatePerson(
                    idInput.getText(),
                    nameInput.getText(),
                    mailInput.getText(),
                    departmentInput.getText(),
                    majorInput.getText(),
                    numberPhoneInput.getText()
            );
            if (person != null) {
                DialogUtil.getInstance().showAlert("Thông báo", "Cập nhật thành viên thành công", Alert.AlertType.INFORMATION);
            }
        });
        userDeleteButton.setOnMouseClicked(event -> {
            boolean delete = DialogUtil.getInstance().showConfirm(
                    "Xóa thành viên", "Xác nhận muốn xóa thành viên này?");
            if(delete) {
                PersonEntity person = PersonService.getInstance().deletePersonById(idInput.getText());
                if(person != null) {
                    DialogUtil.getInstance().showAlert("Thông báo", "Xóa thành viên thành công", Alert.AlertType.INFORMATION);
                    controller.initContent(new ListPersonState(controller));
                }
            }
        });
    }

    public void submitValidate() {
        ValidateForm.validate(
                mailInput,
                EmailChecked.builder().id(idInput.getText()).build(),
                mailValidate,
                PersonService.getInstance()::checkEmail
        );
        ValidateForm.validate(nameInput, nameValidate, PersonService.getInstance()::checkName);
        ValidateForm.validate(numberPhoneInput, numberPhoneValidate, PersonService.getInstance()::checkPhoneNumber);
        ValidateForm.validate(departmentInput, departmentValidate, PersonService.getInstance()::checkDepartment);
        ValidateForm.validate(majorInput, majorValidate, PersonService.getInstance()::checkProfession);
    }

    public void clearInput() {
        idInput.setText("");
        mailInput.setText("");
        nameInput.setText("");
        numberPhoneInput.setText("");
        departmentInput.setText("");
        majorInput.setText("");
    }



}
