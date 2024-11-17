package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.view.RequestListPageView;
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

    }

    @FXML
    void clickLogout(MouseEvent event) {

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
            e.printStackTrace();
        }
    }

    public void refreshRequestList() {
        displayRequests();
    }


    @FXML
    void clickRichieste(MouseEvent event) throws IOException {
        displayRequests();
    }
}
