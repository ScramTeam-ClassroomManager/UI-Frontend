package it.unical.classroommanager_ui.controller;


import it.unical.classroommanager_ui.model.UserDto;
import it.unical.classroommanager_ui.model.UserManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;


public class UtentiListInstanceController {

    @FXML
    private Label UtentiMatriolaLabel;

    @FXML
    private Label UtentiNameLabel;

    @FXML
    private Label UtentiRoleLabel;

    @FXML
    private Label UtentiSuernameLabel;

    @FXML
    private MFXButton rimuoviButton;

    MainPageController mainPageController;

    UserDto user;


    @FXML
    void reservePressed(ActionEvent event) {

    }

    public void init(MainPageController mainPageController, UserDto user){
        this.mainPageController = mainPageController;
        this.user = user;

        UtentiNameLabel.setText(user.getName());
        UtentiSuernameLabel.setText(user.getSurename());

        UtentiRoleLabel.setText(user.getRole());
        UtentiMatriolaLabel.setText(String.valueOf(user.getSerialNumber()));
    }
}


