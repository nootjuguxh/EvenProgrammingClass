package com.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Phase 8 (LO3): main application. Loads the navigation shell and starts a
 * system "heartbeat" scheduler (the dashboard has its own 5-second refresh).
 */
public class App extends Application {

    private ScheduledExecutorService scheduler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        // 2. Set up the Scene and Stage
        primaryStage.setTitle("University Management System");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

        // 3. LO3: schedule a background task to run every 5 seconds
        scheduler = Executors.newScheduledThreadPool(1, runnable -> {
            Thread thread = new Thread(runnable, "heartbeat");
            thread.setDaemon(true);
            return thread;
        });

        Runnable heartbeat = () ->
                System.out.println("System Heartbeat: checking for updates at " + new java.util.Date());

        // after 0 seconds, then every 5 seconds
        scheduler.scheduleAtFixedRate(heartbeat, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        if (scheduler != null) {
            scheduler.shutdownNow(); // clean shutdown when the window closes
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
