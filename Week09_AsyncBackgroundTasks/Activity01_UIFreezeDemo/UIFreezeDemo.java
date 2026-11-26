import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIFreezeDemo extends Application {
    @Override
    public void start(Stage stage) {
        ProgressIndicator spinner = new ProgressIndicator();   // animates only if the UI thread is free
        Label status = new Label("Idle");
        Button bad = new Button("Blocking: sleep 5s on the UI thread (freezes!)");
        Button good = new Button("Good: sleep 5s in a background Task");

        bad.setOnAction(e -> {
            status.setText("Blocking...");
            try { Thread.sleep(5000); } catch (InterruptedException ignored) { }
            status.setText("Done (but the spinner froze and the window did not respond)");
        });

        good.setOnAction(e -> {
            status.setText("Working in background...");
            good.setDisable(true);
            Task<String> task = new Task<>() {
                @Override protected String call() throws Exception {
                    Thread.sleep(5000);
                    return "Task Complete! The spinner kept spinning.";
                }
            };
            task.setOnSucceeded(ev -> { status.setText(task.getValue()); good.setDisable(false); });
            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        });

        VBox root = new VBox(12, spinner, bad, good, status);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 440, 240));
        stage.setTitle("Blocking the UI Thread");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
