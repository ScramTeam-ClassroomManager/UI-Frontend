package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.palexdev.materialfx.controls.MFXButton;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.Status;
import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class RequestListInstanceController {

    @FXML
    private Label reasonLabel;

    @FXML
    private Label requestDateLabel;

    @FXML
    private Label startHourLabel;

    @FXML
    private Label endHourLabel;

    @FXML
    private Label classroomNameLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label requestCreationDateLabel;

    @FXML
    private MFXButton AcceptButton;

    @FXML
    private MFXButton RefuseButton;

    private MainPageController mainPageController;
    private RequestDto request;

    public void init(MainPageController mainPageController, RequestDto request) {
        this.mainPageController = mainPageController;
        this.request = request;

        reasonLabel.setText(request.getReason());
        requestDateLabel.setText(request.getRequestDate().toString());
        startHourLabel.setText(request.getStartHour().toString());
        endHourLabel.setText(request.getEndHour().toString());
        requestCreationDateLabel.setText(request.getCreationDate().toString());

        CompletableFuture.runAsync(() -> {
            try {
                String classroomName = getClassroomName(request.getClassroomId());
                Platform.runLater(() -> classroomNameLabel.setText(classroomName));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> classroomNameLabel.setText("Errore"));
            }
        });

        CompletableFuture.runAsync(() -> {
            try {
                String userName = getUserName(request.getUserSerialNumber());
                Platform.runLater(() -> userNameLabel.setText(userName));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> userNameLabel.setText("Errore"));
            }
        });
    }

    private String getClassroomName(long classroomId) throws IOException {
        String apiUrl = "http://localhost:8080/api/v1/class/getClassName/" + classroomId;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());
        connection.setRequestProperty("Accept", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.readLine();
        }
    }


    private String getUserName(String userSerialNumber) throws IOException {
        String apiUrl = "http://localhost:8080/api/v1/auth/getFirstSecondUser/" + userSerialNumber;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());
        connection.setRequestProperty("Accept", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(reader, User.class);
            return user.firstName() + " " + user.lastName();
        }
    }

    private void updateRequestStatus(Status newStatus) {
        CompletableFuture.runAsync(() -> {
            try {
                String apiUrl = "http://localhost:8080/api/v1/request/changeStatusRequest/" + request.getId() + "/status?status=" + newStatus;
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());
                connection.setRequestProperty("Accept", "application/json");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Status aggiornato");
                        alert.setHeaderText(null);
                        alert.setContentText("Lo stato della richiesta è stato aggiornato con successo a " + newStatus + ".");
                        alert.showAndWait();

                        mainPageController.refreshRequestList();
                    });
                } else {
                    System.err.println("Failed to update status. HTTP error code : " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    void PressAccept(ActionEvent event) {
        updateRequestStatus(Status.ACCEPTED);
    }

    @FXML
    void PressRefuse(ActionEvent event) {
        updateRequestStatus(Status.REJECTED);
    }
}

