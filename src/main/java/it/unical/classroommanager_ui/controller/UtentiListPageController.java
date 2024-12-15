package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserDto;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.UtentiListInstanceView;
import it.unical.classroommanager_ui.view.UtentiListPageView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;


import com.fasterxml.jackson.core.type.TypeReference;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.view.ClassroomListInstanceView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import java.io.IOException;

public class UtentiListPageController {

    @FXML
    private BorderPane BPaneListPage;

    @FXML
    private ListView<UtentiListInstanceView> UtentiList;

    @FXML
    private AnchorPane classroomListPane;

    MainPageController mainPageController;
    List<UserDto> users;

    private long selectedUserId;

    public void setBPane(BorderPane BPane){
        this.BPaneListPage = BPane;
    }

    public void fillUtentiList() throws IOException{
        try {
            URL url = new URL("http://localhost:8080/api/v1/auth/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCode == HttpURLConnection.HTTP_OK) ? connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                users = objectMapper.readValue(response.toString(), new TypeReference<List<UserDto>>() {
                });
            }

        } catch (Exception e) {
            System.err.println("Errore durante il caricamento degli utenti: ");
        }

        if (users != null && !users.isEmpty()) {
            UtentiList.getItems().clear();
            for (UserDto user : users) {
                UtentiListInstanceView UtentiListInstanceView = new UtentiListInstanceView(mainPageController, user);
                UtentiList.getItems().add(UtentiListInstanceView);
            }
        }
    }

    public void init(MainPageController mainPageController) throws IOException {
        this.mainPageController = mainPageController;

        try {
            fillUtentiList();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento degli utenti: " + e.getMessage());
        }
    }
}




