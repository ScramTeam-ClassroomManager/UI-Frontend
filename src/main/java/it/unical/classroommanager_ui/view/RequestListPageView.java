package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.RequestListPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class RequestListPageView extends Pane {
    public RequestListPageView(MainPageController mainPageController) {
        FXMLLoader loader = new FXMLLoader(RequestListPageView.class.getResource("requestListPage.fxml"));
        try {
            AnchorPane root = loader.load();
            RequestListPageController controller = loader.getController();
            controller.init(mainPageController);
            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        } catch (Exception ignoredException) {
        }
    }
}
