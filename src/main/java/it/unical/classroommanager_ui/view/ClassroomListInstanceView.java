package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.ClassroomListInstanceController;
import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.model.ClassroomDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ClassroomListInstanceView extends Pane {
    public ClassroomListInstanceView(MainPageController mainController, ClassroomDto classroom){
        FXMLLoader loader = new FXMLLoader(ClassroomListInstanceView.class.getResource("classroomListInstance.fxml"));
        try {


            AnchorPane root = loader.load();
            ClassroomListInstanceController controller = loader.getController();
            controller.init(mainController, classroom);
            this.getChildren().add(root);

        }

        catch(Exception ignoredException) {
        }

    }
}
