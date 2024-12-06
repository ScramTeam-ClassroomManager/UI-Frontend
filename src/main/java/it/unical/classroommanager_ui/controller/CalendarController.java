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
import javafx.fxml.FXML;
import com.calendarfx.view.CalendarView;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarController {

    @FXML
    private CalendarView calendarView;

    private Calendar calendar = new Calendar("Requests");
    private CalendarSource calendarSource = new CalendarSource("Calendario");
    private MainPageController mainPageController;

    @FXML
    private BorderPane BPaneListPage;

    private List<RequestDto> requests = new ArrayList<>();

    private void addAllEvent() {

        try{
            requests = getAllRequests();
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
                    calendar.addEntries(entry);
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
        });

        return entry;
    }


    @FXML
    public void init(MainPageController mainPageController) {

        this.mainPageController = mainPageController;
        addAllEvent();
        calendar.addEventHandler(new CustomCalendarEventHandler());
        if (UserManager.getInstance().getToken().isEmpty() || !(UserManager.getInstance().getUser().role().equals("ADMIN"))) {
            calendar.setReadOnly(true);
        }
        calendarView.setEntryDetailsPopOverContentCallback(param -> new CustomEntryDetailsView(param.getEntry()));

        calendarView.setContextMenuCallback(param -> {
            if (UserManager.getInstance().getToken().isEmpty())
                return new ContextMenu();

            ContextMenu contextMenu = new ContextMenu();
            MenuItem addEntry = new MenuItem("Aggiungi Richiesta");
            addEntry.setOnAction(e -> {

                Entry<RequestDto> entry = createEntry();
                entry.setCalendar(calendar);
                calendar.addEntry(entry);
            });

            contextMenu.getItems().add(addEntry);
            return contextMenu;
        });

        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(calendarSource);

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


//TODO: REMINDER: MODIFICARE PERMESSI BACKEND PER USARE ENDPOINT ALLREQUESTS
//TODO: NON PERMETTERE L'ELIMINAZIONE DI RICHIESTE SE NON SI E' ADMIN FACENDO TASTO DESTRO