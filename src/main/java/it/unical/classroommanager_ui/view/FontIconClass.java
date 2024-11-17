package it.unical.classroommanager_ui.view;

import org.kordamp.ikonli.javafx.FontIcon;

public class FontIconClass extends FontIcon {
    public FontIconClass(String name, int size) {
        super(name);
        this.setIconSize(size);
        this.getStyleClass().add("icons-color");
    }

}
