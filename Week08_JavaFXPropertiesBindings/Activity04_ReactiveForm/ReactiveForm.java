import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReactiveForm extends Application {
    private static final int MAX = 100;

    @Override
    public void start(Stage stage) {
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        Button submit = new Button("Submit");
        Label result = new Label();

        // Fluent API: disabled if name empty OR email empty OR email has no "@"
        BooleanBinding noAt = Bindings.createBooleanBinding(
                () -> !emailField.getText().contains("@"), emailField.textProperty());
        submit.disableProperty().bind(
                nameField.textProperty().isEmpty()
                        .or(emailField.textProperty().isEmpty())
                        .or(noAt));

        // Live character counter
        TextArea bio = new TextArea();
        bio.setPromptText("Short bio (max " + MAX + ")");
        bio.setPrefRowCount(4);
        Label counter = new Label();
        counter.textProperty().bind(
                bio.textProperty().length().negate().add(MAX).asString().concat(" characters remaining"));
        // Turn red when over the limit
        counter.styleProperty().bind(Bindings.when(bio.textProperty().length().greaterThan(MAX))
                .then("-fx-text-fill: red;").otherwise("-fx-text-fill: black;"));

        submit.setOnAction(e -> result.setText("Submitted: " + nameField.getText()));

        VBox root = new VBox(10, nameField, emailField, bio, counter, submit, result);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 360, 360));
        stage.setTitle("Reactive Form (no event handlers for validation)");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
