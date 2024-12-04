package it.unical.classroommanager_ui.model;

import com.calendarfx.model.Entry;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CustomEntryDetailsView extends VBox {

    private Label userLabel;
    private Label classroomLabel;
    private Label reasonLabel;

    public CustomEntryDetailsView(Entry<?> entry) {
        // Inizializzazione base della VBox
        super();
        this.setSpacing(10); // Spaziatura tra gli elementi
        this.setPadding(new Insets(15)); // Padding interno alla VBox

        // Creazione delle etichette
        String username = entry.getTitle().split(":")[0];
        String reason = entry.getTitle().split(":")[1].split("\n")[0];
        userLabel = new Label(username);
        reasonLabel = new Label(reason);
        classroomLabel = new Label("Aula: " + entry.getLocation());

        // Stile migliorato per le etichette
        userLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        classroomLabel.setStyle("-fx-font-size: 14px;");
        reasonLabel.setStyle("-fx-font-size: 14px;");

        // Aggiunta delle etichette alla VBox
        this.getChildren().addAll(userLabel, classroomLabel, reasonLabel);
    }
}
