package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.ClassroomListPageController;
import it.unical.classroommanager_ui.controller.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ClassroomListPageView extends Pane {
//    public ClassroomListPageView(MainPageController mainPageController) {
//        FXMLLoader loader = new FXMLLoader(ClassroomListPageView.class.getResource("classroomListPage.fxml"));
//        try {
//            AnchorPane root = loader.load();
//            ClassroomListPageController controller = loader.getController();
//            controller.init(mainPageController);
//            this.getChildren().add(root);
//            root.prefWidthProperty().bind(this.widthProperty());
//            root.prefHeightProperty().bind(this.heightProperty());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public ClassroomListPageView(MainPageController mainPageController, long departmentId) {
        FXMLLoader loader = new FXMLLoader(ClassroomListPageView.class.getResource("classroomListPage.fxml"));
        try {
            AnchorPane root = loader.load();
            ClassroomListPageController controller = loader.getController();
            controller.init(mainPageController, departmentId); // Passa anche l'ID del dipartimento
            this.getChildren().add(root);
            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
