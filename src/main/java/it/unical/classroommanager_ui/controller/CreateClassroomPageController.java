package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class CreateClassroomPageController {

    @FXML
    private Label capacityAlert;

    @FXML
    private MFXTextField classroomCapacity;

    @FXML
    private MFXTextField classroomCube;

    @FXML
    private MFXTextField classroomFloor;

    @FXML
    private MFXTextField classroomName;

    @FXML
    private MFXToggleButton classroomProjector;

    @FXML
    private MFXTextField classroomSocket;

    @FXML
    private Label floorAlert;

    @FXML
    private ImageView imageContainer;

    @FXML
    private Label nameAlert;

    @FXML
    private Label socketAlert;

    @FXML
    private StackPane stackImage;

    @FXML
    private MFXButton insertPhotoButton;

    @FXML
    private MFXButton removePhotoButton;

    @FXML
    private Label cubeAlert;

    MainPageController mainPageController;

    private File currentImage = null;

    boolean imageCheck = false;

    @FXML
    void insertPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli una foto");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(insertPhotoButton.getScene().getWindow());

        if (selectedFile != null) {

            currentImage = selectedFile;

            removePhotoButton.setDisable(false);

            stackImage.setStyle("-fx-border-width: 2; -fx-border-color: #4DA6FF;");

            Image image = new Image(selectedFile.getPath());

            imageContainer.setImage(image);
        }

    }

    @FXML
    void deletePhoto(ActionEvent event) {

        imageContainer.setImage(null);

        currentImage = null;

        imageCheck = false;

        removePhotoButton.setDisable(true);

        stackImage.setStyle("-fx-border-width: 2; -fx-border-color: #4DA6FF;");

    }

    public static boolean isNotInteger(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @FXML
    void createClassroom(ActionEvent event) {
        boolean name;
        boolean capacity = true;
        boolean floor = true;
        boolean socket = true;

        // NAME CHECK

        if (classroomName.getText()==null || classroomName.getText().isEmpty()){
            name = false;

            classroomName.setStyle("-fx-border-color: red");
            nameAlert.setStyle("-fx-text-fill: red");


            FadeTransition ft = new FadeTransition(Duration.seconds(1), nameAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            nameAlert.setVisible(true);
        }
        else{
            name = true;

            classroomName.setStyle("-fx-border-color: green");

            nameAlert.setVisible(false);

            nameAlert.setText("✓");
            nameAlert.setStyle("-fx-text-fill: green");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), nameAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            nameAlert.setVisible(true);
        }

        // CAPACITY CHECK

        if (!(classroomCapacity.getText()==null || classroomCapacity.getText().isEmpty()) &&
        isNotInteger(classroomCapacity.getText())){

            capacity = false;

            classroomCapacity.setStyle("-fx-border-color: red");
            capacityAlert.setStyle("-fx-text-fill: red");
            capacityAlert.setText("Inserisci un numero o lascia vuoto questo campo.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), capacityAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            capacityAlert.setVisible(true);
        }
        else{
            capacity = true;

            classroomCapacity.setStyle("-fx-border-color: green");

            capacityAlert.setVisible(false);

            capacityAlert.setText("✓");
            capacityAlert.setStyle("-fx-text-fill: green");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), capacityAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            capacityAlert.setVisible(true);
        }

        // FLOOR CHECK

        if (!(classroomFloor.getText()==null || classroomFloor.getText().isEmpty()) &&
                isNotInteger(classroomFloor.getText())){

            floor = false;

            classroomFloor.setStyle("-fx-border-color: red");
            floorAlert.setStyle("-fx-text-fill: red");
            floorAlert.setText("Inserisci un numero o lascia vuoto questo campo.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), floorAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            floorAlert.setVisible(true);
        }
        else{
            floor = true;

            classroomFloor.setStyle("-fx-border-color: green");

            floorAlert.setVisible(false);

            floorAlert.setText("✓");
            floorAlert.setStyle("-fx-text-fill: green");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), floorAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            floorAlert.setVisible(true);
        }

        // SOCKET CHECK

        if (!(classroomSocket.getText()==null || classroomSocket.getText().isEmpty()) &&
                isNotInteger(classroomSocket.getText())){

            socket = false;

            classroomSocket.setStyle("-fx-border-color: red");
            socketAlert.setStyle("-fx-text-fill: red");
            socketAlert.setText("Inserisci un numero o lascia vuoto questo campo.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), socketAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            socketAlert.setVisible(true);
        }
        else{
            socket = true;

            classroomSocket.setStyle("-fx-border-color: green");

            socketAlert.setVisible(false);

            socketAlert.setText("✓");
            socketAlert.setStyle("-fx-text-fill: green");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), socketAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            socketAlert.setVisible(true);
        }

        classroomCube.setStyle("-fx-border-color: green");
        cubeAlert.setVisible(false);

        cubeAlert.setText("✓");
        cubeAlert.setStyle("-fx-text-fill: green");

        FadeTransition ft = new FadeTransition(Duration.seconds(1), cubeAlert);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        cubeAlert.setVisible(true);


        if(name && capacity && floor && socket){

            // TODO: DIALOG BOX DI CONFERMA INSERIMENTO
            // TODO: CHIAMATA AL BACKEND PER L'INSERIMENTO E MEMORIZZAZIONE DELLA FOTO IN UNA NUOVA CARTELLA DENTRO RESOURCES
            // TODO: RITORNO ALLA PAGINA PRINCIPALE CHE MOSTRA LE AULE

        }


    }

    public void init(MainPageController mainPageController){
        this.mainPageController = mainPageController;
        stackImage.setStyle("-fx-border-width: 2; -fx-border-color: #4DA6FF;");
    }



}
