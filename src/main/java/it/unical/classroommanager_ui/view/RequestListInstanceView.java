package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.RequestListInstanceController;
import it.unical.classroommanager_ui.model.RequestDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class RequestListInstanceView extends Pane {
    public RequestListInstanceView(MainPageController mainController, RequestDto request) {
        FXMLLoader loader = new FXMLLoader(RequestListInstanceView.class.getResource("requestListInstance.fxml"));
        try {
            AnchorPane root = loader.load();
            RequestListInstanceController controller = loader.getController();
            controller.init(mainController, request);
            this.getChildren().add(root);
        } catch (Exception ignoredException) {
        }
    }
}
