package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.Role;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
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

    String currPage = "";

    @FXML
    void clickAule(MouseEvent event) {
        if (!currPage.equals("cubi")) {
            displayCubes();
        }
    }

    void displayClassrooms(){
        if (!(UserManager.getInstance().getToken().isEmpty())) {
            if (UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString()) && !currPage.equals("aule")) {
                try {
                    FXMLLoader loader = new FXMLLoader(CreateClassroomPageView.class.getResource("newClassroomPage.fxml"));
                    AnchorPane nuovoAnchorPane = loader.load();
                    CreateClassroomPageController createClassroomPageController = loader.getController();
                    createClassroomPageController.init(this);

                    createClassroomPageController.setBPane(BPane);
                    BPane.setCenter(nuovoAnchorPane);

                    currPage = "aule";

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (!currPage.equals("aule")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(ClassroomListPageView.class.getResource("classroomListPage.fxml"));
                        AnchorPane nuovoAnchorPane = loader.load();
                        ClassroomListPageController classroomListPageController = loader.getController();
                        classroomListPageController.init(this);

                        classroomListPageController.setBPane(BPane);
                        BPane.setCenter(nuovoAnchorPane);

                        currPage = "aule";


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        else{
            if (!currPage.equals("aule")) {
                try {
                    FXMLLoader loader = new FXMLLoader(ClassroomListPageView.class.getResource("classroomListPage.fxml"));
                    AnchorPane nuovoAnchorPane = loader.load();
                    ClassroomListPageController classroomListPageController = loader.getController();
                    classroomListPageController.init(this);

                    classroomListPageController.setBPane(BPane);
                    BPane.setCenter(nuovoAnchorPane);

                    currPage = "aule";


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void displayClassroomsByCube(int cubeNumber) {
        try {
            FXMLLoader loader = new FXMLLoader(ClassroomListPageView.class.getResource("classroomListPage.fxml"));
            AnchorPane nuovoAnchorPane = loader.load();
            ClassroomListPageController classroomListPageController = loader.getController();
            classroomListPageController.init(this);

            classroomListPageController.fillClassroomListByCube(cubeNumber);
            classroomListPageController.setBPane(BPane);
            BPane.setCenter(nuovoAnchorPane);

            currPage = "aulePerCubo";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayCubes() {
        try {
            FXMLLoader loader = new FXMLLoader(CubeListPageView.class.getResource("cubeListPage.fxml"));
            AnchorPane nuovoAnchorPane = loader.load();
            CubeListPageController cubeListPageController = loader.getController();
            cubeListPageController.fillCubeList(this);

            cubeListPageController.setBPane(BPane);
            BPane.setCenter(nuovoAnchorPane);

            currPage = "cubi";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    void displayClassroomDetails(ClassroomDto classroomDto){
        ClassroomDetailsPageView classroomDetailsPageView = new ClassroomDetailsPageView(this, classroomDto);

        BPane.setCenter(classroomDetailsPageView);

        currPage = "dettagliAula";


    }

    @FXML
    void clickLogout(MouseEvent event) {
        if (!(UserManager.getInstance().getToken().isEmpty())) {
            try {
                UserManager.getInstance().logout();
                SceneHandler.getInstance().createLoginScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                SceneHandler.getInstance().createLoginScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void clickProfilo(MouseEvent event) {
        if (!(UserManager.getInstance().getToken().isEmpty())){

        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Profilo");
            alert.setHeaderText("Avviso");
            alert.setContentText("Per poter visualizzare il profilo devi essere loggato.");

            alert.getDialogPane().setMinHeight(200);
            alert.getDialogPane().setMinWidth(200);
            alert.show();
        }

    }

    public void displayRequests() {
        try {
            FXMLLoader loader = new FXMLLoader(RequestListPageView.class.getResource("requestListPage.fxml"));
            AnchorPane nuovoAnchorPane = loader.load();
            RequestListPageController requestListPageController = loader.getController();
            requestListPageController.init(this);

            requestListPageController.setBPane(BPane);

            BPane.setCenter(nuovoAnchorPane);

            currPage = "richieste";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshRequestList() {
        displayRequests();
    }

    @FXML
    void clickRichieste(MouseEvent event) throws IOException {
        if (!(UserManager.getInstance().getToken().isEmpty())){
            if(!currPage.equals("richieste")) {
                displayRequests();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Richieste");
            alert.setHeaderText("Avviso");
            alert.setContentText("Per poter visualizzare le richieste devi essere loggato.");

            alert.getDialogPane().setMinHeight(200);
            alert.getDialogPane().setMinWidth(200);
            alert.show();
        }

    }

    public void init() {
        if (!(UserManager.getInstance().getToken().isEmpty())) {
            if (UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString())) {
                labelAule.setText("Aggiungi Aula");

            } else {
                labelAule.setText("Prenota Aula");
                displayCubes();
            }
        }
        else{
            labelLogout.setText("Login");
            labelAule.setText("Aule");
            displayCubes();
        }
    }
}
