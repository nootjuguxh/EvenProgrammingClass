package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Phase 5 (LO2 - Producer-Consumer): loads students on a BACKGROUND thread
 * so the UI never freezes while waiting for the database.
 */
public class LoadStudentsTask extends Task<ObservableList<Student>> {

    // Runs entirely on a background thread - never touch the UI here
    @Override
    protected ObservableList<Student> call() throws Exception {
        ObservableList<Student> students = FXCollections.observableArrayList();

        String sql = "SELECT student_id, registration_number, first_name, last_name "
                   + "FROM students ORDER BY registration_number";

        // try-with-resources closes the connection automatically
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("registration_number"),
                        rs.getString("first_name"),
                        rs.getString("last_name")));
            }
        }
        return students; // handed back to the UI thread via setOnSucceeded
    }
}
