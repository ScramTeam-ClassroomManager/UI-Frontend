package it.unical.classroommanager_ui.controller;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.beans.property.Property;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomCalendarEventHandler implements EventHandler<CalendarEvent> {

    @Override
    public void handle(CalendarEvent calendarEvent) {

        if (calendarEvent.isEntryAdded()){
            Entry<RequestDto> entry = (Entry<RequestDto>) calendarEvent.getEntry();

            if(entry.getProperties().get("repetition").equals("Mai")){
                RequestDto requestDto = parseRequest(entry);
                postRequest(requestDto,entry);
            } else if (entry.getProperties().get("repetition").equals("Mensile")) {
                List<RequestDto> requests = new ArrayList<>();
                List<Entry<RequestDto>> entries = new ArrayList<>();
                LocalDate startDate = entry.getStartDate();
                LocalDate endDate = entry.getStartDate();
                endDate = startDate.plusMonths(1);
                while (startDate.isBefore(endDate)) {
                    RequestDto requestDto = parseRequest(entry);
                    requestDto.setRequestDate(startDate);
                    requests.add(requestDto);
                    Entry<RequestDto> newEntry = copyEntry(entry);
                    newEntry.setUserObject(requestDto);
                    newEntry.changeStartDate(startDate);
                    newEntry.changeEndDate(startDate);
                    entries.add(newEntry);
                    startDate = startDate.plusWeeks(1);
                }
                for (int i = 0; i < requests.size(); i++) {
                    postRequest(requests.get(i), entries.get(i));
                }
            } else if (entry.getProperties().get("repetition").equals("Semestrale")) {
                List<RequestDto> requests = new ArrayList<>();
                List<Entry<RequestDto>> entries = new ArrayList<>();
                LocalDate startDate = entry.getStartDate();
                LocalDate endDate = entry.getStartDate();
                endDate = startDate.plusMonths(3);

                while (startDate.isBefore(endDate)) {
                    RequestDto requestDto = parseRequest(entry);
                    requestDto.setRequestDate(startDate);
                    requests.add(requestDto);
                    Entry<RequestDto> newEntry = copyEntry(entry);
                    newEntry.setUserObject(requestDto);
                    newEntry.changeStartDate(startDate);
                    newEntry.changeEndDate(startDate);
                    entries.add(newEntry);
                    startDate = startDate.plusWeeks(1);
                }
                for (int i = 0; i < requests.size(); i++) {
                    postRequest(requests.get(i), entries.get(i));
                }

            }
        }

        if (calendarEvent.isEntryRemoved()){
            Entry<RequestDto> entry = (Entry<RequestDto>) calendarEvent.getEntry();

            try {
                URL url = new URL("http://localhost:8080/api/v1/request/deleteRequest/" + entry.getUserObject().getId());
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

    private RequestDto parseRequest(Entry<RequestDto> entry) {
        RequestDto requestDto = new RequestDto();
        ClassroomDto classroom = getClassroom(entry.getLocation());

        requestDto.setClassroomId(classroom.getId());
        requestDto.setReason(entry.getTitle());
        requestDto.setStartHour(entry.getStartTime());
        requestDto.setEndHour(entry.getEndTime());
        requestDto.setRequestDate(entry.getStartDate());

        return requestDto;
    }

    private ClassroomDto getClassroom(String name) {
        ClassroomDto classroom = new ClassroomDto();
        try {
            URL urlClassroom = new URL("http://localhost:8080/api/v1/class/classroomByName/" + name);

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
                return classroom;
            } else {
                System.err.println("Failed : HTTP error code : " + responseCodeClassroom);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classroom;
    }

    private Entry<RequestDto> copyEntry(Entry<RequestDto> entry) {
        Entry<RequestDto> newEntry = new Entry<>();
        newEntry.setTitle(entry.getTitle());
        newEntry.setLocation(entry.getLocation());
        newEntry.changeStartTime(entry.getStartTime());
        newEntry.changeEndTime(entry.getEndTime());
        newEntry.setFullDay(entry.isFullDay());

        return newEntry;
    }

    private void postRequest(RequestDto requestDto,Entry<RequestDto> entry){
        try {
            URL urlRequest = new URL("http://localhost:8080/api/v1/request/addRequest");
            HttpURLConnection connectionRequest = (HttpURLConnection) urlRequest.openConnection();
            connectionRequest.setRequestMethod("POST");
            connectionRequest.setRequestProperty("Content-Type","application/json");
            connectionRequest.setDoOutput(true);
            connectionRequest.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonInputString = objectMapper.writeValueAsString(requestDto);

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
                ObjectMapper newObjectMapper = new ObjectMapper();
                newObjectMapper.registerModule(new JavaTimeModule());
                RequestDto createdRequest = newObjectMapper.readValue(responseRequest.toString(), new TypeReference<RequestDto>() {});
                entry.setUserObject(createdRequest);
                System.out.println("Richiesta inserita nel sistema con successo.");
            }
            else {
                System.out.println("Insuccesso nell'inserimento della richiesta nel sistema.");
                entry.removeFromCalendar();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}




