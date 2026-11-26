import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LayoutPanes extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        HBox top = new HBox(10, new Label("My Dashboard"), new Button("Profile"));
        top.setPadding(new Insets(10));

        VBox left = new VBox(8, new Button("Home"), new Button("Stats"), new Button("Settings"));
        left.setPadding(new Insets(10));

        GridPane center = new GridPane();
        center.setHgap(10); center.setVgap(10); center.setPadding(new Insets(10));
        center.add(new Label("Username:"), 0, 0);
        center.add(new TextField(), 1, 0);
        center.add(new Label("Email:"), 0, 1);
        center.add(new TextField(), 1, 1);

        root.setTop(top);
        root.setLeft(left);
        root.setCenter(center);
        root.setBottom(new Label("  BorderPane + HBox + VBox + GridPane"));

        stage.setTitle("Layout Panes");
        stage.setScene(new Scene(root, 520, 300));
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}
