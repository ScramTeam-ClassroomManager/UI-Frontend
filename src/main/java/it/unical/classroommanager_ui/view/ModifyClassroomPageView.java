package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.ModifyClassroomPageController;
import it.unical.classroommanager_ui.model.ClassroomDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ModifyClassroomPageView extends Pane {
    public ModifyClassroomPageView(MainPageController mainPageController, ClassroomDto classroomDto) {
        FXMLLoader loader = new FXMLLoader(ModifyClassroomPageView.class.getResource("modifyClassroomPage.fxml"));
        try {
            AnchorPane root = loader.load();
            ModifyClassroomPageController controller = loader.getController();

            controller.init(mainPageController, classroomDto);

            this.getChildren().add(root);

            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());

        } catch (Exception ignoredException) {

        }
    }
}
