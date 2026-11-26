import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskProgress extends Application {
    @Override
    public void start(Stage stage) {
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        Label statusLabel = new Label("Ready");
        Button start = new Button("Start");

        start.setOnAction(e -> {
            start.setDisable(true);
            Task<Void> task = new Task<>() {
                @Override protected Void call() throws Exception {
                    for (int i = 0; i <= 100; i += 10) {
                        Thread.sleep(500);
                        updateProgress(i, 100);
                        updateMessage("Processing... " + i + "%");
                    }
                    return null;
                }
            };
            // Bind UI to the task's properties (thread-safe)
            progressBar.progressProperty().bind(task.progressProperty());
            statusLabel.textProperty().bind(task.messageProperty());

            task.setOnSucceeded(ev -> {
                statusLabel.textProperty().unbind();
                statusLabel.setText("Task Finished!");
                start.setDisable(false);
            });
            task.setOnFailed(ev -> {
                statusLabel.textProperty().unbind();
                statusLabel.setText("Error: " + task.getException().getMessage());
                start.setDisable(false);
            });
            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        });

        VBox root = new VBox(12, start, progressBar, statusLabel);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 360, 160));
        stage.setTitle("Task with Progress");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
