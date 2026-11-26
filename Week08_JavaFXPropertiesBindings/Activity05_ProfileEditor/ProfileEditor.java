import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/** The UI is bound to the Person model; listeners handle a side effect (logging). */
public class ProfileEditor extends Application {
    @Override
    public void start(Stage stage) {
        Person person = new Person();
        person.setName("Alice");
        person.setAge(20);

        TextField nameField = new TextField();
        TextField ageField = new TextField();
        Label summary = new Label();
        TextArea log = new TextArea();
        log.setEditable(false);
        log.setPrefRowCount(5);

        // Two-way: editing the field updates the model, and vice versa
        nameField.textProperty().bindBidirectional(person.nameProperty());
        ageField.textProperty().bindBidirectional(person.ageProperty(), new NumberStringConverter("0"));

        // One-way computed label
        summary.textProperty().bind(Bindings.concat(
                person.nameProperty(), " is ", person.ageProperty().asString(), " years old"));

        // Listeners for side effects
        person.nameProperty().addListener((o, oldV, newV) ->
                log.appendText("name: '" + oldV + "' -> '" + newV + "'\n"));
        person.ageProperty().addListener((o, oldV, newV) ->
                log.appendText("age: " + oldV + " -> " + newV + "\n"));

        Button birthday = new Button("Happy Birthday (+1 age)");
        birthday.setOnAction(e -> person.setAge(person.getAge() + 1)); // model changes, UI follows
        birthday.disableProperty().bind(person.nameProperty().isEmpty());

        VBox root = new VBox(8, new Label("Name"), nameField, new Label("Age"), ageField,
                birthday, summary, new Label("Change log"), log);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 360, 440));
        stage.setTitle("Profile Editor (Reactive UI)");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
