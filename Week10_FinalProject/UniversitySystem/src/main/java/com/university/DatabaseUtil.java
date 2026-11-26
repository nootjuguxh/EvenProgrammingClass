package com.university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Phase 3 (LO1): the "bridge" between the Java application and MySQL.
 * The UI never talks to the database directly - it always goes through
 * background Tasks that use this class.
 */
public class DatabaseUtil {

    // "university_db" is the database created in Phase 1
    private static final String URL = env("DB_URL", "jdbc:mysql://localhost:3306/university_db");
    private static final String USER = env("DB_USER", "root");

    // Set DB_PASSWORD in the environment for the MySQL account.
    private static final String PASSWORD = env("DB_PASSWORD", "");

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    /** Returns a new connection. Throws SQLException if it fails. */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
