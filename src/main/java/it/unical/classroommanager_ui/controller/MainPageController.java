package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.model.Role;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.image.ImageView;
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
    private Label labelUtenti;

    @FXML
    private Label labelLogout;

    @FXML
    private Label labelProfilo;

    @FXML
    private Label labelRichieste;

    @FXML
    private Label labelInserimentoAula;

    @FXML
    private Label labelStoricoPren;

    @FXML
    private Label labelCalendario;

    @FXML
    private ImageView ImmInser;

    @FXML
    private ImageView ImmStorico;

    @FXML
    private ImageView ImmLogIn;

    @FXML
    private ImageView ImmLogOut;

    @FXML
    private ImageView ImmUtenti;

    String currPage = "";

    @FXML
    void clickAule(MouseEvent event) {
        if (!currPage.equals("cubi")) {
            displayDepartments();
        }
    }

    void displayClassroomDetails(ClassroomDto classroomDto){
        ClassroomDetailsPageView classroomDetailsPageView = new ClassroomDetailsPageView(this, classroomDto);

        BPane.setCenter(classroomDetailsPageView);

        currPage = "dettagliAula";

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

    public void displayModifyClassroomPage(ClassroomDto classroomDto){
        ModifyClassroomPageView modifyClassroomPageView = new ModifyClassroomPageView(this, classroomDto);

        BPane.setCenter(modifyClassroomPageView);

        currPage = "modificaAula";

    }


    public void refreshRequestList() {
        displayRequests();
    }

    @FXML
    void  clickUtenti(MouseEvent event){
        if(!currPage.equals("utenti")) {
            try {
                FXMLLoader loader = new FXMLLoader(UtentiListPageView.class.getResource("UtentiListPage.fxml"));
                AnchorPane nuovoAnchorPane = loader.load();
                UtentiListPageController utentiListPageController = loader.getController();

                utentiListPageController.init(this);

                utentiListPageController.setBPane(BPane);
                BPane.setCenter(nuovoAnchorPane);

                currPage = "utenti";

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void clickInserisciAula(MouseEvent event) {
        if (!currPage.equals("inserimentoAula")) {
            try {
                FXMLLoader loader = new FXMLLoader(CreateClassroomPageView.class.getResource("newClassroomPage.fxml"));
                AnchorPane nuovoAnchorPane = loader.load();
                CreateClassroomPageController createClassroomPageController = loader.getController();
                createClassroomPageController.init(this);

                createClassroomPageController.setBPane(BPane);
                BPane.setCenter(nuovoAnchorPane);

                currPage = "inserimentoAula";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
    void clickCalendario(MouseEvent event){
        if (!currPage.equals("calendario")) {
            try{
                FXMLLoader loader = new FXMLLoader(CalendarPageView.class.getResource("calendarPage.fxml"));
                AnchorPane nuovoAnchorPane = loader.load();
                CalendarController calendarController = loader.getController();
                calendarController.init(this);

                calendarController.setBPane(BPane);
                BPane.setCenter(nuovoAnchorPane);
                currPage = "calendario";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void clickProfilo(MouseEvent event) {
        if (!(UserManager.getInstance().getToken().isEmpty())){
            if(!currPage.equals("profilo")){

                ProfilePageView profilePageView = new ProfilePageView(this);
                BPane.setCenter(profilePageView);

                currPage = "profilo";

            }
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

    @FXML
    void clickRichieste(MouseEvent event) throws IOException {
        if (!(UserManager.getInstance().getToken().isEmpty())) {
            if (!currPage.equals("richieste")) {
                if (UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString())) {
                    displayRequests();
                }
                else {
                    displayRequestHistory();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Richieste");
            alert.setHeaderText("Avviso");
            alert.setContentText("Per poter visualizzare le richieste devi essere loggato.");

            alert.getDialogPane().setMinHeight(200);
            alert.getDialogPane().setMinWidth(200);
            alert.show();
        }

    }

    public void displayDepartments() {
        try {
            FXMLLoader loader = new FXMLLoader(DepartmentListPageView.class.getResource("departmentListPage.fxml"));
            AnchorPane nuovoAnchorPane = loader.load();
            DepartmentListPageController departmentListPageController = loader.getController();
            departmentListPageController.fillDepartmentList(this);
            departmentListPageController.setMainPageController(this);

            departmentListPageController.setBPane(BPane);
            BPane.setCenter(nuovoAnchorPane);

            currPage = "dipartimenti";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {
        if (!(UserManager.getInstance().getToken().isEmpty())) {
            if (UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString())) {
                labelInserimentoAula.setVisible(true);
                labelUtenti.setVisible(true);
                ImmLogOut.setVisible(true);
                ImmLogIn.setVisible(false);
                ImmUtenti.setVisible(true);
            } else {
                labelInserimentoAula.setVisible(false);
                ImmInser.setVisible(false);
                ImmStorico.setVisible(false);
                labelStoricoPren.setVisible(false);
                ImmLogOut.setVisible(true);
                ImmLogIn.setVisible(false);
            }
            displayDepartments();
        } else {
            labelLogout.setText("Login");
            labelInserimentoAula.setVisible(false);
            labelStoricoPren.setVisible(false);
            labelUtenti.setVisible(false);
            ImmInser.setVisible(false);
            ImmStorico.setVisible(false);
            ImmLogOut.setVisible(false);
            ImmLogIn.setVisible(true);
            ImmUtenti.setVisible(false);
            displayDepartments();
        }
    }

    @FXML
    void clickStoricoPren(MouseEvent event) {
        if (!(UserManager.getInstance().getToken().isEmpty())) {
            if (!currPage.equals("storico")) {
                displayRequestHistory();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Storico Prenotazioni");
            alert.setHeaderText("Avviso");
            alert.setContentText("Per poter visualizzare lo storico delle prenotazioni devi essere loggato.");

            alert.getDialogPane().setMinHeight(200);
            alert.getDialogPane().setMinWidth(200);
            alert.show();
        }
    }

    public void displayRequestHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(RequestHistoryPageView.class.getResource("requestHistoryPage.fxml"));
            AnchorPane nuovoAnchorPane = loader.load();
            RequestHistoryPageController requestHistoryPageController = loader.getController();

            boolean isAdmin = UserManager.getInstance().getUser().role().equals(Role.ADMIN.toString());
            requestHistoryPageController.init(this, isAdmin);

            requestHistoryPageController.setBPane(BPane);
            BPane.setCenter(nuovoAnchorPane);

            currPage = "storico";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayClassroomsByDepartment(long departmentId) {
        try {
            FXMLLoader loader = new FXMLLoader(ClassroomListPageView.class.getResource("classroomListPage.fxml"));
            AnchorPane nuovoAnchorPane = loader.load();
            ClassroomListPageController classroomListPageController = loader.getController();

            classroomListPageController.init(this, departmentId);

            classroomListPageController.setBPane(BPane);
            BPane.setCenter(nuovoAnchorPane);

            currPage = "aulePerDipartimento";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
