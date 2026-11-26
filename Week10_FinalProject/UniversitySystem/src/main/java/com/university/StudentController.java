package com.university;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Phase 7 (LO2 & LO4): handles UI events. Every database call runs inside a
 * Task so the UI thread is never blocked.
 */
public class StudentController {

    // @FXML connects these fields to the fx:id values in StudentView.fxml
    @FXML private TextField regField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colReg;
    @FXML private TableColumn<Student, String> colFirst;
    @FXML private TableColumn<Student, String> colLast;
    @FXML private Label statusLabel;
    @FXML private Button saveButton;

    // The list the TableView displays
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    // True while a save is running (also disables the button via binding)
    private final BooleanProperty saving = new SimpleBooleanProperty(false);

    // Called automatically when the FXML finishes loading
    @FXML
    public void initialize() {
        // 1. Connect the TableColumns to the Student properties (Week 8)
        colReg.setCellValueFactory(cell -> cell.getValue().regNumberProperty());
        colFirst.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
        colLast.setCellValueFactory(cell -> cell.getValue().lastNameProperty());

        // 2. The TableView displays our observable list
        studentTable.setItems(studentList);

        // 3. Disable Save while any field is empty or a save is in progress (Week 8 binding)
        saveButton.disableProperty().bind(
                regField.textProperty().isEmpty()
                        .or(firstNameField.textProperty().isEmpty())
                        .or(lastNameField.textProperty().isEmpty())
                        .or(saving));

        // 4. Load the initial data from the database
        loadStudents();
    }

    // Triggered when the user clicks "Save Student"
    @FXML
    private void handleSaveStudent() {
        String reg = regField.getText().trim();
        String fName = firstNameField.getText().trim();
        String lName = lastNameField.getText().trim();

        saving.set(true);
        statusLabel.setText("Saving...");
        statusLabel.setStyle("-fx-text-fill: blue;");

        // Background Task (Producer-Consumer pattern)
        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "INSERT INTO students (registration_number, first_name, last_name, email) "
                           + "VALUES (?, ?, ?, ?)";
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, reg);
                    stmt.setString(2, fName);
                    stmt.setString(3, lName);
                    stmt.setString(4, reg + "@university.edu"); // generated email
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        // These handlers run back on the JavaFX Application Thread
        saveTask.setOnSucceeded(e -> {
            saving.set(false);
            showStatus("Student saved successfully!", false);
            regField.clear();
            firstNameField.clear();
            lastNameField.clear();
            loadStudents(); // refresh the table
        });

        saveTask.setOnFailed(e -> {
            saving.set(false);
            Throwable error = saveTask.getException();
            if (error instanceof SQLIntegrityConstraintViolationException) {
                showStatus("A student with registration number \"" + reg + "\" already exists.", true);
            } else {
                showStatus("Could not save student: " + error.getMessage(), true);
            }
        });

        runInBackground(saveTask);
    }

    private void loadStudents() {
        LoadStudentsTask task = new LoadStudentsTask();

        task.setOnSucceeded(e -> {
            studentList.setAll(task.getValue());
            showStatus("Loaded " + studentList.size() + " students from database.", false);
        });

        task.setOnFailed(e ->
                showStatus("Could not load students: " + task.getException().getMessage(), true));

        runInBackground(task);
    }

    private void showStatus(String message, boolean error) {
        statusLabel.setText(message);
        statusLabel.setStyle(error ? "-fx-text-fill: red;" : "-fx-text-fill: blue;");
    }

    /** Daemon threads never keep the JVM alive after the window closes. */
    private void runInBackground(Task<?> task) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
