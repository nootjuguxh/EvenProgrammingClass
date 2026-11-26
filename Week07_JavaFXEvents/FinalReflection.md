# Week 7 Final Reflection

## What I built this week
I completed five JavaFX event activities covering actions, mouse and keyboard events, event filters and handlers, drag-and-drop, and custom events.

## What was challenging
Learning the difference between capturing and bubbling propagation took practice. Drag-and-drop setup was also difficult because the source, target, transfer mode, and consumed event all have to work together.

## What I learned
I learned how `setOnAction`, mouse handlers, key handlers, filters, and custom events pass information between components. I also learned that event filters run during capture while handlers usually run during bubbling.

## How I would use this in a real project
I would use standard action and mouse events for ordinary controls, filters for shared validation or shortcuts, and drag-and-drop or custom events for specialized user interactions.

## Self-Assessment Questions

1. What is the difference between setOnAction and setOnMouseClicked?
   Answer: `setOnAction` represents the logical activation of a control, while `setOnMouseClicked` represents a mouse click event on a node.

2. Why does a KeyListener or setOnKeyPressed sometimes fail to trigger?
   Answer: Keyboard events are delivered to the focused node, so the target control must accept focus or the event must be handled at a node higher in the scene hierarchy.

3. What are the two phases of event propagation in JavaFX?
   Answer: Capturing and bubbling.

4. Which phase happens first: Capturing or Bubbling?
   Answer: Capturing happens first.

5. What is the difference between an Event Filter and an Event Handler?
   Answer: A filter is used during capture and can intercept an event before it reaches the target. A handler is used during bubbling and can process the event at the target or a parent.

6. What does the e.consume() method do?
   Answer: It marks the event as consumed so it does not continue through the normal event chain.

7. Which method must be called on a target node to accept a drag-and-drop operation?
   Answer: `setOnDragOver()` is used to accept the appropriate transfer mode with `event.acceptTransferModes(...)`.

8. What is the Dragboard used for?
   Answer: The Dragboard carries data between the drag source and drop target.

9. Why would you create a Custom Event instead of calling a method directly?
   Answer: A custom event allows loosely connected components to communicate through JavaFX's event system and can propagate through the scene hierarchy.

10. Which class must your custom event extend?
   Answer: `Event`.

