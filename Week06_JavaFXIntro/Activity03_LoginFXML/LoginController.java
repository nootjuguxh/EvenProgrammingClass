import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameField;   // name must match fx:id
    @FXML private Button loginButton;
    @FXML private Label messageLabel;

    @FXML
    private void handleLogin(ActionEvent event) {   // name must match onAction
        System.out.println("Logging in: " + usernameField.getText());
        messageLabel.setText("Welcome, " + usernameField.getText() + "!");
    }
}
