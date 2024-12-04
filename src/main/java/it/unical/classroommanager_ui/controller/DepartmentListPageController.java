package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.classroommanager_ui.model.DepartmentDto;
import it.unical.classroommanager_ui.view.DepartmentListInstanceView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DepartmentListPageController {

    @FXML
    private ListView<DepartmentListInstanceView> departmentsList;

    @FXML
    private BorderPane BPaneListPage;

    private MainPageController mainPageController;

    private List<DepartmentDto> departments;

    public void setBPane(BorderPane BPane) {
        this.BPaneListPage = BPane;
    }

    public void fillDepartmentList(MainPageController mainPageController) throws IOException {
        try {
            URL url = new URL("http://localhost:8080/api/v1/department/allDepartments");
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
                departments = objectMapper.readValue(response.toString(), new TypeReference<List<DepartmentDto>>() {});

                departmentsList.getItems().clear();
                for (DepartmentDto department : departments) {
                    DepartmentListInstanceView departmentListInstanceView = new DepartmentListInstanceView(mainPageController, department);
                    departmentsList.getItems().add(departmentListInstanceView);
                }
            } else {
                System.err.println("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Problemi nella ricezione dei dipartimenti.");
        }
    }

}
