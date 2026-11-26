import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FilterVsHandler extends Application {
    @Override
    public void start(Stage stage) {
        TextArea log = new TextArea();
        log.setEditable(false);
        CheckBox consumeInFilter = new CheckBox("Consume event in VBox filter");
        Button target = new Button("Click me (target)");

        VBox box = new VBox(10, target);
        BorderPane root = new BorderPane(box);
        root.setTop(consumeInFilter);
        root.setBottom(log);

        // Capturing phase: root -> target
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> log.appendText("1. FILTER   BorderPane (capturing)\n"));
        box.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            log.appendText("2. FILTER   VBox (capturing)\n");
            if (consumeInFilter.isSelected()) {
                e.consume();
                log.appendText("   -> consumed in filter: never reaches target\n");
            }
        });

        // Target + bubbling phase: target -> root
        target.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> log.appendText("3. HANDLER  Button (target)\n"));
        box.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> log.appendText("4. HANDLER  VBox (bubbling)\n"));
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> log.appendText("5. HANDLER  BorderPane (bubbling)\n"));

        stage.setScene(new Scene(root, 450, 350));
        stage.setTitle("Event Filters vs Handlers");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
