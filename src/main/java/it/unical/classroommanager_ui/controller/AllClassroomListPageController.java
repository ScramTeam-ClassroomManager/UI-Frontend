package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.view.ClassroomListInstanceView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class AllClassroomListPageController {

    @FXML
    private BorderPane BPaneListPage;

    @FXML
    private ListView<ClassroomListInstanceView> allClassroomList;

    @FXML
    private AnchorPane allClassroomListPane;

    @FXML
    private MFXComboBox<Integer> filterCubeNumber;

    @FXML
    private MFXTextField filterCapability;

    @FXML
    private MFXTextField filterPlugs;

    @FXML
    private MFXComboBox<String> filterProjector;

    @FXML
    private MFXComboBox<String> filterType;

    @FXML
    private MFXButton applyFiltersButton;

    private MainPageController mainPageController;
    private List<ClassroomDto> classRooms;

    public void setBPane(BorderPane BPane){
        this.BPaneListPage = BPane;
    }

    public void init(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
        loadAllClassrooms();
        loadAllCubes();
    }

    private void loadAllClassrooms() {
        try {
            URL url = new URL("http://localhost:8080/api/v1/class/classrooms");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCode == HttpURLConnection.HTTP_OK) ? connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                classRooms = objectMapper.readValue(response.toString(), new TypeReference<List<ClassroomDto>>() {});
                updateClassroomList();
            } else {
                System.err.println("Errore durante il caricamento delle aule: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Errore durante il caricamento delle aule: " + e.getMessage());
        }
    }

    private void loadAllCubes() {
        try {
            URL url = new URL("http://localhost:8080/api/v1/cube/cubes");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCode == HttpURLConnection.HTTP_OK) ? connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Integer> cubes = objectMapper.readValue(response.toString(), new TypeReference<List<Object>>() {})
                        .stream()
                        .map(cube -> (Integer) ((java.util.Map<?, ?>) cube).get("number"))
                        .collect(Collectors.toList());
                filterCubeNumber.getItems().clear();
                filterCubeNumber.getItems().add(null);
                filterCubeNumber.getItems().addAll(cubes);
                filterCubeNumber.setPromptText("Cubo :");
            } else {
                System.err.println("Errore durante il caricamento dei cubi: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Errore durante il caricamento dei cubi: " + e.getMessage());
        }
    }

    @FXML
    private void applyFilters() {
        try {
            Integer cubeNumber = filterCubeNumber.getValue();
            String capability = filterCapability.getText().trim();
            String plugs = filterPlugs.getText().trim();
            String projector = filterProjector.getValue();
            String type = filterType.getValue();

            StringBuilder urlBuilder = new StringBuilder("http://localhost:8080/api/v1/class/AllclassroomsFiltered?");
            if (cubeNumber != null) urlBuilder.append("cubeNumber=").append(cubeNumber).append("&");
            if (!capability.isEmpty()) urlBuilder.append("capability=").append(capability).append("&");
            if (!plugs.isEmpty()) urlBuilder.append("plugs=").append(plugs).append("&");
            if (projector != null) urlBuilder.append("projector=").append(projector.equals("Con proiettore")).append("&");
            if (type != null) urlBuilder.append("type=").append(type).append("&");

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            (responseCode == HttpURLConnection.HTTP_OK) ? connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                classRooms = objectMapper.readValue(response.toString(), new TypeReference<List<ClassroomDto>>() {});
            } else {
                classRooms = null;
                System.err.println("Errore nella richiesta: " + responseCode);
            }

            updateClassroomList();

        } catch (Exception e) {
            System.out.println("Errore nell'applicazione dei filtri: " + e.getMessage());
        }
    }

    private void updateClassroomList() {
        allClassroomList.getItems().clear();
        if (classRooms != null && !classRooms.isEmpty()) {
            for (ClassroomDto classroom : classRooms) {
                ClassroomListInstanceView classroomListInstanceView = new ClassroomListInstanceView(mainPageController, classroom);
                allClassroomList.getItems().add(classroomListInstanceView);
            }
        } else {
            System.out.println("Nessuna aula trovata con i filtri specificati.");
        }
    }
}
