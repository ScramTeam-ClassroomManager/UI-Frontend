package it.unical.classroommanager_ui.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.unical.classroommanager_ui.model.DepartmentDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class DepartmentListInstanceController {

    @FXML
    private Label departmentLabel;

    @FXML
    private MFXButton ViewAuleButton;

    private MainPageController mainPageController;
    private DepartmentDto department;

    public void init(MainPageController mainPageController, DepartmentDto department) {
        this.mainPageController = mainPageController;
        this.department = department;

        departmentLabel.setText(department.getDepartment());

        ViewAuleButton.setOnMouseClicked(this::clickViewAule);
    }

    @FXML
    void clickViewAule(MouseEvent event) {
        if (mainPageController != null && department != null) {
            mainPageController.displayClassroomsByDepartment(department.getId());
        }
    }
}
