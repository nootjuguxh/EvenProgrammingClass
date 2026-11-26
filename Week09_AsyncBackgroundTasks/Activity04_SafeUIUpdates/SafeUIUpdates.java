import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SafeUIUpdates extends Application {
    @Override
    public void start(Stage stage) {
        Label unsafeResult = new Label("Unsafe test result appears here");
        Label runLaterLabel = new Label("Method 1: Platform.runLater()");
        Label propertyLabel = new Label("Method 2: Task properties");

        Button unsafe = new Button("Try updating UI from a background thread (unsafe)");
        unsafe.setOnAction(e -> new Thread(() -> {
            try {
                unsafeResult.setText("This should not work");     // wrong thread!
            } catch (IllegalStateException ex) {
                Platform.runLater(() -> unsafeResult.setText("Caught: " + ex.getMessage()));
            }
        }).start());

        Button runLater = new Button("Method 1: Platform.runLater()");
        runLater.setOnAction(e -> {
            Task<Void> task = new Task<>() {
                @Override protected Void call() throws Exception {
                    Thread.sleep(1500);
                    String result = "Fetched data (via runLater)";
                    Platform.runLater(() -> runLaterLabel.setText(result));
                    return null;
                }
            };
            Thread t = new Thread(task); t.setDaemon(true); t.start();
        });

        Button property = new Button("Method 2: updateMessage() + binding (best practice)");
        property.setOnAction(e -> {
            Task<Void> task = new Task<>() {
                @Override protected Void call() throws Exception {
                    updateMessage("Starting download...");
                    Thread.sleep(1500);
                    updateMessage("Download complete!");
                    return null;
                }
            };
            propertyLabel.textProperty().bind(task.messageProperty());
            task.setOnSucceeded(ev -> propertyLabel.textProperty().unbind());
            Thread t = new Thread(task); t.setDaemon(true); t.start();
        });

        VBox root = new VBox(12, unsafe, unsafeResult, runLater, runLaterLabel, property, propertyLabel);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 480, 330));
        stage.setTitle("Safely Updating the UI");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
