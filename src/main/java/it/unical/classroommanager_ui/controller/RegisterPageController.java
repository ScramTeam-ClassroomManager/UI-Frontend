package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import it.unical.classroommanager_ui.model.Role;
import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.SceneHandler;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class RegisterPageController {

    @FXML
    private Label emailErrorLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label loginAccessLabel;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private TextField nameField;

    @FXML
    private VBox passwordErrorLabel;

    @FXML
    private MFXPasswordField passwordField;

    @FXML
    private Label repeatPasswordErrorLabel;

    @FXML
    private MFXPasswordField repeatPasswordField;

    @FXML
    private Label surnameErrorLabel;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField serialnumberField;

    @FXML
    private Label serialnumberErrorLabel;

    @FXML
    private MFXComboBox<Role> roleComboBox;

    @FXML
    private Label roleErrorLabel;

    @FXML
    void registerAction(ActionEvent event) throws IOException {

        boolean name;
        boolean surname;
        boolean email;
        boolean serialNumber;
        boolean password;
        boolean repeatPassword;
        boolean role;

        boolean serialNOrEmailAlreadyInUse = false;

        // CHECK SUL NOME

        if (nameField.getText()==null|| nameField.getText().isEmpty()) {

            name = false;

            nameField.setStyle("-fx-border-color: red");

            nameErrorLabel.setText("Compila questo campo");
            nameErrorLabel.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), nameErrorLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            nameErrorLabel.setVisible(true);

        }

        else {

            // RISPETTA I CRITERI

            if (Pattern.matches("[a-zA-Z]{3,13}", nameField.getText())) {
                name = true;
                nameField.setStyle("-fx-border-color: green");

                nameErrorLabel.setVisible(false);
                nameErrorLabel.setText("✓");
                nameErrorLabel.setStyle("-fx-text-fill: green; -fx-alignment: center;");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), nameErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                nameErrorLabel.setVisible(true);

            }


            else {

                name = false;

                nameField.setText(null);
                nameField.setStyle("-fx-border-color: red");

                nameErrorLabel.setText("Nome non valido");
                nameErrorLabel.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), nameErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                nameErrorLabel.setVisible(true);


            }
        }




        // CHECK SUL COGNOME

        if (surnameField.getText()==null|| surnameField.getText().isEmpty()) {

            surname = false;
            surnameField.setStyle("-fx-border-color: red");

            surnameErrorLabel.setText("Compila questo campo");
            surnameErrorLabel.setStyle("-fx-text-fill: red");


            FadeTransition ft = new FadeTransition(Duration.seconds(1), surnameErrorLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            surnameErrorLabel.setVisible(true);

        }

        else {

            // RISPETTA I CRITERI

            if (Pattern.matches("[a-zA-Z]{3,13}", surnameField.getText())) {
                surname = true;
                surnameField.setStyle("-fx-border-color: green");

                surnameErrorLabel.setVisible(false);
                surnameErrorLabel.setText("✓");
                surnameErrorLabel.setStyle("-fx-text-fill: green; -fx-alignment: center;");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), surnameErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                surnameErrorLabel.setVisible(true);

            }


            else {

                surname = false;

                surnameField.setText(null);
                surnameField.setStyle("-fx-border-color: red");

                surnameErrorLabel.setText("Cognome non valido");
                surnameErrorLabel.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), surnameErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                surnameErrorLabel.setVisible(true);


            }
        }

        // CHECK EMAIL

        if (emailField.getText()==null|| emailField.getText().isEmpty()) {

            email = false;

            emailField.setStyle("-fx-border-color: red");
            emailErrorLabel.setText("Compila questo campo");
            emailErrorLabel.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), emailErrorLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            emailErrorLabel.setVisible(true);

        }

        else {

            // RISPETTA I CRITERI
            if (Pattern.matches("(\\S.*\\S)(@)(\\S.*\\S)(.\\S[a-z]{2,3})", emailField.getText())) {

                email = true;

                emailField.setStyle("-fx-border-color: green");

                emailErrorLabel.setVisible(false);
                emailErrorLabel.setText("✓");
                emailErrorLabel.setStyle("-fx-text-fill: green; -fx-alignment: center;");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), emailErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                emailErrorLabel.setVisible(true);

            }

            // NON RISPETTA I CRITERI

            else {

                email = false;

                emailField.setText(null);

                emailField.setStyle("-fx-border-color: red");
                emailErrorLabel.setText("Email non valida");
                emailErrorLabel.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), emailErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                emailErrorLabel.setVisible(true);


            }


        }

        // CHECK PASSWORD

        if (passwordField.getText()==null|| passwordField.getText().isEmpty()) {

            password = false;

            passwordField.setStyle("-fx-border-color: red");

            repeatPasswordField.setText(null);

            FadeTransition ft = new FadeTransition(Duration.seconds(1), passwordErrorLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            passwordErrorLabel.setVisible(true);

        }

        else {
            if (Pattern.matches("[a-zA-Z0-9]{10,20}", passwordField.getText())) {
                password = true;
                passwordField.setStyle("-fx-border-color: green");

                passwordErrorLabel.setVisible(false);

                FadeTransition ft = new FadeTransition(Duration.seconds(1), passwordErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                passwordErrorLabel.setVisible(true);


            }

            else {

                password = false;

                passwordField.setText(null);
                repeatPasswordField.setText(null);

                passwordField.setStyle("-fx-border-color: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), passwordErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                passwordErrorLabel.setVisible(true);

            }

        }


        // ----------------------------------------------------------------------------- //


        // CHECK CONFIRM PASSWORD

        if ( repeatPasswordField.getText()==null|| repeatPasswordField.getText().isEmpty()) {

            repeatPassword = false;


            repeatPasswordErrorLabel.setVisible(false);

            passwordField.setText(null);



            repeatPasswordField.setStyle("-fx-border-color: red");
            repeatPasswordErrorLabel.setText("Compila questo campo");
            repeatPasswordErrorLabel.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), repeatPasswordErrorLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            repeatPasswordErrorLabel.setVisible(true);

        }

        else{

            if ( !repeatPasswordField.getText().equals(passwordField.getText()) ) {

                repeatPassword = false;


                passwordField.setText(null);
                repeatPasswordField.setText(null);

                repeatPasswordErrorLabel.setVisible(false);

                passwordField.setStyle("-fx-border-color: red");
                repeatPasswordField.setStyle("-fx-border-color: red");
                repeatPasswordErrorLabel.setText("Le due password non sono uguali");
                repeatPasswordErrorLabel.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), repeatPasswordErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                repeatPasswordErrorLabel.setVisible(true);


            }

            else{

                repeatPassword = true;

                repeatPasswordField.setStyle("-fx-border-color: green");

                repeatPasswordErrorLabel.setVisible(false);
                repeatPasswordErrorLabel.setText("✓");
                repeatPasswordErrorLabel.setStyle("-fx-text-fill: green");

                passwordField.setStyle("-fx-border-color: green");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), repeatPasswordErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                repeatPasswordErrorLabel.setVisible(true);



            }


        }

        // CHECK SULLA MATRICOLA

        if (serialnumberField.getText()==null|| serialnumberField.getText().isEmpty()) {

            serialNumber = false;

            serialnumberField.setStyle("-fx-border-color: red");
            serialnumberErrorLabel.setText("Compila questo campo");
            serialnumberErrorLabel.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), serialnumberErrorLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            serialnumberErrorLabel.setVisible(true);

        }


        else {

            // RISPETTA I CRITERI

            if (Pattern.matches("[0-9]{6}", serialnumberField.getText())) {

                serialNumber = true;

                serialnumberField.setStyle("-fx-border-color: green");

                serialnumberErrorLabel.setVisible(false);
                serialnumberErrorLabel.setText("✓");
                serialnumberErrorLabel.setStyle("-fx-text-fill: green; -fx-alignment: center;");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), serialnumberErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                serialnumberErrorLabel.setVisible(true);

            }

            // NON RISPETTA I CRITERI

            else {

                serialNumber = false;

                serialnumberField.setText(null);

                serialnumberField.setStyle("-fx-border-color: red");
                serialnumberErrorLabel.setText("Matricola non valida");
                serialnumberErrorLabel.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), serialnumberErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                serialnumberErrorLabel.setVisible(true);


            }


        }

        //CHECK ROLE

        if (roleComboBox.getValue() == null) {

            role = false;


            roleErrorLabel.setVisible(false);

            roleComboBox.setStyle("-fx-border-color: red");
            roleErrorLabel.setText("Seleziona un ruolo");
            roleErrorLabel.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), roleErrorLabel);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            roleErrorLabel.setVisible(true);
        }

        else {
            role = true;
        }




        if (name && surname && email && password && repeatPassword && serialNumber && role){

            // start connection
            URL url = new URL("http://localhost:8080/api/v1/auth/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);

            // prepare input
            String jsonInputString = String.format("{\"serialNumber\": \"%s\", \"firstName\": \"%s\"," +
                            "\"lastName\": \"%s\", \"email\": \"%s\", \"password\": \"%s\", \"role\": \"%s\"}",
                    serialnumberField.getText(), nameField.getText(), surnameField.getText(), emailField.getText(), passwordField.getText(), roleComboBox.getValue().toString());


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            catch(Exception e){
                e.printStackTrace();
            }

            // get results
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCode == HttpURLConnection.HTTP_CREATED) ?
                                    connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            catch (Exception e) {
                // Handle any other exceptions that may occur
                serialNOrEmailAlreadyInUse = true;
                System.err.println("Problemi con la registrazione: " + e.getMessage());
            }

            if (serialNOrEmailAlreadyInUse || response.toString().contains("Matricola o Email")){

                email = false;

                // EMAIL GIA IN UTILIZZO
                emailField.setText(null);

                emailField.setStyle("-fx-border-color: red");
                emailErrorLabel.setText("L'email potrebbe essere già in uso");
                emailErrorLabel.setStyle("-fx-text-fill: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), emailErrorLabel);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();

                emailErrorLabel.setVisible(true);

                serialNumber = false;

                // MATRICOLA GIA IN UTILIZZO
                serialnumberField.setText(null);

                serialnumberField.setStyle("-fx-border-color: red");
                serialnumberErrorLabel.setText("La matricola potrebbe essere già in uso");
                serialnumberErrorLabel.setStyle("-fx-text-fill: red");

                FadeTransition ft1 = new FadeTransition(Duration.seconds(1), serialnumberErrorLabel);
                ft1.setFromValue(0.0);
                ft1.setToValue(1.0);
                ft1.play();

                serialnumberErrorLabel.setVisible(true);

            }
            if (!email && !serialNumber){
                return;
            }


            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                // Handle successful registration
                System.out.println("User registered successfully!");
            } else {
                // Handle errors
                System.out.println("Registration failed: " + responseCode);
            }



            // LO PORTA ALLA PAG PRINCIPALE E LO FA LOGGARE
            UserManager.getInstance().setUser(new User(serialnumberField.getText(), nameField.getText(), surnameField.getText(),
                    emailField.getText(), passwordField.getText(), roleComboBox.getValue().toString()));
            SceneHandler.getInstance().createMainPageScene();

        }

    }

    @FXML
    public void initialize(){

        roleComboBox.getItems().addAll(Role.values());

        loginAccessLabel.setOnMousePressed(event -> {
            try {
                SceneHandler.getInstance().createLoginScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



    }

}
