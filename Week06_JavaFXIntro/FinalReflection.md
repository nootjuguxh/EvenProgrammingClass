# Week 6 Final Reflection

## What I built this week
I completed five JavaFX introduction activities covering application structure, controls, layout panes, FXML, and basic styling.

## What was challenging
Moving from Swing to JavaFX took some adjustment because JavaFX uses `Stage`, `Scene`, `Application`, FXML, and properties. It also took practice to connect an FXML control to a controller and keep the UI thread safe.

## What I learned
I learned that JavaFX applications start from `Application.start()`, FXML separates the interface from Java logic, and layout panes manage component placement. I also learned that long operations should not run on the JavaFX Application Thread.

## How I would use this in a real project
I would use FXML and CSS for applications that need a clear separation between layout, behaviour, and presentation. JavaFX controls and layout panes would help produce a consistent desktop interface.

## Self-Assessment Questions

1. What are the three main classes that make up the JavaFX architecture hierarchy?
   Answer: `Application`, `Stage`, and `Scene`.

2. Which method must be overridden in a class that extends Application?
   Answer: `start(Stage primaryStage)`.

3. What is the difference between a Stage and a Scene?
   Answer: A Stage is the application window, while a Scene contains the nodes displayed inside that window.

4. Name four JavaFX Layout Panes and their Swing equivalents.
   Answer: `VBox` is similar to a vertical `BoxLayout`, `HBox` is similar to a horizontal `BoxLayout`, `BorderPane` is similar to `BorderLayout`, and `GridPane` is similar to `GridLayout`.

5. What is FXML, and why is it useful?
   Answer: FXML is an XML language used to describe a JavaFX interface. It separates layout from Java controller code.

6. How do you link a button in Scene Builder to a method in the Controller?
   Answer: Give the button an `onAction` value such as `#methodName` and declare the matching `@FXML` method in the controller.

7. What is the name of the JavaFX UI thread?
   Answer: The JavaFX Application Thread.

8. How do you update a label from a background thread safely?
   Answer: Use `Platform.runLater(() -> label.setText("value"))` or update a property through a Task callback.

9. What does the @FXML annotation do?
   Answer: It tells JavaFX to inject an FXML control or make a method available to the FXML file.

10. How do you apply CSS styling to a JavaFX component?
   Answer: Call `scene.getStylesheets().add("path/style.css")` or attach an inline stylesheet to the scene or application.

