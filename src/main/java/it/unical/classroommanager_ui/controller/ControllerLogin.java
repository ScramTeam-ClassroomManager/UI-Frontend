package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.model.TokenDecoder;
import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.SceneHandler;
import jakarta.validation.constraints.Pattern;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.palexdev.materialfx.controls.MFXPasswordField;

import java.io.IOException;
import java.util.Map;



public class ControllerLogin {


    @FXML
    private Label Label_errorematr;

    @FXML
    private Label Label_errorepass;

    @FXML
    private Label Label_sbagliati;

    @FXML
    private MFXPasswordField Passwordfield_password;

    @FXML
    private TextField Textfield_matricola;

    @Pattern(regexp = "\\d{10}")
    private String serialNumber;

    @Pattern(regexp = "\\d{8}")
    private String password;

    private static final String LOGIN_URL = "http://localhost:8080/api/v1/auth/login";

    @FXML
    void iscrizione(MouseEvent event) {
        try {
            SceneHandler.getInstance().createRegisterScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void login(ActionEvent event) {
        String serialNumber = Textfield_matricola.getText();
        String password = Passwordfield_password.getText();

        if (serialNumber.isEmpty()){
            Label_errorematr.setVisible(true);
        }

        if (password.isEmpty()){
            Label_errorepass.setVisible(true);
        }

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                authenticateUser(serialNumber, password);
                return null;
            }
        };

        new Thread(task).start();
    }

    private void authenticateUser(String serialNumber, String password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = Map.of(
                    "serialNumber", serialNumber,
                    "password", password
            );


            String jsonRequestBody = new ObjectMapper().writeValueAsString(requestBody);

            HttpEntity<String> request = new HttpEntity<>(jsonRequestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {

                String token = (String) response.getBody().get("token");

                TokenDecoder tokenDecoder = new TokenDecoder(token);

                UserManager.getInstance().setUser(new User(tokenDecoder.serialNumber(), tokenDecoder.name(), tokenDecoder.surname(),
                        tokenDecoder.email(), "NIENTE", tokenDecoder.role()));
                UserManager.getInstance().setToken(token);

                javafx.application.Platform.runLater(() -> {
                    try {
                        SceneHandler.getInstance().createMainPageScene();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            javafx.application.Platform.runLater(() -> {
                Label_sbagliati.setVisible(true);
            });
        }
    }

}