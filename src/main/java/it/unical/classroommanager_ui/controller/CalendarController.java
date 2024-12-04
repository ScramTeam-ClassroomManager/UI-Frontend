package it.unical.classroommanager_ui.controller;

import javafx.fxml.FXML;
import com.calendarfx.view.CalendarView;
import javafx.scene.layout.BorderPane;

public class CalendarController {

    @FXML
    private CalendarView calendarView;
    private MainPageController mainPageController;

    @FXML
    private BorderPane BPaneListPage;

    @FXML
    public void init(MainPageController mainPageController) {
        // Configura il calendario (opzionale)
        this.mainPageController = mainPageController;
        calendarView.showWeekPage(); // Mostra la vista settimanale
    }

    public void setBPane(BorderPane BPane){
        this.BPaneListPage = BPane;
    }
}
