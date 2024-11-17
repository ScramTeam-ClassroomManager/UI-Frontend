package it.unical.classroommanager_ui.view;

import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class imageSelector {

    public static Image checkMark(){

        File file = new File("src/main/resources/it/unical/classroommanager_ui/view/immaginiVarie/confirmation.png");

        return new Image(file.toURI().toString(), 30, 30, false, false);

    }

    public static Image classroomImage(String classroomName){
        String imageName = classroomName + ".png";
        String imagePath = "src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/" + imageName;

        Path path = Paths.get(imagePath);
        if (Files.exists(path) && Files.isRegularFile(path)){
            File file = new File(imagePath);
            return new Image(file.toURI().toString());
        }
        else{
            File file = new File("src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/imageUnavailable.png");
            return new Image(file.toURI().toString());
        }
    }


}
