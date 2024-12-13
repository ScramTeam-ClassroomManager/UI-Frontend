package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class ProfilePageController {

    @FXML
    private Text emailtext;

    @FXML
    private Text nameText;

    @FXML
    private Label roleLabel;

    @FXML
    private Label serialNumberLabel;

    @FXML
    private Text surnameText;

    MainPageController mainPageController;

    public void init(MainPageController mainPageController){
        this.mainPageController = mainPageController;

        User user = UserManager.getInstance().getUser();

        nameText.setText(user.firstName());
        surnameText.setText(user.lastName());
        emailtext.setText(user.email());

        roleLabel.setText(user.role());
        serialNumberLabel.setText(user.serialNumber());

    }
}
