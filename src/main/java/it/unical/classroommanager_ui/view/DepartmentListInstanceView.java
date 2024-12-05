package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.DepartmentListInstanceController;
import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.model.DepartmentDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class DepartmentListInstanceView extends AnchorPane {

    public DepartmentListInstanceView(MainPageController mainPageController, DepartmentDto department) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("departmentListInstance.fxml"));
        try {
            AnchorPane root = loader.load();
            DepartmentListInstanceController controller = loader.getController();
            controller.init(mainPageController, department);
            this.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
