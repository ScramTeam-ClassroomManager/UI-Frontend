package it.unical.classroommanager_ui.view;


import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.UtentiListInstanceController;
import it.unical.classroommanager_ui.model.UserDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UtentiListInstanceView extends Pane {
    public UtentiListInstanceView(MainPageController mainPageController, UserDto user){
        FXMLLoader loader = new FXMLLoader(UtentiListInstanceView.class.getResource("UtentiListInstance.fxml"));
            try {
            AnchorPane root = loader.load();
            UtentiListInstanceController controller = loader.getController();
            controller.init(mainPageController, user);
            this.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
