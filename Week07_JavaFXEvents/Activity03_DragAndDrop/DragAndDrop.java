import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DragAndDrop extends Application {
    @Override
    public void start(Stage stage) {
        Label source = new Label("Drag Me");
        source.setStyle("-fx-border-color: black; -fx-padding: 15;");

        Label target = new Label("Drop here");
        target.setMinSize(150, 100);
        target.setAlignment(Pos.CENTER);
        target.setStyle("-fx-border-color: gray; -fx-border-style: dashed;");

        // 1. Source: start the drag
        source.setOnDragDetected(e -> {
            Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(source.getText());
            db.setContent(content);
            e.consume();
        });

        // 2. Target: accept the drag
        target.setOnDragOver(e -> {
            if (e.getGestureSource() != target && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        // Visual feedback
        target.setOnDragEntered(e -> target.setStyle("-fx-border-color: green; -fx-background-color: #d5f5e3;"));
        target.setOnDragExited(e -> target.setStyle("-fx-border-color: gray; -fx-border-style: dashed;"));

        // 3. Target: handle the drop
        target.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                target.setText("Dropped: " + db.getString());
                success = true;
            }
            e.setDropCompleted(success);
            e.consume();
        });

        // 4. Source: finish
        source.setOnDragDone(e -> {
            if (e.getTransferMode() == TransferMode.MOVE) source.setText("(moved)");
            e.consume();
        });

        HBox root = new HBox(40, source, target);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root, 450, 200));
        stage.setTitle("Drag and Drop");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
