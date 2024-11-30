package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.model.ClassroomDto;
import it.unical.classroommanager_ui.view.FontIconClass;
import it.unical.classroommanager_ui.view.imageSelector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class ModifyAlertController {

    @FXML
    private Label arrowLabel;

    @FXML
    private Label arrowLabel1;

    @FXML
    private ImageView newImage;

    @FXML
    private Label nextCapability;

    @FXML
    private Label nextCube;

    @FXML
    private Label nextFloor;

    @FXML
    private Text nextName;

    @FXML
    private Label nextProjector;

    @FXML
    private Label nextSocket;

    @FXML
    private Label nextType;

    @FXML
    private Label previousCapability;

    @FXML
    private Label previousCube;

    @FXML
    private Label previousFloor;

    @FXML
    private ImageView previousImage;

    @FXML
    private Text previousName;

    @FXML
    private Label previousProjector;

    @FXML
    private Label previousSocket;

    @FXML
    private Label previousType;

    ModifyClassroomPageController modifyClassroomPageController;
    MainPageController mainPageController;

    boolean confirmation = false;

    boolean getConfirmation(){
        return confirmation;
    }

    @FXML
    void confirmAction(ActionEvent event) {
        Stage stage = (Stage) arrowLabel.getScene().getWindow();
        this.confirmation = true;
        stage.close();
    }

    public void init(ModifyClassroomPageController modifyClassroomPageController, MainPageController mainPageController,
                     ClassroomDto classroomDto, String nameText, String cubeText, String floorText,
                     String capacityText, String socketText, String projectorText, String availableText, String typeText, File currentImage){

        this.modifyClassroomPageController = modifyClassroomPageController;
        this.mainPageController = mainPageController;


        arrowLabel.setGraphic((new FontIconClass("mdi2a-arrow-right-bold", 20)));
        arrowLabel1.setGraphic((new FontIconClass("mdi2a-arrow-right-bold", 20)));


        // SET IMMAGINE

        previousImage.setImage(imageSelector.classroomImage(classroomDto.getName()));

        if(currentImage!=null) {
            Image image = new Image(currentImage.getPath());
            newImage.setImage(image);
        }
        else{
            newImage.setImage(imageSelector.unspecifiedImage());
        }

        // SET ALTRI CAMPI
        // PREVIOUS

        previousName.setText(classroomDto.getName());
        previousCapability.setText("("+classroomDto.getCapability()+")");
        previousCube.setText("("+classroomDto.getCubeNumber()+")");
        previousFloor.setText("("+classroomDto.getFloor()+")");
        previousSocket.setText("("+classroomDto.getNumSocket()+")");
        previousType.setText(classroomDto.getType());
        if(classroomDto.isProjector()){
            previousProjector.setText("Si.");
        }
        else{
            previousProjector.setText("No.");
        }

        // NEW

        nextName.setText(nameText);
        nextCapability.setText("("+capacityText+")");
        nextCube.setText("("+cubeText+")");
        nextFloor.setText("("+floorText+")");
        nextSocket.setText("("+socketText+")");
        nextType.setText(typeText);
        if(projectorText.equals("true")){
            nextProjector.setText("Si.");
        }
        else{
            nextProjector.setText("No.");
        }

    }




}
