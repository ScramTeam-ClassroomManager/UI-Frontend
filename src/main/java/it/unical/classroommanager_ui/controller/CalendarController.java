package it.unical.classroommanager_ui.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.classroommanager_ui.model.CustomEntryDetailsView;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.User;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.fxml.FXML;
import com.calendarfx.view.CalendarView;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    private void addEvent() {
        // Aggiungi un evento al calendario
        for (RequestDto request : requests) {
            try {
                Entry entry = new Entry();
                if (request.getStatus().equals("ACCEPTED")) {
                    entry.changeStartDate(request.getRequestDate());
                    entry.changeStartTime(request.getStartHour());
                    entry.changeEndDate(request.getRequestDate());
                    entry.changeEndTime(request.getEndHour());
                    entry.setLocation(getClassroomName(request.getClassroomId()));
                    entry.setTitle(getUserName(request.getUserSerialNumber()) + ": "
                            + request.getReason() + "\n"
                            + "Aula: " + entry.getLocation() + "\n");
                    calendarView.getCalendars().getFirst().addEntries(entry);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRequest(){
        try{
            requests = getAllRequests();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    public void init(MainPageController mainPageController) {
        // Configura il calendario (opzionale)
        this.mainPageController = mainPageController;

        calendarView.setEntryDetailsPopOverContentCallback(param -> new CustomEntryDetailsView(param.getEntry()));
        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(calendarSource);
        createRequest();
        addEvent();

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