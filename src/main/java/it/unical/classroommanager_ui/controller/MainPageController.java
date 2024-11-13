package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.unical.classroommanager_ui.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Label labelAule;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label labelLogout;

    @FXML
    private Label labelProfilo;

    @FXML
    private Label labelRichieste;


    @FXML
    private MFXButton buttonAccetta;

    @FXML
    private MFXButton buttonRifiuta;

    @FXML
    private BorderPane requestsView;

    @FXML
    private MFXTableView<?> tableview;

    @FXML
    private MFXTextField textfield;

    @FXML
    private VBox mainVbox;

    @FXML
    void accetta(ActionEvent event) {

    }

    @FXML
    void rifiuta(ActionEvent event) {

    }

    private void loadRequestsPage() {
        BorderPane requestsView = SceneHandler.getInstance().createRequestsView();

        mainVbox.getChildren().add(requestsView);
    }

    @FXML
    void clickAule(MouseEvent event) {

    }

    @FXML
    void clickLogout(MouseEvent event) throws IOException {
        SceneHandler.getInstance().createLoginScene();

    }

    @FXML
    void clickProfilo(MouseEvent event) {

    }

    @FXML
    void clickRichieste(MouseEvent event) {
        loadRequestsPage();
    }




}
