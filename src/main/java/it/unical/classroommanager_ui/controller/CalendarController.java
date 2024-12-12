package it.unical.classroommanager_ui.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.unical.classroommanager_ui.model.*;
import it.unical.classroommanager_ui.view.CustomEntryDetailsView;
import javafx.fxml.FXML;
import com.calendarfx.view.CalendarView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

public class CalendarController {

    @FXML
    private CalendarView calendarView;
    private Set<String> departments = new HashSet<>();
    private CalendarSource calendarSource = new CalendarSource("Request");
    private MainPageController mainPageController;

    @FXML
    private BorderPane BPaneListPage;

    private List<RequestDto> requests = new ArrayList<>();

    private void addAllEvent() {

        try{
            requests = getAllRequests();
            List <DepartmentDto> departmentList = new ArrayList<>();
            departmentList.addAll(getAllDepartments());
            int length = departmentList.size();
            int index = 1;
            for (DepartmentDto departmentDto : departmentList) {
                String department = departmentDto.getDepartment();
                if (!(departments.contains(department))) {
                    departments.add(department);
                    Calendar calendar = new Calendar(department);
                    calendar.setStyle(Calendar.Style.getStyle(index%length));
                    index++;
                    calendarSource.getCalendars().add(calendar);
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        for (RequestDto request : requests) {
            try {
                Entry<RequestDto> entry = new Entry<>();
                if (request.getStatus().equals("ACCEPTED")) {
                    entry.changeStartDate(request.getRequestDate());
                    entry.changeStartTime(request.getStartHour());
                    entry.changeEndDate(request.getRequestDate());
                    entry.changeEndTime(request.getEndHour());
                    entry.setLocation(getClassroomName(request.getClassroomId()));
                    entry.setTitle(getUserName(request.getUserSerialNumber()) + ": "
                            + request.getReason() + "\n"
                            + "Aula: " + entry.getLocation() + "\n");
                    entry.setUserObject(request);
                    for (Calendar c : calendarSource.getCalendars()) {
                        if (c.getName().equals(getDepartment(request.getClassroomId()))) {
                            entry.setCalendar(c);
                            c.addEntries(entry);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Entry createEntry(){
        Entry<RequestDto> entry = new Entry<>();

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Inserisci Nuova Richiesta");
        dialog.setHeaderText("Inserisci i dettagli dell'evento");

        MFXTextField titleField = new MFXTextField();
        titleField.setPromptText("Motivazione");
        titleField.setPrefWidth(160);

        MFXTextField locationField = new MFXTextField();
        locationField.setPromptText("Aula");
        locationField.setPrefWidth(160);

        MFXDatePicker datePicker = new MFXDatePicker();
        MFXComboBox<LocalTime> startHourCB = new MFXComboBox<>();
        MFXComboBox<LocalTime> endHourCB = new MFXComboBox<>();
        MFXComboBox<String> ripetizioni = new MFXComboBox<>();

        ripetizioni.getItems().add("Mai");
        ripetizioni.getItems().add("Mensile");
        ripetizioni.getItems().add("Semestrale");

        ripetizioni.getSelectionModel().selectFirst();

        for (int i = 8; i < 21; i++){
            startHourCB.getItems().add(LocalTime.of(i,0));
            startHourCB.getItems().add(LocalTime.of(i, 30));

            endHourCB.getItems().add(LocalTime.of(i,0));
            endHourCB.getItems().add(LocalTime.of(i, 30));
        }


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Motivazione:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Aula:"), 0, 1);
        grid.add(locationField, 1, 1);
        grid.add(new Label("Data:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Ora Inizio:"), 0, 3);
        grid.add(startHourCB, 1, 3);
        grid.add(new Label("Ora Fine:"), 0, 4);
        grid.add(endHourCB, 1, 4);

        grid.add(new Label("Ripeti per:"), 0, 5);
        grid.add(ripetizioni, 1, 5);

        dialog.getDialogPane().setContent(grid);


        ButtonType createButtonType = new ButtonType("Crea", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(titleField.getText(), locationField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            entry.setTitle(result.getKey());
            entry.setLocation(result.getValue());
            entry.changeStartDate(datePicker.getValue());
            entry.changeStartTime(startHourCB.getValue());
            entry.changeEndDate(datePicker.getValue());
            entry.changeEndTime(endHourCB.getValue());
            entry.getProperties().put("repetition", ripetizioni.getValue());
        });
        return entry;
    }

    private void showCustomEntryDetailsView(Entry<?> entry) {
        CustomEntryDetailsView detailsView = new CustomEntryDetailsView(entry);

        Stage detailsStage = new Stage();
        detailsStage.setTitle("Dettagli Evento");
        detailsStage.setScene(new Scene(detailsView));
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.show();
    }

    @FXML
    public void init(MainPageController mainPageController) {

        this.mainPageController = mainPageController;
        addAllEvent();
        for (Calendar calendar : calendarSource.getCalendars())
            calendar.addEventHandler(new CustomCalendarEventHandler());

        calendarView.setEntryDetailsPopOverContentCallback(param -> new CustomEntryDetailsView(param.getEntry()));

        calendarView.setContextMenuCallback(param -> {
            if (UserManager.getInstance().getToken().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Accesso richiesto");
                alert.setHeaderText("Azione non consentita");
                alert.setContentText("Devi effettuare il login per aggiungere una richiesta.");
                alert.showAndWait();

                return null;
            }

            ContextMenu contextMenu = new ContextMenu();
            MenuItem addEntry = new MenuItem("Aggiungi Richiesta");
            addEntry.setOnAction(e -> {

                String department;
                Entry<RequestDto> entry = createEntry();
                try {
                    Long classroomId = getClassroomId(entry.getLocation());
                    department = getDepartment(classroomId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                for (Calendar c : calendarSource.getCalendars()) {
                    if (c.getName().equals(department)) {
                        entry.setCalendar(c);
                        c.addEntry(entry);
                    }
                }
            });

            contextMenu.getItems().add(addEntry);
            return contextMenu;
        });

        calendarView.setEntryEditPolicy(context ->{
            if (context.getEditOperation() == CalendarView.EditOperation.MOVE)
                return false;
            return true;
        });

        calendarView.setEntryContextMenuCallback(param -> {
            Entry<RequestDto> entry = (Entry<RequestDto>) param.getEntry();

            ContextMenu contextMenu = new ContextMenu();

            MenuItem viewDetails = new MenuItem("Vedi dettagli");
            viewDetails.setOnAction(event -> {
                showCustomEntryDetailsView(entry);
            });
            contextMenu.getItems().add(viewDetails);

            if (!(UserManager.getInstance().getToken().isEmpty()) && UserManager.getInstance().getUser().role().equals("ADMIN")) {
                MenuItem deleteEntry = new MenuItem("Elimina richiesta");
                deleteEntry.setOnAction(event -> {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                            "Vuoi davvero eliminare questa richiesta?");
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            param.getCalendar().removeEntry(entry); // Rimuovi l'evento
                        }
                    });
                });
                contextMenu.getItems().add(deleteEntry);
            }

            return contextMenu;
        });

        calendarView.getCalendarSources().clear();
        calendarView.getCalendarSources().add(calendarSource);
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);

        calendarView.setStyle("-fx-background-color: #9b2030");
        calendarView.showDayPage();
    }

    public void setBPane(BorderPane BPane){
        this.BPaneListPage = BPane;
    }


    private String getClassroomName(long classroomId) throws IOException {
        String apiUrl = "http://localhost:8080/api/v1/class/getClassName/" + classroomId;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.readLine();
        }
    }

    private Long getClassroomId(String classroomName) throws IOException {
        ClassroomDto classroom = new ClassroomDto();
        try {
            URL urlClassroom = new URL("http://localhost:8080/api/v1/class/classroomByName/" + classroomName);

            HttpURLConnection connectionClassroom = (HttpURLConnection) urlClassroom.openConnection();
            connectionClassroom.setRequestMethod("GET");
            connectionClassroom.setRequestProperty("Accept", "application/json");

            int responseCodeClassroom = connectionClassroom.getResponseCode();
            StringBuilder responseClassroom = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCodeClassroom == HttpURLConnection.HTTP_OK) ? connectionClassroom.getInputStream() : connectionClassroom.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseClassroom.append(line);
                }
            }

            if (responseCodeClassroom == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                classroom = objectMapper.readValue(responseClassroom.toString(), new TypeReference<ClassroomDto>() {
                });
            } else {
                System.err.println("Failed : HTTP error code : " + responseCodeClassroom);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return classroom.getId();
    }


    private String getUserName(String userSerialNumber) throws IOException {
        String apiUrl = "http://localhost:8080/api/v1/auth/getFirstSecondUser/" + userSerialNumber;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(reader, User.class);
            return user.firstName() + " " + user.lastName();
        }
    }

    private String getDepartment(long classroomId) throws IOException {
        String apiUrl = "http://localhost:8080/api/v1/department/departmentByClassroom/" + classroomId;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.readLine();
        }
    }

    private List<DepartmentDto> getAllDepartments(){
        try {
            URL url = new URL("http://localhost:8080/api/v1/department/allDepartments");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCode == HttpURLConnection.HTTP_OK) ?
                                    connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.toString(), new TypeReference<List<DepartmentDto>>() {});
            } else {
                System.err.println("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Problema con la connessione al server");
        }
        return List.of();
    }


    private List<RequestDto> getAllRequests() throws IOException {
        try {
            URL url = new URL("http://localhost:8080/api/v1/request/requests");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCode == HttpURLConnection.HTTP_OK) ?
                                    connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                requests = objectMapper.readValue(response.toString(), new TypeReference<List<RequestDto>>() {});
                return requests;
            } else {
                System.err.println("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Problema con la connessione al server");
        }
        return List.of();
    }
}

