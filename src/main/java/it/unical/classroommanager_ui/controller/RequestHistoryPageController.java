package it.unical.classroommanager_ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.RequestHistoryInstanceView;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

    @FXML
    private MFXComboBox<String> statusFilter;

    private MainPageController mainPageController;
    private List<RequestDto> requests;

    public void init(MainPageController mainPageController, boolean isAdmin) throws IOException {
        this.mainPageController = mainPageController;
        if (isAdmin) {
            fillRequestHistoryList("http://localhost:8080/api/v1/request/nonPendingRequests");
            statusFilter.getItems().addAll("ALL", "ACCEPTED", "REJECTED");
        } else {
            fillRequestHistoryList("http://localhost:8080/api/v1/request/userRequests");
            statusFilter.getItems().addAll("ALL", "ACCEPTED", "PENDING", "REJECTED");
        }
    }

    public void setBPane(BorderPane BPane) {
        this.BPaneListPage = BPane;
    }

    private void fillRequestHistoryList(String apiUrl) throws IOException {
        try {
            URL url = new URL(apiUrl);
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
        }
    }

    public void filterRequestsByStatus(String status) throws IOException {
        historyList.getItems().clear();

        if (requests != null && !requests.isEmpty()) {
            for (RequestDto request : requests) {
                if ("ALL".equalsIgnoreCase(status) || request.getStatus().equalsIgnoreCase(status)) {
                    RequestHistoryInstanceView requestHistoryInstanceView = new RequestHistoryInstanceView(mainPageController, request);
                    historyList.getItems().add(requestHistoryInstanceView);
                }
            }
        }
    }
    @FXML
    private void onFilterChanged() {
        String selectedStatus = statusFilter.getSelectionModel().getSelectedItem();
        if (selectedStatus != null) {
            try {
                filterRequestsByStatus(selectedStatus);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}



