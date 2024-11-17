package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.FontIconClass;
import it.unical.classroommanager_ui.view.imageSelector;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private MFXComboBox<String> endHourCB;

    @FXML
    private MFXComboBox<String> startHourCB;

    @FXML
    private Label dateAlert;

    @FXML
    private Label hourAlert;


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

            startHourCB.setStyle("-fx-border-color: green");

            hourAlert.setStyle("-fx-text-fill: green");
            hourAlert.setText("✓");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            hourAlert.setVisible(true);
        }

        // CHECK END HOUR
        // TODO: DEAL WITH TIME, STARTHOUR << ENDHOUR
        if(endHourCB.getValue() == null){
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
        else{
            endHour = true;

            endHourCB.setStyle("-fx-border-color: green");

            hourAlert.setStyle("-fx-text-fill: green");
            hourAlert.setText("✓");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            hourAlert.setVisible(true);
        }




        if (date && startHour && endHour){

            URL url = new URL("http://localhost:8080/api/v1/request/addRequest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);

            connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());


            // prepare input
            String formattedStartHour = startHourCB.getValue() + ":00";
            String formattedEndHour = endHourCB.getValue() + ":00";

            if (!(startHourCB.getValue().startsWith("1")))
                formattedStartHour = "0" + formattedStartHour;

            if (!(endHourCB.getValue().startsWith("1")))
                formattedEndHour = "0" + formattedEndHour;

            String jsonInputString = String.format("{\"reason\": \"%s\", \"classroomId\": \"%s\"," +
                            "\"startHour\": \"%s\", \"endHour\": \"%s\", \"requestDate\": \"%s\"}",
                    "", classroomDto.getId(), formattedStartHour, formattedEndHour, datePicker.getValue());

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
            }
        }

    }

    public void init(MainPageController mainPageController, ClassroomDto classroomDto){
        this.mainPageController = mainPageController;
        this.classroomDto = classroomDto;

        textName.setText(classroomDto.getName());
        cubeLabel.setText("("+ classroomDto.getCube() +")");
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

        // TODO: DYNAMIC SYSTEM NEEDS TO BE CREATED
        startHourCB.getItems().addAll("8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30",
                "14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00");
        endHourCB.getItems().addAll("8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30",
                "14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00");

        // TODO: DEAL WITH PAST DATES (SHOULD NOT BE AVAILABLE) NO DAYS OR MONTHS THAT HAVE PASSED (YEARS HAVE BEEN DEALT WITH)
        NumberRange<Integer> numberRange = new NumberRange<Integer>(2024,2025);
        datePicker.setYearsRange(numberRange);

        // TODO: DEAL WITH PAST DATES (SHOULD NOT BE AVAILABLE)
        // TODO: DYNAMIC SYSTEM TO CHOSE HOURS
        // TODO: BACKEND CALL
        // TODO: DEAL WITH TIME, STARTHOUR << ENDHOUR


    }

}