package it.unical.classroommanager_ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ControllerLogin {

    @FXML
    private Button Button_login;

    @FXML
    private TextField Textfield_matricola;

    @FXML
    private Label Label_errorematr;

    @FXML
    private PasswordField Passwordfield_password;

    @FXML
    private Label Label_errorepass;

    @FXML
    private Label Label_registra;

    @FXML
    private void iscrizione(MouseEvent evento){

    }

    @FXML
    private void login(ActionEvent evento){
        if(Textfield_matricola.getText().isEmpty()){
            Label_errorematr.setVisible(true);
        }
        else{
            if(Passwordfield_password.getText().isEmpty()){
                Label_errorepass.setVisible(true);
            }
        }
    }
}
