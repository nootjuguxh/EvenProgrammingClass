package com.university;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * LO3 (Scheduled Tasks): a ScheduledExecutorService refreshes the dashboard
 * every 5 seconds. The query runs on the scheduler's background thread and the
 * labels are updated safely with Platform.runLater().
 */
public class DashboardController {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML private Label studentCountLabel;
    @FXML private Label programCountLabel;
    @FXML private Label lastUpdatedLabel;
    @FXML private Label statusLabel;

    private ScheduledExecutorService scheduler;

    @FXML
    public void initialize() {
        // Daemon thread so the scheduler never blocks the application from exiting
        scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "dashboard-refresh");
            thread.setDaemon(true);
            return thread;
        });

        // Run immediately (delay 0), then every 5 seconds
        scheduler.scheduleAtFixedRate(this::refresh, 0, 5, TimeUnit.SECONDS);
    }

    /** Runs on the scheduler thread - never touch UI controls directly here. */
    private void refresh() {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            int students = count(stmt, "SELECT COUNT(*) FROM students");
            int programs = count(stmt, "SELECT COUNT(*) FROM programs");
            String time = LocalTime.now().format(TIME_FORMAT);

            // Hand the results to the UI thread
            Platform.runLater(() -> {
                studentCountLabel.setText(String.valueOf(students));
                programCountLabel.setText(String.valueOf(programs));
                lastUpdatedLabel.setText("Last updated: " + time + " (auto-refresh every 5 seconds)");
                statusLabel.setText("Connected to database");
                statusLabel.setStyle("-fx-text-fill: green;");
            });
        } catch (Exception ex) {
            // Catch everything: an uncaught exception would cancel all future runs
            Platform.runLater(() -> {
                statusLabel.setText("Database unavailable: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: red;");
            });
        }
    }

    private int count(Statement stmt, String sql) throws SQLException {
        try (ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /** Called by MainController when the user leaves the dashboard. */
    public void stop() {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}
