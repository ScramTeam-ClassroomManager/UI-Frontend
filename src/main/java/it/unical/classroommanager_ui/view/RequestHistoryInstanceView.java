package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.RequestHistoryInstanceController;
import it.unical.classroommanager_ui.model.RequestDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RequestHistoryInstanceView extends Pane {

    public RequestHistoryInstanceView(MainPageController mainPageController, RequestDto request) {
        FXMLLoader loader = new FXMLLoader(RequestHistoryInstanceView.class.getResource("requestHistoryInstance.fxml"));
        try {
            AnchorPane root = loader.load();
            RequestHistoryInstanceController controller = loader.getController();
            controller.init(mainPageController, request);
            this.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore nel caricamento del file FXML: " + e.getMessage());
        }
    }
}
