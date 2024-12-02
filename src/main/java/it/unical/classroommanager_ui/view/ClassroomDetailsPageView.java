package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.ClassroomDetailsPageController;
import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.model.ClassroomDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;


public class ClassroomDetailsPageView extends VBox {

    public ClassroomDetailsPageView(MainPageController mainPageController, ClassroomDto classroomDto) {
        FXMLLoader loader = new FXMLLoader(CreateClassroomPageView.class.getResource("classroomDetailsPage.fxml"));
        try {
            VBox root = loader.load();
            ClassroomDetailsPageController controller = loader.getController();

            controller.init(mainPageController, classroomDto);


            this.getChildren().add(root);

        } catch (Exception ignoredException) {

        }
    }
}
