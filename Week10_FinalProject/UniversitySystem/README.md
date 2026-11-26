# University Management System

A JavaFX desktop application for managing student records and university programs. The project uses MySQL for persistence and Java background tasks for database operations.

## Features

- Navigate between the dashboard and student views
- Add students to the database
- Display students in a table
- Refresh dashboard student and program counts
- Run database operations away from the JavaFX Application Thread

## Technology

- Java 25 (latest LTS)
- JavaFX 17
- Maven
- MySQL
- JDBC

## Database setup

1. Start MySQL.
2. Run `database/schema.sql` to create the database and tables.
3. Optionally run `database/sample_data.sql` to add sample records.
4. Configure the database connection with environment variables:

```text
DB_URL=jdbc:mysql://localhost:3306/university_db
DB_USER=root
DB_PASSWORD=
```

The default password in `DatabaseUtil` must be replaced or supplied through `DB_PASSWORD`.

## Run

From the project directory:

```bash
mvn javafx:run
```

## Project structure

- `src/main/java/com/university/` — Java and JavaFX application classes
- `src/main/resources/com/university/` — FXML views
- `database/` — database schema and sample data
- `pom.xml` — Maven dependencies and JavaFX configuration
