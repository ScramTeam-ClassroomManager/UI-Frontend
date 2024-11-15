package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.CreateClassroomPageController;
import it.unical.classroommanager_ui.controller.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CreateClassroomPageView extends Pane {

    public CreateClassroomPageView(MainPageController mainPageController) {
        FXMLLoader loader = new FXMLLoader(CreateClassroomPageView.class.getResource("newClassroomPage.fxml"));
        try {
            AnchorPane root = loader.load();
            CreateClassroomPageController controller = loader.getController();

            controller.init(mainPageController);


            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());

        } catch (Exception ignoredException) {

        }
    }
}
