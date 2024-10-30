package it.unical.classroommanager_ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterPageController {

    @FXML
    private Label emailErrorLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label loginAccessLabel;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label repeatPasswordErrorLabel;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Label surnameErrorLabel;

    @FXML
    private TextField surnameField;

    @FXML
    void registerAction(ActionEvent event) {

    }

    @FXML
    public void initialize(){
        //TODO
    }

}
