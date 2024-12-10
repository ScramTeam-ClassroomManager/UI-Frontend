package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.classroommanager_ui.model.CubeDto;
import it.unical.classroommanager_ui.view.CubeListInstanceView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CubeListPageController {

//    @FXML
//    private ListView<CubeListInstanceView> cubeList;
//
//    @FXML
//    private BorderPane BPaneListPage;
//
//    public void setBPane(BorderPane BPane) {
//        this.BPaneListPage = BPane;
//    }
//
//    @FXML
//    private AnchorPane cubeListPane;
//
//    private List<CubeDto> cubes;
//
//    public void fillCubeList(MainPageController mainPageController) throws IOException {
//        try {
//            URL url = new URL("http://localhost:8080/api/v1/cube/cubes");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept", "application/json");
//
//            int responseCode = connection.getResponseCode();
//            StringBuilder response = new StringBuilder();
//
//            try (BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(
//                            (responseCode == HttpURLConnection.HTTP_OK) ? connection.getInputStream() : connection.getErrorStream()))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//            }
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                cubes = objectMapper.readValue(response.toString(), new TypeReference<List<CubeDto>>() {});
//
//                cubes.sort(Comparator.comparingInt(CubeDto::getNumber));
//
//                cubeList.getItems().clear();
//                for (CubeDto cube : cubes) {
//                    CubeListInstanceView cubeListInstanceView = new CubeListInstanceView(mainPageController, cube);
//                    cubeList.getItems().add(cubeListInstanceView);
//                }
//            } else {
//                System.err.println("Failed : HTTP error code : " + responseCode);
//            }
//        } catch (Exception e) {
//            System.out.println("Problemi nella ricezione dei cubi.");
//        }
//    }
}
