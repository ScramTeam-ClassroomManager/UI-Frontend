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
    private Label nameLabel;

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
         URL url = new URL("http://localhost:8080/api/v1/class/booking/"+classroom.getId());
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("PUT");
         connection.setRequestProperty("Accept", "application/json");

         // LETTURA RISPOSTA
         int responseCode = connection.getResponseCode();
         StringBuilder response = new StringBuilder();

         try (BufferedReader reader = new BufferedReader(
                 new InputStreamReader(
                         (responseCode == HttpURLConnection.HTTP_OK) ?
                                 connection.getInputStream() : connection.getErrorStream()))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 response.append(line);
             }
         }
         catch (Exception e) {
             // Handle any other exceptions that may occur
             System.err.println("Problemi con la prenotazione: " + e.getMessage());
             return;
         }

         reserveButton.setStyle("-fx-background-color: red;");
         reserveButton.setDisable(true);





     }

    public void init(MainPageController mainPageController, ClassroomDto classroom){

        this.mainPageController = mainPageController;
        this.classroom = classroom;


        capabilityLabel.setText(String.valueOf(classroom.getCapability()));
        cubeLabel.setText(String.valueOf(classroom.getCube()));
        floorLabel.setText(String.valueOf(classroom.getFloor()));
        nameLabel.setText(classroom.getName());
        num_socketLabel.setText(String.valueOf(classroom.getNumSocket()));
        if(classroom.isProjector()){
            projectorLabel.setText("Si");
        }
        else{
            projectorLabel.setText("No");
        }

        if(!classroom.getAvailable()) {
            reserveButton.setStyle("-fx-background-color: red;");
            reserveButton.setDisable(true);
        }


    }


}
