package it.unical.classroommanager_ui.view;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneHandler.getInstance().init(stage);
        SceneHandler.getInstance().createMainPageScene();
    }

    public static void main(String[] args) {
        launch();
    }
}