package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.RequestHistoryInstanceView;
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

public class RequestHistoryPageController {

    @FXML
    private BorderPane BPaneListPage;

    @FXML
    private ListView<RequestHistoryInstanceView> historyList;

    @FXML
    private AnchorPane historyListPane;

    private MainPageController mainPageController;
    private List<RequestDto> requests;

    public void init(MainPageController mainPageController) throws IOException {
        this.mainPageController = mainPageController;
        fillRequestHistoryList();
    }

    public void setBPane(BorderPane BPane) {
        this.BPaneListPage = BPane;
    }

    public void fillRequestHistoryList() throws IOException {
        try {
            URL url = new URL("http://localhost:8080/api/v1/request/nonPendingRequests");
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
                System.err.println("Errore HTTP: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("Errore nel recupero delle richieste storiche.");
        }

        if (requests != null && !requests.isEmpty()) {
            for (RequestDto request : requests) {
                RequestHistoryInstanceView requestHistoryInstanceView = new RequestHistoryInstanceView(mainPageController, request);
                historyList.getItems().add(requestHistoryInstanceView);
            }

            historyListPane.setMinHeight(150 + historyList.getItems().size() * 137);
            historyList.setMinHeight(historyList.getItems().size() * 137);
        }
    }
}


