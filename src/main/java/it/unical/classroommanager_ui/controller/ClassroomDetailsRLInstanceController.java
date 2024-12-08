package it.unical.classroommanager_ui.controller;

import it.unical.classroommanager_ui.model.RequestDto;
import it.unical.classroommanager_ui.view.FontIconClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ClassroomDetailsRLInstanceController {

    @FXML
    private Label dateIconLabel;

    @FXML
    private Text dateText;

    @FXML
    private Text endHour;

    @FXML
    private Label hourIconLabel;

    @FXML
    private Text startHour;

    MainPageController mainPageController;

    public void init(MainPageController mainPageController, RequestDto requestDto){

        this.mainPageController = mainPageController;

        dateText.setText("" + requestDto.getRequestDate());
        startHour.setText("" + requestDto.getStartHour());
        endHour.setText("" + requestDto.getEndHour());


        dateIconLabel.setGraphic((new FontIconClass("mdi2c-calendar-month", 20)));
        hourIconLabel.setGraphic((new FontIconClass("mdi2c-clock", 20)));

    }

}
