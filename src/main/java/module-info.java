module it.unical.classroommanager_ui {

    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires jakarta.validation;
    requires MaterialFX;
    requires jjwt.api;
    requires jjwt.impl;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.springdoc.openapi.common;
    requires javafx.base;
    requires org.kordamp.ikonli.javafx;
    opens it.unical.classroommanager_ui.model to com.fasterxml.jackson.databind;

    opens it.unical.classroommanager_ui to javafx.fxml;
    exports it.unical.classroommanager_ui;
    exports it.unical.classroommanager_ui.view;
    opens it.unical.classroommanager_ui.view to javafx.fxml;
    exports it.unical.classroommanager_ui.controller;
    opens it.unical.classroommanager_ui.controller to javafx.fxml;
}