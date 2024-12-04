package it.unical.classroommanager_ui.view;

import it.unical.classroommanager_ui.controller.CalendarController;
import it.unical.classroommanager_ui.controller.ClassroomDetailsPageController;
import it.unical.classroommanager_ui.controller.MainPageController;
import it.unical.classroommanager_ui.model.ClassroomDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CalendarPageView extends Pane {

    public CalendarPageView(MainPageController mainPageController) {
        FXMLLoader loader = new FXMLLoader(CalendarPageView.class.getResource("calendarPage.fxml"));
        try {
            VBox root = loader.load();
            CalendarController controller = loader.getController();

            controller.init(mainPageController);


            this.getChildren().add(root);
            root.prefWidthProperty().bind(this.widthProperty());
            root.prefHeightProperty().bind(this.heightProperty());

        } catch (Exception ignoredException) {

        }
    }
}
