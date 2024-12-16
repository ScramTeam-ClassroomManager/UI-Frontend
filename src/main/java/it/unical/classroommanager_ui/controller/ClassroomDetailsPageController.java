package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.palexdev.materialfx.beans.NumberRange;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.Role;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.ClassroomDetailsRLInstanceView;
import it.unical.classroommanager_ui.view.FontIconClass;
import it.unical.classroommanager_ui.view.imageSelector;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @FXML
    private TextArea reasonTextArea;

    @FXML
    private ListView<ClassroomDetailsRLInstanceView> requestListView;

    @FXML
    private ImageView backArrow;

    @FXML
    private MFXComboBox<String> repetitionComboBox;

    MainPageController mainPageController;
    ClassroomDto classroomDto;

    @FXML
    void bookClass(ActionEvent event) throws IOException {

        boolean date;
        boolean startHour;
        boolean endHour;

        // CHECK DATE
        if (datePicker.getValue() == null || datePicker.getValue().isBefore(LocalDate.now())) {
            date = false;
            datePicker.setStyle("-fx-border-color: red");
            dateAlert.setStyle("-fx-text-fill: red");
            dateAlert.setText("Se vuoi prenotare l'aula devi inserire una data successiva ad oggi.");
            FadeTransition ft = new FadeTransition(Duration.seconds(1), dateAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            dateAlert.setVisible(true);
        } else {
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
        if (startHourCB.getValue() == null) {
            startHour = false;
            startHourCB.setStyle("-fx-border-color: red");
            hourAlert.setStyle("-fx-text-fill: red");
            hourAlert.setText("Se vuoi prenotare l'aula devi inserire un'ora di inizio e di fine.");
            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            hourAlert.setVisible(true);
        } else {
            startHour = true;
        }

        // CHECK END HOUR
        if (endHourCB.getValue() == null || endHourCB.getValue().isBefore(startHourCB.getValue()) ||
                endHourCB.getValue().equals(startHourCB.getValue())) {
            endHour = false;
            endHourCB.setStyle("-fx-border-color: red");
            startHourCB.setStyle("-fx-border-color: red");
            hourAlert.setStyle("-fx-text-fill: red");
            hourAlert.setText("L'ora finale deve essere successiva a quella di inizio.");
            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            hourAlert.setVisible(true);
        } else {
            endHour = true;
            endHourCB.setStyle("-fx-border-color: green");
            hourAlert.setStyle("-fx-text-fill: green");
            hourAlert.setText("✓");
            startHourCB.setStyle("-fx-border-color: green");
            FadeTransition ft = new FadeTransition(Duration.seconds(1), hourAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            hourAlert.setVisible(true);
        }

        if (date && startHour && endHour) {
            String repetitionType = repetitionComboBox.getValue();
            LocalDate requestDate = datePicker.getValue();

            int repeatCount = 0;
            int maxRepeats = 1;
            int weeksIncrement = 0;

            if ("Mensile".equals(repetitionType)) {
                maxRepeats = 4;
                weeksIncrement = 1;
            } else if ("Semestrale".equals(repetitionType)) {
                maxRepeats = 12;
                weeksIncrement = 1;
            }

            while (repeatCount < maxRepeats) {
                String jsonInputString = String.format(
                        "{\"reason\": \"%s\", \"classroomId\": \"%s\", \"startHour\": \"%s\", \"endHour\": \"%s\", \"requestDate\": \"%s\"}",
                        reasonTextArea.getText(), classroomDto.getId(), startHourCB.getValue(), endHourCB.getValue(), requestDate
                );

                int responseCode = postRequest(jsonInputString);

                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    System.out.println("Richiesta inserita con successo: " + requestDate);
                } else {
                    System.out.println("Errore nell'inserimento della richiesta: " + requestDate);
                    break;
                }

                requestDate = requestDate.plusWeeks(weeksIncrement);
                repeatCount++;
            }
        }
    }

    private int postRequest(String jsonInputString) {
        try {

            URL url = new URL("http://localhost:8080/api/v1/request/addRequest");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (Exception e) {
                throw new IOException();
            }

             return connection.getResponseCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifyClassroom(ActionEvent event) {
        mainPageController.displayModifyClassroomPage(classroomDto);
    }

    public void init(MainPageController mainPageController, ClassroomDto classroomDto) throws IOException {
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

        repetitionComboBox.getItems().add("Mai");
        repetitionComboBox.getItems().add("Mensile");
        repetitionComboBox.getItems().add("Semestrale");




        // CALLING BACKEND TO GET ALL ACCEPTED CLASSROOM REQUESTS
        URL url = new URL("http://localhost:8080/api/v1/request/acceptedRequestsOfClassroom/"+classroomDto.getId());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                List<RequestDto> requests = objectMapper.readValue(response.toString(), new TypeReference<List<RequestDto>>(){});

                // ORDER THE LIST IN ORDER OF WHAT COMES FIRST IN TIMELINE
                sortRequestsByDateTime(requests);
                if (!requests.isEmpty()) {
                    for (RequestDto request : requests) {
                        ClassroomDetailsRLInstanceView classroomDetailsRLInstanceView = new ClassroomDetailsRLInstanceView(mainPageController, request);
                        requestListView.getItems().add(classroomDetailsRLInstanceView);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error reading the response: " + e.getMessage());
            }

        } else {
            System.err.println("Richiesta prenotazioni classe fallita, codice risposta:" + responseCode);
        }





    }
    public static void sortRequestsByDateTime(List<RequestDto> requests) {
        requests.sort((r1, r2) -> {
            // First, compare by requestDate
            int dateComparison = r1.getRequestDate().compareTo(r2.getRequestDate());
            if (dateComparison != 0) {
                return dateComparison;
            }

            // If requestDate is the same, compare by startHour
            int startHourComparison = r1.getStartHour().compareTo(r2.getStartHour());
            if (startHourComparison != 0) {
                return startHourComparison;
            }

            // If both requestDate and startHour are the same, compare by endHour
            return r1.getEndHour().compareTo(r2.getEndHour());
        });
    }

    @FXML
    void PressBack(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unical/classroommanager_ui/view/departmentListPage.fxml"));
            AnchorPane departmentPage = loader.load();

            DepartmentListPageController departmentListPageController = loader.getController();
            departmentListPageController.setMainPageController(mainPageController);
            departmentListPageController.fillDepartmentList(mainPageController);

            mainPageController.getBPane().setCenter(departmentPage);
        } catch (IOException e) {
            System.err.println("Errore durante il ritorno alla pagina dei dipartimenti: " + e.getMessage());
        }
    }

}