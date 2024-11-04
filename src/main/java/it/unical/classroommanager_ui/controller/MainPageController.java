package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.view.ClassroomListPageView;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class MainPageController {

    @FXML
    private ScrollPane displaySection;




    public void displayClassrooms(){



        ClassroomListPageView classroomListPageView = new ClassroomListPageView(this);
        displaySection.setContent(classroomListPageView);

        displaySection.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        classroomListPageView.prefWidthProperty().bind(displaySection.widthProperty());
        classroomListPageView.minHeightProperty().bind(displaySection.heightProperty());


        // ritorna sulla cima della scrollbar
        displaySection.setVvalue(0);

    }



    @FXML
    public void initialize(){

        displayClassrooms();

    }
}
