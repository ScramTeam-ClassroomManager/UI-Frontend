package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.ClassroomDetailsPageController;
import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.model.ClassroomDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class ClassroomDetailsPageView extends Pane {

    public ClassroomDetailsPageView(MainPageController mainPageController, ClassroomDto classroomDto) {
        FXMLLoader loader = new FXMLLoader(CreateClassroomPageView.class.getResource("classroomDetailsPage.fxml"));
        try {
            AnchorPane root = loader.load();
            ClassroomDetailsPageController controller = loader.getController();

            controller.init(mainPageController, classroomDto);

            this.getChildren().add(root);


            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());


        } catch (Exception ignoredException) {

        }
    }
}
