package it.unical.classroommanager_ui.view;

import com.calendarfx.model.Entry;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomEntryDetailsView extends VBox {

    private Label userLabel;
    private Label classroomLabel;
    private Label reasonLabel;

    public CustomEntryDetailsView(Entry<?> entry) {
        // Inizializzazione base della VBox
        super();
        this.setSpacing(15); // Spaziatura tra gli elementi
        this.setPadding(new Insets(20)); // Padding interno alla VBox
        this.setStyle("-fx-background-color: #ffffff; " +  // Sfondo bianco
                "-fx-border-color: #8B0000; " +     // Bordo rosso scuro
                "-fx-border-radius: 10px; " +       // Angoli arrotondati
                "-fx-background-radius: 10px; " +   // Angoli arrotondati sfondo
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);"); // Effetto ombra

        // Creazione delle etichette con testo personalizzato
        String username = entry.getTitle().split(":")[0];
        String reason = entry.getTitle().split(":")[1].split("\n")[0];

        userLabel = new Label("Utente: " + username);
        classroomLabel = new Label("Aula: " + entry.getLocation());
        reasonLabel = new Label("Motivazione: " + reason);

        // Creazione di HBox con icone e testo
        HBox userBox = createCustomRow(userLabel, "user-icon.png");
        HBox classroomBox = createCustomRow(classroomLabel, "classroom-icon.png");
        HBox reasonBox = createCustomRow(reasonLabel, "reason-icon.png");

        // Aggiunta delle etichette alla VBox
        this.getChildren().addAll(userBox, classroomBox, reasonBox);
    }

    /**
     * Crea una riga (HBox) con un'icona e un'etichetta.
     *
     * @param label La Label da includere nella riga.
     * @param iconPath Il percorso dell'icona.
     * @return Un nodo HBox contenente l'icona e il testo.
     */
    private HBox createCustomRow(Label label, String iconPath) {
        HBox container = new HBox();
        container.setSpacing(10);
        container.setPadding(new Insets(5));

        // Stile dell'etichetta
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #8B0000;");

        // Aggiunta dell'icona e del testo al contenitore
        container.getChildren().add(label);
        return container;
    }
}
