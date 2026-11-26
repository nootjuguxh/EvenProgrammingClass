import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomEventApp extends Application {
    @Override
    public void start(Stage stage) {
        TextField userField = new TextField("Alice");
        Button loginBtn = new Button("Login");
        Label status = new Label("Not logged in");
        VBox root = new VBox(10, userField, loginBtn, status);
        root.setStyle("-fx-padding: 20;");

        // Listener: any component can react to the custom event
        root.addEventHandler(LoginSuccessEvent.LOGIN_SUCCESS, event -> {
            status.setText("Custom Event Caught! Welcome " + event.getUsername());
            System.out.println("Logged in: " + event.getUsername());
        });

        // Source: just announces that a login happened
        loginBtn.setOnAction(e -> root.fireEvent(new LoginSuccessEvent(userField.getText())));

        stage.setScene(new Scene(root, 320, 180));
        stage.setTitle("Custom Event");
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
