package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.RequestListInstanceView;
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

public class RequestListPageController {

    @FXML
    private BorderPane BPaneListPage;

    public void setBPane(BorderPane BPane){
        this.BPaneListPage = BPane;
    }

    @FXML
    private ListView<RequestListInstanceView> requestList;

    @FXML
    private AnchorPane requestListPane;

    @FXML
    private RequestListPageController requestListPageController;


    private MainPageController mainPageController;
    private List<RequestDto> requests;

    public void fillRequestList() throws IOException {
        try {
            URL url = new URL("http://localhost:8080/api/v1/request/pendingRequests");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            String token = UserManager.getInstance().getToken();
            connection.setRequestProperty("Authorization", "Bearer " + token);

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
            } else {
                System.err.println("Failed : HTTP error code : " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Problemi nella ricezione delle richieste.");
        }

        if (requests != null && !requests.isEmpty()) {
            for (RequestDto request : requests) {
                RequestListInstanceView requestListInstanceView = new RequestListInstanceView(mainPageController, request);
                requestList.getItems().add(requestListInstanceView);
            }

            requestListPane.setMinHeight(150 + requestList.getItems().size() * 137);
            requestList.setMinHeight(requestList.getItems().size() * 137);
        }
    }


    public void init(MainPageController mainPageController) throws IOException {
        this.mainPageController = mainPageController;
        fillRequestList();
    }
}

