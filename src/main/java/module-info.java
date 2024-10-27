module it.unical.classroommanager_ui {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unical.classroommanager_ui to javafx.fxml;
    exports it.unical.classroommanager_ui;
    exports it.unical.classroommanager_ui.view;
    opens it.unical.classroommanager_ui.view to javafx.fxml;
    exports it.unical.classroommanager_ui.controller;
    opens it.unical.classroommanager_ui.controller to javafx.fxml;
}