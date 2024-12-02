package it.unical.classroommanager_ui.controller;


import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ClassroomListInstanceController {

    @FXML
    private Label capabilityLabel;

    @FXML
    private Label cubeLabel;

    @FXML
    private Label classroomNameLabel;

    @FXML
    private Button reserveButton;





    MainPageController mainPageController;
    ClassroomDto classroom;


    @FXML
    void reservePressed(ActionEvent event) throws IOException {
        if (mainPageController != null) {
            mainPageController.displayClassroomDetails(classroom);
        } else {
            System.err.println("Errore: mainPageController è null!");
        }
    }

    public void init(MainPageController mainPageController, ClassroomDto classroom) {
        this.mainPageController = mainPageController;
        this.classroom = classroom;

        capabilityLabel.setText(String.valueOf(classroom.getCapability()));
        cubeLabel.setText(String.valueOf(classroom.getCubeNumber()));
        classroomNameLabel.setText(classroom.getName());

        if (UserManager.getInstance().getToken().isEmpty()){
            reserveButton.setDisable(true);
        }
    }



}