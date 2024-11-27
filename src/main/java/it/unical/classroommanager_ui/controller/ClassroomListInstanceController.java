package it.unical.classroommanager_ui.controller;


import it.unical.classroommanager_ui.model.ClassroomDto;
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
    private Label floorLabel;

    @FXML
    private Label classroomNameLabel;

    @FXML
    private Label num_socketLabel;

    @FXML
    private Label projectorLabel;

    @FXML
    private Button reserveButton;





    MainPageController mainPageController;
    ClassroomDto classroom;

     @FXML
     void reservePressed(ActionEvent event) throws IOException {
         mainPageController.displayClassroomDetails(classroom);
     }

    public void init(MainPageController mainPageController, ClassroomDto classroom){

        this.mainPageController = mainPageController;
        this.classroom = classroom;


        capabilityLabel.setText(String.valueOf(classroom.getCapability()));
        cubeLabel.setText(String.valueOf(classroom.getCubeNumber()));
        floorLabel.setText(String.valueOf(classroom.getFloor()));
        classroomNameLabel.setText(classroom.getName());
        num_socketLabel.setText(String.valueOf(classroom.getNumSocket()));
        if(classroom.isProjector()){
            projectorLabel.setText("Si");
        }
        else{
            projectorLabel.setText("No");
        }
    }


}
