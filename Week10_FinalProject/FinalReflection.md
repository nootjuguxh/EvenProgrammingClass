# Week 10 Final Reflection: Capstone Project (University Management System)

**Name:** Mohamednoor Dhuh  **Student ID:** 23ZAD108490  **Date:** 2026-11-20

## 1. What I built
A JavaFX desktop application backed by MySQL, with a sidebar navigation shell, a Students view (add and list students) and a live Dashboard that auto-refreshes every 5 seconds.

## 2. How the project meets the learning outcomes
- **LO1, EDA principles:** the UI only fires events; all database work lives in `DatabaseUtil` and Task classes, so the UI never talks to the database directly.
- **LO2, Producer-Consumer:** `LoadStudentsTask` and the save `Task` run on background threads and hand results back through `setOnSucceeded` / `setOnFailed`, so the UI never freezes.
- **LO3, Scheduled tasks:** a `ScheduledExecutorService` refreshes the dashboard counts every 5 seconds and updates labels with `Platform.runLater()`.
- **LO4, Event communication:** buttons use `onAction` handlers, the table is bound to `Student` properties, and the Save button is bound to the text fields with `disableProperty().bind(...)`.

## 3. Key things I learned
- Why the JavaFX Application Thread must never run database or other slow work.
- How `Task` separates background work from UI updates.
- Why UI updates from a scheduler thread need `Platform.runLater()`.
- How properties and bindings remove the need for manual UI synchronisation.
- Why queries use `PreparedStatement` (prevents SQL injection).

## 4. Challenges I faced and how I solved them
My first challenge was getting JavaFX, Maven, and Java 17 working together. I checked the project configuration and used the JavaFX Maven plugin so the application could start with `mvn javafx:run`.

I also had to be careful about database setup. I separated database connection code from the UI and added environment variables for the URL, username, and password, which made it easier to run the project without changing Java code on a different machine.

Keeping database work off the JavaFX Application Thread was another challenge. I moved loading and saving operations into `Task` objects and used `setOnSucceeded` and `setOnFailed` for the UI updates.

## 5. What I would improve
I would add editing and deleting students, program assignment, student search, and validation with clearer error messages. I would also use a database connection pool and add automated tests for database operations.

## 6. Confidence rating (1-5)
Event-driven design: 4  Tasks and threading: 4  Scheduling: 4  Bindings: 4  JDBC: 4
