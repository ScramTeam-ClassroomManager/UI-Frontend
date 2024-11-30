package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.view.SceneHandler;
import it.unical.classroommanager_ui.view.imageSelector;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

import static it.unical.classroommanager_ui.controller.CreateClassroomPageController.isNotInteger;

public class ModifyClassroomPageController {

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
    private Label cubeAlert;

    @FXML
    private Label floorAlert;

    @FXML
    private ImageView imageContainer;

    @FXML
    private MFXButton insertPhotoButton;

    @FXML
    private Label nameAlert;

    @FXML
    private MFXButton removePhotoButton;

    @FXML
    private Label socketAlert;

    @FXML
    private StackPane stackImage;

    @FXML
    private Text textName;

    @FXML
    private Label typeAlert;

    @FXML
    private MFXComboBox<String> typeCBox;

    MainPageController mainPageController;
    ClassroomDto classroomDto;

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

            imageCheck = true;
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



    @FXML
    void modifyClassroom(ActionEvent event) throws IOException {

        boolean name;
        boolean capacity = true;
        boolean floor = true;
        boolean socket = true;
        boolean type = false;
        boolean cube = true;

        String nameText = "N/A";
        String cubeText = "0";
        String floorText = "0";
        String capacityText = "0";
        String socketText = "0";
        String projectorText = "false";
        String availableText = "true";
        String typeText = "N/A";

        // NAME CHECK

        if (classroomName.getText()==null || classroomName.getText().isEmpty()){
            name = false;

            classroomName.setStyle("-fx-border-color: red");

            nameAlert.setStyle("-fx-text-fill: red");
            nameAlert.setText("La compilazione di questo campo è obbligatoria.");


            FadeTransition ft = new FadeTransition(Duration.seconds(1), nameAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            nameAlert.setVisible(true);
        }
        else{
            name = true;
            nameText = classroomName.getText();

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
            if (classroomCapacity.getText()==null || classroomCapacity.getText().isEmpty()){
                capacityText = "0";
            }
            else{
                capacityText = classroomCapacity.getText();
            }

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
            if (classroomFloor.getText()==null || classroomFloor.getText().isEmpty()){
                floorText = "0";
            }
            else{
                floorText = classroomFloor.getText();
            }

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
            if (classroomSocket.getText()==null || classroomSocket.getText().isEmpty()){
                socketText = "0";
            }
            else{
                socketText = classroomSocket.getText();
            }

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


        if (typeCBox.getValue()==null || typeCBox.getValue().isEmpty()){
            type = false;

            typeCBox.setStyle("-fx-border-color: red");
            typeAlert.setStyle("-fx-text-fill: red");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), typeAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            typeAlert.setVisible(true);
        }
        else{
            type = true;
            typeText = typeCBox.getValue();

            typeCBox.setStyle("-fx-border-color: green");

            typeAlert.setVisible(false);

            typeAlert.setText("✓");
            typeAlert.setStyle("-fx-text-fill: green");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), typeAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            typeAlert.setVisible(true);

        }

        // CUBE CHECK

        if (!(classroomCube.getText()==null || classroomCube.getText().isEmpty()) &&
                isNotInteger(classroomCube.getText())){

            cube = false;

            classroomCube.setStyle("-fx-border-color: red");
            cubeAlert.setStyle("-fx-text-fill: red");
            cubeAlert.setText("Inserisci un numero o lascia vuoto questo campo.");

            FadeTransition ft = new FadeTransition(Duration.seconds(1), cubeAlert);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            cubeAlert.setVisible(true);
        }
        else{
            cube = true;
            if (classroomCube.getText()==null || classroomCube.getText().isEmpty()){
                cubeText = "0";
            }
            else{
                cubeText = classroomCube.getText();
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
        }


        // PROJECTOR CHECK

        if(classroomProjector.isSelected()){
            projectorText = "true";
        }
        else{
            projectorText = "false";
        }


        if(name && capacity && floor && socket && type && cube){

            FXMLLoader loader = new FXMLLoader(SceneHandler.class.getResource("modifyAlert.fxml"));
            AnchorPane root = loader.load();

            ModifyAlertController controller = loader.getController();
            controller.init(this, mainPageController, classroomDto, nameText, cubeText, floorText,
                    capacityText, socketText, projectorText, availableText, typeText, currentImage);

            Stage modalStage = new Stage();
            modalStage.setTitle("Finestra di conferma delle modifiche");
            modalStage.initModality(Modality.APPLICATION_MODAL);

            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();


            if(controller.getConfirmation()){
                //TODO: SE ALERTA CONFERMA, CHIAMA IL BACKEND
                //TODO: ELIMINA IMMAGINE NEL DB SE NON ERA QUELLA UNKNOWN, ALTRIMENTI SALVA LA NUOVA O ELIMINA LA PRECEDENTE
                //TODO: TORNA ALLA PAGINA PRINCIPALE
            }

        }

    }



    public void init(MainPageController mainPageController, ClassroomDto classroomDto){
        this.mainPageController = mainPageController;
        this.classroomDto = classroomDto;

        typeCBox.getItems().addAll("NORMAL", "AUDITORIUM");

        textName.setText(classroomDto.getName());
        classroomName.setText(classroomDto.getName());
        classroomCapacity.setText(Integer.toString(classroomDto.getCapability()));
        classroomCube.setText(Integer.toString(classroomDto.getCube()));
        classroomFloor.setText(Integer.toString(classroomDto.getFloor()));
        classroomSocket.setText(Integer.toString(classroomDto.getNumSocket()));

        typeCBox.setText(classroomDto.getType());
        typeCBox.setValue(classroomDto.getType());

        if(classroomDto.isProjector()){
            classroomProjector.setSelected(true);
        }

        imageContainer.setImage(imageSelector.classroomImage(classroomDto.getName()));
        stackImage.setStyle("-fx-border-width: 2; -fx-border-color: #4DA6FF;");


    }
}
