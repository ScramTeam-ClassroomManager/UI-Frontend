package it.unical.classroommanager_ui.view;

import javafx.scene.image.Image;

import java.io.File;

public class imageSelector {

    public static Image checkMark(){

        File file = new File("src/main/resources/it/unical/classroommanager_ui/view/immaginiVarie/confirmation.png");

        return new Image(file.toURI().toString(), 30, 30, false, false);


    }
}
