package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.view.ClassroomListPageView;
import it.unical.classroommanager_ui.view.CreateClassroomPageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class MainPageController {

    @FXML
    private ScrollPane displaySection;

    @FXML
    private BorderPane mainPageBorderPane;

    boolean currentlyShowingClassrooms = false;
    boolean currentlyCreatingNewClassroom = false;




    public void displayClassrooms(){





        ClassroomListPageView classroomListPageView = new ClassroomListPageView(this);
        displaySection.setContent(classroomListPageView);

        displaySection.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        classroomListPageView.prefWidthProperty().bind(displaySection.widthProperty());
        classroomListPageView.minHeightProperty().bind(displaySection.heightProperty());


        // ritorna sulla cima della scrollbar
        displaySection.setVvalue(0);
        this.currentlyShowingClassrooms = true;
        this.currentlyCreatingNewClassroom = false;

    }

    @FXML
    void newClassroom(ActionEvent event) {
        if (!currentlyCreatingNewClassroom) {

            CreateClassroomPageView createClassroomPageView = new CreateClassroomPageView(this);
            displaySection.setContent(createClassroomPageView);

            displaySection.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


            createClassroomPageView.prefWidthProperty().bind(displaySection.widthProperty());
            createClassroomPageView.prefHeightProperty().bind(displaySection.heightProperty());

            this.currentlyShowingClassrooms = false;
            this.currentlyCreatingNewClassroom = true;
        }


    }

    @FXML
    void showClassrooms(ActionEvent event) {
        if (!currentlyShowingClassrooms){
            displayClassrooms();
        }

    }



    @FXML
    public void initialize(){

        displayClassrooms();

    }
}
