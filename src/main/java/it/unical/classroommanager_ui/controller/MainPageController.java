package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.model.Role;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.ClassroomListPageView;
import it.unical.classroommanager_ui.view.CreateClassroomPageView;
import it.unical.classroommanager_ui.view.RequestListPageView;
import it.unical.classroommanager_ui.view.SceneHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainPageController {

    public BorderPane getBPane() {
        return BPane;
    }

    @FXML
    private BorderPane BPane;

    @FXML
    private Label labelAule;

    @FXML
    private Label labelLogout;

    @FXML
    private Label labelProfilo;

    @FXML
    private Label labelRichieste;

    boolean currentlyShowingClassrooms = false;
    boolean currentlyCreatingNewClassroom = false;
    boolean currentlyShowingRequests = false;

    @FXML
    void clickAule(MouseEvent event) {
        if (UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString())){
            try {
                FXMLLoader loader = new FXMLLoader(CreateClassroomPageView.class.getResource("newClassroomPage.fxml"));
                AnchorPane nuovoAnchorPane = loader.load();
                CreateClassroomPageController createClassroomPageController = loader.getController();

                createClassroomPageController.setBPane(BPane);
                BPane.setCenter(nuovoAnchorPane);


            }   catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                FXMLLoader loader = new FXMLLoader(ClassroomListPageView.class.getResource("classroomListPage.fxml"));
                AnchorPane nuovoAnchorPane = loader.load();
                ClassroomListPageController classroomListPageController = loader.getController();
                classroomListPageController.init(this);

                classroomListPageController.setBPane(BPane);
                BPane.setCenter(nuovoAnchorPane);


            }   catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void clickLogout(MouseEvent event) {
        try {
            SceneHandler.getInstance().createLoginScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void clickProfilo(MouseEvent event) {

    }

    public void displayRequests() {
        try {
            FXMLLoader loader = new FXMLLoader(RequestListPageView.class.getResource("requestListPage.fxml"));
            AnchorPane nuovoAnchorPane = loader.load();
            RequestListPageController requestListPageController = loader.getController();
            requestListPageController.init(this);

            requestListPageController.setBPane(BPane);

            BPane.setCenter(nuovoAnchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshRequestList() {
        displayRequests();
    }


    @FXML
    void clickRichieste(MouseEvent event) throws IOException {
        displayRequests();
    }

    public void init() {
        if (UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString())){
            labelAule.setText("Aggiungi Aula");
        }
        else {
            labelAule.setText("Prenota Aula");
        }
    }
}
