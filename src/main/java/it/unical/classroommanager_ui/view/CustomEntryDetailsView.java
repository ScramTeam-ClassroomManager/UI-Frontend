package it.unical.classroommanager_ui.view;

import com.calendarfx.model.Entry;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomEntryDetailsView extends VBox {

    private Label userLabel;
    private Label classroomLabel;
    private Label reasonLabel;

    public CustomEntryDetailsView(Entry<?> entry) {

        super();
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ffffff; " +
                "-fx-border-color: #9b2030; " +
                "-fx-border-radius: 10px; " +
                "-fx-background-radius: 10px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        String username = entry.getTitle().split(":")[0];
        String reason = entry.getTitle().split(":")[1].split("\n")[0];

        userLabel = new Label("Utente: " + username);
        classroomLabel = new Label("Aula: " + entry.getLocation());
        reasonLabel = new Label("Motivazione: " + reason);

        HBox userBox = createCustomRow(userLabel, "user-icon.png");
        HBox classroomBox = createCustomRow(classroomLabel, "classroom-icon.png");
        HBox reasonBox = createCustomRow(reasonLabel, "reason-icon.png");

        this.getChildren().addAll(userBox, classroomBox, reasonBox);
    }


    private HBox createCustomRow(Label label, String iconPath) {
        HBox container = new HBox();
        container.setSpacing(10);
        container.setPadding(new Insets(5));

        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #8B0000;");

        container.getChildren().add(label);
        return container;
    }
}
