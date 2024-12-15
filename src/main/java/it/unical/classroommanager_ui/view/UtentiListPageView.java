package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.UtentiListPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UtentiListPageView extends Pane{
    public UtentiListPageView(MainPageController mainPageController){
        FXMLLoader loader = new FXMLLoader(UtentiListPageView.class.getResource("UtentiListPage.fxml"));
        try {
            AnchorPane root = loader.load();
            UtentiListPageController controller = loader.getController();
            controller.init(mainPageController);
            this.getChildren().add(root);
            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
