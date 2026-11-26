import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class BindingsDemo extends Application {
    @Override
    public void start(Stage stage) {
        // Unidirectional: label follows the text field
        TextField nameField = new TextField();
        Label greetingLabel = new Label();
        greetingLabel.textProperty().bind(Bindings.concat("Hello, ", nameField.textProperty()));

        // Bidirectional: slider <-> text field (needs a converter: Double <-> String)
        Slider volumeSlider = new Slider(0, 100, 50);
        TextField volumeField = new TextField();
        volumeField.textProperty().bindBidirectional(volumeSlider.valueProperty(), new NumberStringConverter("0"));

        // High-level binding with format
        Label volumeLabel = new Label();
        volumeLabel.textProperty().bind(Bindings.format("Volume: %.1f", volumeSlider.valueProperty()));

        // Bound rule + unbind
        Button unbindBtn = new Button("Unbind greeting and set manually");
        unbindBtn.setOnAction(e -> {
            greetingLabel.textProperty().unbind();
            greetingLabel.setText("Manually set after unbind()");
        });

        VBox root = new VBox(10, new Label("Name:"), nameField, greetingLabel,
                new Separator(), volumeSlider, volumeField, volumeLabel, unbindBtn);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 360, 380));
        stage.setTitle("Unidirectional and Bidirectional Bindings");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
