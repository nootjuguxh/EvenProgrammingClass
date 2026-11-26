package com.university;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Phase 9: the navigation shell. Sidebar buttons fire events that swap the
 * view shown in the centre pane.
 */
public class MainController {

    // Connects to the StackPane in MainView.fxml
    @FXML private StackPane centerPane;

    // Remember the dashboard so its scheduler can be stopped when we navigate away
    private DashboardController currentDashboard;

    @FXML
    public void initialize() {
        showDashboard(); // start on the dashboard
    }

    // Triggered when the Dashboard button is clicked
    @FXML
    private void showDashboard() {
        stopDashboard();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
            Parent dashboardView = loader.load();
            currentDashboard = loader.getController();
            centerPane.getChildren().setAll(dashboardView);
        } catch (IOException e) {
            e.printStackTrace();
            centerPane.getChildren().setAll(new Label("Could not load the dashboard: " + e.getMessage()));
        }
    }

    // Triggered when the Students button is clicked
    @FXML
    private void showStudents() {
        stopDashboard();
        try {
            Parent studentView = FXMLLoader.load(getClass().getResource("StudentView.fxml"));
            centerPane.getChildren().setAll(studentView);
        } catch (IOException e) {
            e.printStackTrace();
            centerPane.getChildren().setAll(new Label("Could not load the student view: " + e.getMessage()));
        }
    }

    private void stopDashboard() {
        if (currentDashboard != null) {
            currentDashboard.stop();
            currentDashboard = null;
        }
    }
}
