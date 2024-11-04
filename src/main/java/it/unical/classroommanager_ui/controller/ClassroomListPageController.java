package it.unical.classroommanager_ui.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.view.ClassroomListInstanceView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class ClassroomListPageController {

    @FXML
    private ListView<ClassroomListInstanceView> classroomList;

    @FXML
    private AnchorPane classroomListPane;

    MainPageController mainPageController;

    List<ClassroomDto> classRooms;


    public void fillClassroomList() throws IOException {
        try {
            URL url = new URL("http://localhost:8080/api/v1/class/classrooms");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // LETTURA RISPOSTA
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
                classRooms = objectMapper.readValue(response.toString(), new TypeReference<List<ClassroomDto>>() {});
            }
            else {
                System.err.println("Failed : HTTP error code : " + responseCode);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problemi nella ricezione delle classi.");
        }

        if (!classRooms.isEmpty()) {

            for (ClassroomDto classroom : classRooms) {


                ClassroomListInstanceView classroomListInstanceView = new ClassroomListInstanceView(mainPageController, classroom);

                classroomList.getItems().add(classroomListInstanceView);


            }

            classroomListPane.setMinHeight(150+classroomList.getItems().size() * 137);
            classroomList.setMinHeight(classroomList.getItems().size() * 137);

        }


    }


    public void init(MainPageController mainPageController) throws IOException {
        this.mainPageController = mainPageController;

        fillClassroomList();
    }

}
