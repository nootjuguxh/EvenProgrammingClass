# Week 9 Final Reflection

## What I built this week
I completed five JavaFX background-task activities. They demonstrated running long work in a background thread, reporting progress, updating controls safely, using `Task` and `Service`, and responding to success, failure, or cancellation.

## What was challenging
The main difficulty was remembering that JavaFX UI controls can only be changed safely on the Application Thread. I had to separate the slow code from the code that updates labels, buttons, and progress bars.

## What I learned
I learned that database operations and network work should not block the UI. I learned how `Task.call()` runs work in a background thread and how `setOnSucceeded`, `setOnFailed`, `updateProgress()`, and `updateMessage()` are used to report results.

## How I would use this in a real project
I would use background tasks when loading reports, saving records, uploading files, or downloading data. The UI could remain usable while the operation runs, and I could disable submit buttons and show progress without freezing the window.

## Self-Assessment Questions

1. What is the name of the thread that handles all UI updates in JavaFX?
   Answer: The JavaFX Application Thread.

2. What happens to the UI if you perform a 10-second network request on the Application Thread?
   Answer: The window freezes and cannot process events or repaint until the request finishes.

3. Name three examples of operations that should be moved to a background thread.
   Answer: Database queries, file I/O, and network requests.

4. What is the main difference between Task and Service?
   Answer: A Task represents one operation, while a Service manages and can restart that operation.

5. Which method is overridden to put your heavy code inside a Task?
   Answer: `call()`.

6. Why is it dangerous to call label.setText() from inside a background Task?
   Answer: JavaFX UI controls are not thread-safe and should be updated only on the JavaFX Application Thread.

7. What method is used to safely send a UI update from a background thread to the UI thread?
   Answer: `Platform.runLater()`, or JavaFX task progress and state callbacks where those are available.

8. How does updateProgress() automatically update the UI without Platform.runLater()?
   Answer: `Task` publishes its progress property on the JavaFX application thread, and controls bound to that property receive the update automatically.

9. How do you know when a Task has completed successfully so you can re-enable a button?
   Answer: Add a `setOnSucceeded` handler, then run the button update there.

10. If you don't know how long a task will take, what value should you set for the progress bar?
   Answer: `ProgressIndicator.INDETERMINATE_PROGRESS`, usually represented by `-1`.

