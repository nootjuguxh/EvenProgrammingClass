import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/** Combines action, key, filter and custom events in one small notes app. */
public class EventNotesApp extends Application {

    static class NoteAddedEvent extends Event {
        static final EventType<NoteAddedEvent> NOTE_ADDED = new EventType<>(Event.ANY, "NOTE_ADDED");
        private final String text;
        NoteAddedEvent(String text) { super(NOTE_ADDED); this.text = text; }
        String getText() { return text; }
    }

    @Override
    public void start(Stage stage) {
        TextField input = new TextField();
        input.setPromptText("Type a note and press Enter or Add");
        Button add = new Button("Add");
        ListView<String> notes = new ListView<>();
        Label status = new Label("Ctrl+L clears all notes");

        VBox root = new VBox(10, new HBox(8, input, add), notes, status);
        root.setStyle("-fx-padding: 15;");

        // Custom event handler (decoupled from the button)
        root.addEventHandler(NoteAddedEvent.NOTE_ADDED, e -> {
            notes.getItems().add(e.getText());
            status.setText("Added: " + e.getText());
            input.clear();
        });

        // Action events fire the custom event
        add.setOnAction(e -> {
            if (!input.getText().isBlank()) root.fireEvent(new NoteAddedEvent(input.getText()));
        });
        input.setOnAction(e -> add.fire());

        // Event filter (capturing) catches a shortcut before any child sees it
        root.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.L) {
                notes.getItems().clear();
                status.setText("Cleared by Ctrl+L filter");
                e.consume();
            }
        });

        stage.setScene(new Scene(root, 400, 350));
        stage.setTitle("Event Notes App");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
