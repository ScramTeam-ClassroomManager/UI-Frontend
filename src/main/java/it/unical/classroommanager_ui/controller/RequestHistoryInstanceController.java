package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class RequestHistoryInstanceController {

    @FXML
    private Label StatusLabel;

    @FXML
    private Label classroomNameLabel;

    @FXML
    private Label endHourLabel;

    @FXML
    private Label reasonLabel;

    @FXML
    private Label requestCreationDateLabel;

    @FXML
    private Label requestDateLabel;

    @FXML
    private Label startHourLabel;

    @FXML
    private Label userNameLabel;

    public void init(MainPageController mainPageController, RequestDto request) {
        reasonLabel.setText(request.getReason());
        requestDateLabel.setText("" + request.getRequestDate());
        startHourLabel.setText("" + request.getStartHour());
        endHourLabel.setText("" + request.getEndHour());
        requestCreationDateLabel.setText("" + request.getCreationDate());

        StatusLabel.setText(request.getStatus().toString());
        if ("ACCEPTED".equals(request.getStatus().toString())) {
            StatusLabel.setTextFill(Color.GREEN);
        } else if ("REJECTED".equals(request.getStatus().toString())) {
            StatusLabel.setTextFill(Color.RED);
        }

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
}
