package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.palexdev.materialfx.controls.MFXButton;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    private Label reasonLabel;

    @FXML
    private Label requestDateLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label startHourLabel;

    @FXML
    private Label requestCreationDateLabel;

    @FXML
    private Label endHourLabel;

    @FXML
    private MFXButton deleteButton;

    @FXML
    private Label response_area_user;

    @FXML
    private Label motiv_rich;

    @FXML
    private Label risp_segr;

    @FXML
    private ImageView imm_mex;

    @FXML
    private ImageView imm_reas;

    private RequestDto request;

    @FXML
    private BorderPane BPaneListPage;

    private MainPageController mainPageController;

    public void setBPane(BorderPane BPane){
        this.BPaneListPage = BPane;
    }

    public BorderPane getPane() {
        return BPaneListPage;
    }

    public void init(RequestDto request) {
        this.request = request;

        reasonLabel.setText(request.getReason());
        requestDateLabel.setText("" + request.getRequestDate());
        startHourLabel.setText("" + request.getStartHour());
        endHourLabel.setText("" + request.getEndHour());
        requestCreationDateLabel.setText("" + request.getCreationDate());

        if ("PENDING".equals(request.getStatus())) {
            motiv_rich.setVisible(true);
            risp_segr.setVisible(false);
            imm_reas.setVisible(true);
            imm_mex.setVisible(false);
            response_area_user.setText(request.getReason());
        } else {
            motiv_rich.setVisible(false);
            risp_segr.setVisible(true);
            imm_reas.setVisible(false);
            imm_mex.setVisible(true);
            response_area_user.setText(request.getAdminResponse());
        }

        StatusLabel.setText(request.getStatus());
        if ("ACCEPTED".equals(request.getStatus())) {
            StatusLabel.setTextFill(Color.GREEN);
        } else if ("REJECTED".equals(request.getStatus())) {
            StatusLabel.setTextFill(Color.RED);
        } else if ("PENDING".equals(request.getStatus())) {
            StatusLabel.setTextFill(Color.ORANGE);
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

        boolean isAdmin = UserManager.getInstance().getUser().role().equalsIgnoreCase("ADMIN");

        if (!isAdmin && ("ACCEPTED".equals(request.getStatus()) || "PENDING".equals(request.getStatus()))) {
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }
    }


    @FXML
    private void deleteRequest() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Eliminazione");
        alert.setHeaderText("Vuoi eliminare questa richiesta?");
        alert.setContentText("Questa azione non può essere annullata.");

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equalsIgnoreCase("OK") || response.getText().equalsIgnoreCase("Yes")) {
                try {
                    deleteRequestAPI(request.getId());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Eliminazione Completata");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La richiesta è stata eliminata con successo.");
                    successAlert.showAndWait();

                    mainPageController.displayRequestHistory();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void deleteRequestAPI(long requestId) throws Exception {
        String apiUrl = "http://localhost:8080/api/v1/request/deleteRequest/" + requestId;
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Errore HTTP: " + responseCode);
        }
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

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

}
