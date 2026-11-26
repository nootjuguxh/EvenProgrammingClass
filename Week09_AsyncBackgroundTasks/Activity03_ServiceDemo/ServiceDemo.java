import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServiceDemo extends Application {
    @Override
    public void start(Stage stage) {
        Label stateLabel = new Label();
        Label dataLabel = new Label("No data yet");
        Button refresh = new Button("Refresh");

        // A Service is reusable: each restart() builds a fresh Task
        Service<String> service = new Service<>() {
            private int count = 0;
            @Override protected Task<String> createTask() {
                final int run = ++count;
                return new Task<>() {
                    @Override protected String call() throws Exception {
                        Thread.sleep(3000);
                        return "Data Loaded (run #" + run + ")";
                    }
                };
            }
        };
        service.setExecutor(java.util.concurrent.Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r); t.setDaemon(true); return t;
        }));

        stateLabel.textProperty().bind(service.stateProperty().asString("State: %s"));
        refresh.disableProperty().bind(service.runningProperty());
        service.setOnSucceeded(e -> dataLabel.setText(service.getValue()));
        service.setOnFailed(e -> dataLabel.setText("Error: " + service.getException().getMessage()));

        refresh.setOnAction(e -> service.restart());

        VBox root = new VBox(12, refresh, stateLabel, dataLabel);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 320, 160));
        stage.setTitle("Reusable Service");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
