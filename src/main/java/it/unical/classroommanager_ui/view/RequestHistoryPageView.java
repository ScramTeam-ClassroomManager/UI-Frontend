package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.controller.RequestHistoryPageController;
import it.unical.classroommanager_ui.model.Role;
import it.unical.classroommanager_ui.model.UserManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class RequestHistoryPageView extends Pane {
    boolean isAdmin = UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString());

    public RequestHistoryPageView(MainPageController mainPageController, boolean isAdmin) {
        FXMLLoader loader = new FXMLLoader(RequestHistoryPageView.class.getResource("requestHistoryPage.fxml"));
        try {
            AnchorPane root = loader.load();
            RequestHistoryPageController controller = loader.getController();
            controller.init(mainPageController, isAdmin);
            this.getChildren().add(root);
            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
