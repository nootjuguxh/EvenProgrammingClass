import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadingDemo extends Application {
    @Override
    public void start(Stage stage) {
        Label resultLabel = new Label("Press the button");
        Button btn = new Button("Run long calculation");

        btn.setOnAction(e -> {
            btn.setDisable(true);
            resultLabel.setText("Working...");
            new Thread(() -> {
                String result = performLongCalculation();      // background thread
                Platform.runLater(() -> {                      // back on the UI thread
                    resultLabel.setText(result);
                    btn.setDisable(false);
                });
            }).start();
        });

        VBox root = new VBox(10, btn, resultLabel);
        root.setStyle("-fx-padding: 20;");
        stage.setTitle("Threading Demo");
        stage.setScene(new Scene(root, 300, 150));
        stage.show();
    }

    private String performLongCalculation() {
        try { Thread.sleep(3000); } catch (InterruptedException ignored) { }
        return "Calculation done!";
    }

    public static void main(String[] args) { launch(args); }
}
