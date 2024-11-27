package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.unical.classroommanager_ui.model.CubeDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CubeListInstanceController {

    @FXML
    private Label cubeNumberLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private MFXButton ViewAuleButton;

    private MainPageController mainPageController;
    private CubeDto cube;

    public void init(MainPageController mainPageController, CubeDto cube) {
        this.mainPageController = mainPageController;
        this.cube = cube;

        cubeNumberLabel.setText("" + cube.getNumber());
        departmentLabel.setText(cube.getDepartment());

        ViewAuleButton.setOnMouseClicked(this::clickViewAule);
    }

    @FXML
    void clickViewAule(MouseEvent event) {
        if (mainPageController != null && cube != null) {
            mainPageController.displayClassroomsByCube(cube.getNumber());
        }
    }
}
