import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StyledApp extends Application {
    @Override
    public void start(Stage stage) {
        Label title = new Label("Styled with CSS");
        title.getStyleClass().add("title-label");

        Button cssButton = new Button("Styled via styles.css");
        cssButton.getStyleClass().add("my-button");

        Button inlineButton = new Button("Styled via setStyle()");
        inlineButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        VBox root = new VBox(12, title, cssButton, inlineButton);
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, 320, 200);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("CSS Styling");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
