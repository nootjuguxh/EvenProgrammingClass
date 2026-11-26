import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloJavaFX extends Application {
    @Override
    public void start(Stage stage) {
        Button btn = new Button("Click Me");
        btn.setOnAction(e -> btn.setText("Clicked!"));
        StackPane root = new StackPane(btn);
        stage.setTitle("JavaFX Hello World");
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
