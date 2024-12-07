package it.unical.classroommanager_ui.controller;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class CustomCalendarEventHandler implements EventHandler<CalendarEvent> {

    @Override
    public void handle(CalendarEvent calendarEvent) {

        if (calendarEvent.isEntryAdded()){
            Entry<RequestDto> entry = (Entry<RequestDto>) calendarEvent.getEntry();

            ClassroomDto classroom = new ClassroomDto();
            try {

                URL urlClassroom = new URL("http://localhost:8080/api/v1/class/classroomByName/" + entry.getLocation());

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
                    classroom = objectMapper.readValue(responseClassroom.toString(), new TypeReference<ClassroomDto>() {});
                } else {
                    System.err.println("Failed : HTTP error code : " + responseCodeClassroom);
                }


                URL urlRequest = new URL("http://localhost:8080/api/v1/request/addRequest");
                HttpURLConnection connectionRequest = (HttpURLConnection) urlRequest.openConnection();
                connectionRequest.setRequestMethod("POST");
                connectionRequest.setRequestProperty("Content-Type","application/json");
                connectionRequest.setDoOutput(true);

                connectionRequest.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());

                String jsonInputString = String.format("{\"reason\": \"%s\", \"classroomId\": \"%s\"," +
                                "\"startHour\": \"%s\", \"endHour\": \"%s\", \"requestDate\": \"%s\"}",
                        entry.getTitle(), classroom.getId(), entry.getStartTime(), entry.getEndTime(), entry.getStartDate());

                try (OutputStream os = connectionRequest.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                catch(Exception e){
                    throw new IOException();
                }

                int responseCodeRequest = connectionRequest.getResponseCode();
                StringBuilder responseRequest = new StringBuilder();
                try (BufferedReader reader1 = new BufferedReader(
                        new InputStreamReader(
                                (responseCodeRequest == HttpURLConnection.HTTP_CREATED) ? connectionRequest.getInputStream() : connectionRequest.getErrorStream()))) {
                    String line1;
                    while ((line1 = reader1.readLine()) != null) {
                        responseRequest.append(line1);
                    }
                }

                if (responseCodeRequest == HttpURLConnection.HTTP_CREATED) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    RequestDto createdRequest = objectMapper.readValue(responseRequest.toString(), new TypeReference<RequestDto>() {});
                    entry.setUserObject(createdRequest);
                    System.out.println("Richiesta inserita nel sistema con successo.");
                }
                else {
                    System.out.println("Insuccesso nell'inserimento della richiesta nel sistema.");
                    entry.removeFromCalendar();
                }


            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }

            entry.setTitle(UserManager.getInstance().getUser().firstName() + " " + UserManager.getInstance().getUser().lastName() + ": " + entry.getTitle() +
                    "\n" + "Aula: " + entry.getLocation());

        }

        if (calendarEvent.isEntryRemoved()){
            Entry entry = calendarEvent.getEntry();

            try {
                URL url = new URL("http://localhost:8080/api/v1/request/deleteRequest/" + entry.getId());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Richiesta eliminata con successo.");
                }
                else {
                    System.out.println("Insuccesso nell'eliminazione della richiesta.");
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}

//TODO: Implementare controlli lato frontend
