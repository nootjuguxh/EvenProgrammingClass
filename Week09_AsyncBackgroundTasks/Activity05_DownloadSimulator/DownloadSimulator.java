import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/** Indeterminate "connecting" phase, determinate download phase, cancel, and completion handlers. */
public class DownloadSimulator extends Application {
    @Override
    public void start(Stage stage) {
        ProgressBar bar = new ProgressBar(0);
        bar.setPrefWidth(320);
        Label status = new Label("Ready");
        Button start = new Button("Start download");
        Button cancel = new Button("Cancel");
        cancel.setDisable(true);

        start.setOnAction(e -> {
            Task<String> task = new Task<>() {
                @Override protected String call() throws Exception {
                    updateMessage("Connecting...");
                    updateProgress(-1, 1);                     // indeterminate
                    Thread.sleep(2000);
                    for (int mb = 0; mb <= 100; mb += 5) {
                        if (isCancelled()) break;
                        updateProgress(mb, 100);
                        updateMessage("Downloaded " + mb + " / 100 MB");
                        try { Thread.sleep(250); }
                        catch (InterruptedException ex) { if (isCancelled()) break; }
                    }
                    return "file.zip";
                }
            };
            bar.progressProperty().bind(task.progressProperty());
            status.textProperty().bind(task.messageProperty());
            start.setDisable(true);
            cancel.setDisable(false);
            cancel.setOnAction(ev -> task.cancel());

            Runnable finish = () -> {
                bar.progressProperty().unbind();
                status.textProperty().unbind();
                start.setDisable(false);
                cancel.setDisable(true);
            };
            task.setOnSucceeded(ev -> { finish.run(); bar.setProgress(1); status.setText("Finished: " + task.getValue()); });
            task.setOnFailed(ev -> { finish.run(); status.setText("Error: " + task.getException().getMessage()); });
            task.setOnCancelled(ev -> { finish.run(); bar.setProgress(0); status.setText("Cancelled by user"); });

            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        });

        VBox root = new VBox(12, new HBox(10, start, cancel), bar, status);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 380, 160));
        stage.setTitle("Download Simulator");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
