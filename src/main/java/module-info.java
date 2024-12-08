module it.unical.classroommanager_ui {

    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires jakarta.validation;
    requires MaterialFX;
    requires jjwt.api;
    requires jjwt.impl;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.springdoc.openapi.common;
    requires com.calendarfx.view;
    requires org.kordamp.ikonli.fontawesome;

    opens it.unical.classroommanager_ui to javafx.fxml;
    exports it.unical.classroommanager_ui;
    exports it.unical.classroommanager_ui.view;
    exports it.unical.classroommanager_ui.controller;
    opens it.unical.classroommanager_ui.view to com.fasterxml.jackson.databind, javafx.fxml;
    exports it.unical.classroommanager_ui.model;
    opens it.unical.classroommanager_ui.model to com.fasterxml.jackson.databind, javafx.fxml;
    opens it.unical.classroommanager_ui.controller to com.fasterxml.jackson.databind, javafx.fxml;
}