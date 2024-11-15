package it.unical.classroommanager_ui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


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

    public void createLoginScene() throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("loginPage.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setResizable(false);

        stage.setTitle("Classroom Manager");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }


    public void createRegisterScene() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("registerPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMainPageScene() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("mainPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1500, 760);

            /*
            for (String style : List.of("css/custom.css")) {
                String resource = Objects.requireNonNull(SceneHandler.class.getResource(style)).toExternalForm();
                scene.getStylesheets().add(resource);
            }

             */


            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMinWidth(1250);
            stage.setMinHeight(800);
            stage.setResizable(true);
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public BorderPane createRequestsView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneHandler.class.getResource("requestsPage.fxml"));
            return new BorderPane(fxmlLoader.load());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }






}
