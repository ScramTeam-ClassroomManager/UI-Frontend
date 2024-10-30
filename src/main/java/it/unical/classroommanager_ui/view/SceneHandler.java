package it.unical.classroommanager_ui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SceneHandler {
    private static SceneHandler instance;
    private Stage stage;

    private SceneHandler(){}

    public static SceneHandler getInstance(){
        if (instance == null){
            instance = new SceneHandler();
        }
        return instance;
    }

    public void init(Stage stage){
        if (this.stage != null){
            return;
        }
        this.stage = stage;
    }

    public void createLoginScene(){
        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("loginPage.fxml"));
        // INSERIRE LA GIUSTA GRANDEZZA
        //Scene scene = new Scene(fxmlLoader.load(), 520, 569);

        stage.setResizable(false);

        stage.setTitle("Classroom Manager");
        //stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void createRegisterScene() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("registerPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 635, 681);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
