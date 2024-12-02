package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.CubeListInstanceController;
import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.model.CubeDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CubeListInstanceView extends Pane {

    public CubeListInstanceView(MainPageController mainPageController, CubeDto cube) {
        FXMLLoader loader = new FXMLLoader(CubeListInstanceView.class.getResource("cubeListInstance.fxml"));
        try {
            AnchorPane root = loader.load();
            CubeListInstanceController controller = loader.getController();
            controller.init(mainPageController, cube);
            this.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
