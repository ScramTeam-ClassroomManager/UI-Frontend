package it.unical.classroommanager_ui.view;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class imageSelector {

    public static Image checkMark(){

        File file = new File("src/main/resources/it/unical/classroommanager_ui/view/immaginiVarie/confirmation.png");

        return new Image(file.toURI().toString(), 30, 30, false, false);

    }

    public static boolean classroomImageExists(String classroomName){
        String imageName = classroomName + ".png";
        String imagePath = "src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/" + imageName;

        Path path = Paths.get(imagePath);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            return true;
        }
        else{
            return false;
        }
    }

    public static void deleteImage(String classroomName){
        String imageName = classroomName + ".png";
        String imagePath = "src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/" + imageName;

        Path path = Paths.get(imagePath);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            try {
                Files.delete(path);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void renameImage(String previousName, String newName){
        String previousImagePath = "src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/" + previousName + ".png";
        String newImagePath = "src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/" + newName + ".png";

        Path previousPath = Paths.get(previousImagePath);
        Path newPath = Paths.get(newImagePath);

        // doppio check per assicurarsi l'immagine esiste
        if (Files.exists(previousPath) && Files.isRegularFile(previousPath)) {
            try {
                Files.move(previousPath, newPath);
            } catch (IOException e) {
                throw new RuntimeException("Error renaming image from: " + previousName + " to: " + newName, e);
            }
        }

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

    public static Image unspecifiedImage(){
        File file = new File("src/main/resources/it/unical/classroommanager_ui/view/classroomImagesDB/imageUnavailable.png");
        return new Image(file.toURI().toString());
    }


}
