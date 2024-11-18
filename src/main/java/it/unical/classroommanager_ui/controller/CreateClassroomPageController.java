package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import it.unical.classroommanager_ui.model.UserManager;
import it.unical.classroommanager_ui.view.SceneHandler;
import it.unical.classroommanager_ui.view.imageSelector;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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

    @FXML
    private MFXComboBox<String> typeCBox;

    @FXML
    private Label typeAlert;

    @FXML
    private BorderPane BPaneListPage;

    public void setBPane(BorderPane BPane){
        this.BPaneListPage = BPane;
    }


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

    public static boolean isNotInteger(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @FXML
    void createClassroom(ActionEvent event) throws IOException {
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

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Conferma inserimento");
            alert.setHeaderText("Inserimento");
            alert.setContentText("Cliccare 'OK' completerà la procedura di inserimento della nuova aula nel sistema.");

            alert.getDialogPane().setGraphic(new ImageView(imageSelector.checkMark()));


            alert.getDialogPane().setMinHeight(200);
            alert.getDialogPane().setMinWidth(200);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){

                // start connection
                URL url = new URL("http://localhost:8080/api/v1/class/addClass");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);

                connection.setRequestProperty("Authorization", "Bearer " + UserManager.getInstance().getToken());

                // prepare input
                String jsonInputString = String.format("{\"name\": \"%s\", \"cube\": \"%s\"," +
                                "\"floor\": \"%s\", \"capability\": \"%s\", \"numSocket\": \"%s\", " +
                                "\"projector\": \"%s\", \"available\": \"%s\", \"type\": \"%s\"}",
                        nameText, cubeText, floorText, capacityText,
                        socketText, projectorText, availableText, typeText);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                catch(Exception e){
                    throw new IOException();
                }


                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    System.out.println("Aula inserita nel sistema con successo.");
                }
                else{
                    System.out.println("Insuccesso nell'inserimento dell'aula nel sistema.");
                }



                // FOTO INSERITA NEL DB SE PRESENTE
                if (imageCheck && currentImage!=null && responseCode == HttpURLConnection.HTTP_CREATED) {
                    FileInputStream in = new FileInputStream(currentImage);

                    String newImageName = classroomName.getText() + ".png";
                    FileOutputStream ou = new FileOutputStream("src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/" + newImageName);

                    BufferedInputStream bin = new BufferedInputStream(in);
                    BufferedOutputStream bou = new BufferedOutputStream(ou);

                    int b = 0;
                    while (b != -1) {

                        b = bin.read();
                        bou.write(b);
                    }
                    bin.close();
                    bou.close();
                }

                SceneHandler.getInstance().createMainPageScene();






            }

        }


    }

    public void init(MainPageController mainPageController){
        this.mainPageController = mainPageController;
        stackImage.setStyle("-fx-border-width: 2; -fx-border-color: #4DA6FF;");

        typeCBox.getItems().addAll("NORMAL", "AUDITORIUM");

    }



}
