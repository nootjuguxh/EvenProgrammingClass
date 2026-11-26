import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class EventTypes extends Application {
    @Override
    public void start(Stage stage) {
        Label info = new Label("Interact with the window");
        Button btn = new Button("Submit");
        Pane canvas = new Pane();
        canvas.setPrefSize(400, 150);
        canvas.setStyle("-fx-background-color: #ecf0f1;");

        // Action event
        btn.setOnAction(e -> info.setText("ActionEvent: button clicked"));

        // Mouse events
        canvas.setOnMouseMoved(e -> info.setText("Mouse at: " + (int) e.getX() + ", " + (int) e.getY()));
        canvas.setOnMouseClicked(e -> info.setText("MouseClicked at " + (int) e.getX() + ", " + (int) e.getY()));
        canvas.setOnMouseEntered(e -> canvas.setStyle("-fx-background-color: #d5f5e3;"));
        canvas.setOnMouseExited(e -> canvas.setStyle("-fx-background-color: #ecf0f1;"));

        // Key events (node needs focus)
        VBox root = new VBox(10, btn, canvas, info);
        root.setFocusTraversable(true);
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) info.setText("Up arrow pressed!");
            else if (e.getCode() == KeyCode.DOWN) info.setText("Down arrow pressed!");
        });

        stage.setScene(new Scene(root, 420, 260));
        stage.setTitle("Action, Mouse and Key Events");
        stage.show();
        root.requestFocus();
    }
    public static void main(String[] args) { launch(args); }
}
