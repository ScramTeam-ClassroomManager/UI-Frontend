package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.RequestHistoryPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class RequestHistoryPageView extends Pane {

    public RequestHistoryPageView(MainPageController mainPageController) {
        FXMLLoader loader = new FXMLLoader(RequestHistoryPageView.class.getResource("requestHistoryPage.fxml"));
        try {
            AnchorPane root = loader.load();
            RequestHistoryPageController controller = loader.getController();
            controller.init(mainPageController);
            this.getChildren().add(root);
            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
