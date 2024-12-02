package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.ProfilePageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ProfilePageView extends Pane {
    public ProfilePageView(MainPageController mainPageController) {
        FXMLLoader loader = new FXMLLoader(ProfilePageView.class.getResource("profilePage.fxml"));
        try {
            AnchorPane root = loader.load();
            ProfilePageController controller = loader.getController();

            controller.init(mainPageController);


            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());

        } catch (Exception ignoredException) {

        }
    }
}
