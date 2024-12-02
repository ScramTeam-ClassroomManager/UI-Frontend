package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.Role;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.FontIconClass;
import it.unical.classroommanager_ui.view.imageSelector;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClassroomDetailsPageController {

    @FXML
    private Label capabilityLabel;

    @FXML
    private ImageView classroomImage;

    @FXML
    private Label cubeLabel;

    @FXML
    private Label floorLabel;

    @FXML
    private Label graphicCapability;

    @FXML
    private Label graphicCube;

    @FXML
    private Label graphicFloor;

    @FXML
    private Label graphicProjector;

    @FXML
    private Label graphicSocket;

    @FXML
    private Label projectorLabel;

    @FXML
    private Label socketLabel;

    @FXML
    private Text textName;

    @FXML
    private Label typeLabel;

    @FXML
    private Label graphicType;

    @FXML
    private MFXDatePicker datePicker;

    @FXML
    private MFXComboBox<LocalTime> endHourCB;

    @FXML
    private MFXComboBox<LocalTime> startHourCB;

    @FXML
    private Label dateAlert;

    @FXML
    private Label hourAlert;

    @FXML
    private Label bookAlert;
    @FXML
    private MFXButton bookButton;

    @FXML
    private Button modifyButton;

    MainPageController mainPageController;
    ClassroomDto classroomDto;

    @FXML
    void bookClass(ActionEvent event) throws IOException {

        boolean date;
        boolean startHour;
        boolean endHour;


        // CHECK DATE
        if(datePicker.getValue() == null || datePicker.getValue().isBefore(LocalDate.now())){
            date = false;

            datePicker.setStyle("-fx-border-color: red");

            dateAlert.setStyle("-fx-text-fill: red");
            dateAlert.setText("Se vuoi prenotare l'aula devi inserire una data successiva ad oggi.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), dateAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            dateAlert.setVisible(true);
        }
        else{
            date = true;

            datePicker.setStyle("-fx-border-color: green");

            dateAlert.setStyle("-fx-text-fill: green");
            dateAlert.setText("✓");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), dateAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            dateAlert.setVisible(true);
        }

        // CHECK START HOUR
        if(startHourCB.getValue() == null){
            startHour = false;

            startHourCB.setStyle("-fx-border-color: red");

            hourAlert.setStyle("-fx-text-fill: red");
            hourAlert.setText("Se vuoi prenotare l'aula devi inserire un ora di inizio e di fine.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            hourAlert.setVisible(true);
        }
        else{
            startHour = true;
        }

        // CHECK END HOUR
        if(endHourCB.getValue() == null ){

            endHour = false;

            endHourCB.setStyle("-fx-border-color: red");

            hourAlert.setStyle("-fx-text-fill: red");
            hourAlert.setText("Se vuoi prenotare l'aula devi inserire un ora di inizio e di fine.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            hourAlert.setVisible(true);
        }
        else if(endHourCB.getValue().isBefore(startHourCB.getValue()) ||
                endHourCB.getValue().equals(startHourCB.getValue())){
            endHour = false;

            endHourCB.setStyle("-fx-border-color: red");
            startHourCB.setStyle("-fx-border-color: red");

            hourAlert.setStyle("-fx-text-fill: red");
            hourAlert.setText("L'ora finale deve essere succesiva a quella di inizio.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            hourAlert.setVisible(true);
        }
        else{
            endHour = true;

            endHourCB.setStyle("-fx-border-color: green");

            hourAlert.setStyle("-fx-text-fill: green");
            hourAlert.setText("✓");

            startHourCB.setStyle("-fx-border-color: green");

            hourAlert.setStyle("-fx-text-fill: green");
            hourAlert.setText("✓");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            hourAlert.setVisible(true);

            hourAlert.setVisible(true);
        }




        if (date && startHour && endHour){

            URL url = new URL("http://localhost:8080/api/v1/request/addRequest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);

            connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());


            // Prepare input
            String jsonInputString = String.format("{\"reason\": \"%s\", \"classroomId\": \"%s\"," +
                            "\"startHour\": \"%s\", \"endHour\": \"%s\", \"requestDate\": \"%s\"}",
                    "", classroomDto.getId(), startHourCB.getValue(), endHourCB.getValue(), datePicker.getValue());

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            catch(Exception e){
                throw new IOException();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Richiesta inserita nel sistema con successo.");
            }
            else{
                System.out.println("Insuccesso nell'inserimento della richiesta nel sistema.");

                startHourCB.setStyle("-fx-border-color: red");
                endHourCB.setStyle("-fx-border-color: red");
                hourAlert.setVisible(false);

                datePicker.setStyle("-fx-border-color: red");
                dateAlert.setVisible(false);

                bookAlert.setStyle("-fx-text-fill: red");
                bookButton.setStyle("-fx-border-color: red");

                FadeTransition ft = new FadeTransition(Duration.seconds(1), bookAlert);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
                bookAlert.setVisible(true);
            }
        }

    }

    @FXML
    void modifyClassroom(ActionEvent event) {
        mainPageController.displayModifyClassroomPage(classroomDto);
    }

    public void init(MainPageController mainPageController, ClassroomDto classroomDto){
        this.mainPageController = mainPageController;
        this.classroomDto = classroomDto;

        modifyButton.setGraphic((new FontIconClass("mdi2p-pencil-box",25)));
        textName.setFocusTraversable(true);

        if(!UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString())) {
            modifyButton.setDisable(true);
        }


        textName.setText(classroomDto.getName());
        cubeLabel.setText("(" + classroomDto.getCubeNumber() + ")");
        floorLabel.setText("("+ classroomDto.getFloor() +")");
        capabilityLabel.setText("("+ classroomDto.getCapability() +")");
        socketLabel.setText("("+classroomDto.getNumSocket()+")");

        if(classroomDto.isProjector()){
            projectorLabel.setText("È presente un proiettore all'interno dell'aula.");
        }
        else{
            projectorLabel.setText("Non è presente alcun proiettore all'interno dell'aula.");
        }

        if(classroomDto.getType().equals("NORMAL")){
            typeLabel.setText("Aula normale");
        }
        else{
            typeLabel.setText("Auditorium");
        }


        graphicCube.setGraphic((new FontIconClass("mdi2c-cube", 20)));
        graphicCapability.setGraphic((new FontIconClass("mdi2a-account-group", 20)));
        graphicFloor.setGraphic((new FontIconClass("mdi2s-stairs-box", 20)));
        graphicSocket.setGraphic((new FontIconClass("mdi2p-power-socket-it", 20)));
        graphicProjector.setGraphic((new FontIconClass("mdi2p-projector", 20)));
        graphicType.setGraphic(new FontIconClass("mdi2a-alert-circle-outline",20));

        classroomImage.setImage(imageSelector.classroomImage(classroomDto.getName()));

        for (int i = 8; i < 21; i++){
            startHourCB.getItems().add(LocalTime.of(i,0));
            startHourCB.getItems().add(LocalTime.of(i, 30));

            endHourCB.getItems().add(LocalTime.of(i,0));
            endHourCB.getItems().add(LocalTime.of(i, 30));
        }
        NumberRange<Integer> numberRange = new NumberRange<Integer>(2024,2025);
        datePicker.setYearsRange(numberRange);
    }

}