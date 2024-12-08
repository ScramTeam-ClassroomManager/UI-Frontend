package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.ClassroomDetailsRLInstanceController;
import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.model.RequestDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ClassroomDetailsRLInstanceView extends Pane {
    public ClassroomDetailsRLInstanceView(MainPageController mainPageController, RequestDto requestDto) {
        FXMLLoader loader = new FXMLLoader(ClassroomDetailsRLInstanceView.class.getResource("classroomDetailsRLInstance.fxml"));
        try {
            AnchorPane root = loader.load();
            ClassroomDetailsRLInstanceController controller = loader.getController();
            controller.init(mainPageController, requestDto);
            this.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
