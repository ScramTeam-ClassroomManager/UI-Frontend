package it.unical.classroommanager_ui.controller;


import it.unical.classroommanager_ui.model.UserDto;
import it.unical.classroommanager_ui.model.UserManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;


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
    void deletePressed(ActionEvent event) {
        CompletableFuture.runAsync(() -> {
            try {
                String apiUrl = "http://localhost:8080/api/v1/auth/deleteUser/" + user.getSerialNumber();
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());
                connection.setRequestProperty("Accept", "application/json");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Platform.runLater(() -> {
                        mainPageController.refreshUtenti();
                    });
                } else {
                    System.err.println("Failed to delete user. HTTP error code : " + responseCode);
                }
            } catch (IOException e) {
                System.out.println("Errore nella rimozione dell'utente");
            }
        });
    }

    public void init(MainPageController mainPageController, UserDto user){
        this.mainPageController = mainPageController;
        this.user = user;

        UtentiNameLabel.setText(user.getFirstName());
        UtentiSuernameLabel.setText(user.getLastName());

        UtentiRoleLabel.setText(user.getRole());
        UtentiMatriolaLabel.setText(String.valueOf(user.getSerialNumber()));
    }
}


