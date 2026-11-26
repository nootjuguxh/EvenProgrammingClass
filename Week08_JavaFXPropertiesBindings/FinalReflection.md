# Week 8 Final Reflection

## What I built this week
I completed five properties-and-bindings exercises. They demonstrated observable model properties, listeners, unidirectional and bidirectional binding, formatted bindings, and automatic button state.

## What was challenging
The most difficult part was understanding which object owned the source value and which object was receiving the bound value. It also took practice to choose a `ChangeListener` instead of an `InvalidationListener` when I needed the old and new values.

## What I learned
I learned that JavaFX properties notify observers when their values change, bindings keep UI values synchronized, and listeners allow application code to react to state changes. This made it possible to update controls without manually assigning values at several points.

## How I would use this in a real project
I would use properties and bindings for form validation, live previews, totals, sliders, and any UI fields that must stay synchronized. `ChangeListener` would be useful for state that requires extra application logic.

## Self-Assessment Questions

1. Why do we use StringProperty instead of String in a JavaFX model?
   Answer: `StringProperty` is observable and can notify listeners or support bindings when its value changes.

2. What is the difference between a ChangeListener and an InvalidationListener?
   Answer: A ChangeListener receives the old and new values. An InvalidationListener is notified that a value may have changed but does not receive the old and new values.

3. Do listeners fire if you set a property to the same value it already holds?
   Answer: No. A change listener fires when the value actually changes.

4. What happens if you try to call .setText() on a Label that is bound using .bind()?
   Answer: The call is rejected because a bound property cannot be set directly.

5. What is the difference between bind() and bindBidirectional()?
   Answer: `bind()` creates a one-way binding from source to target. `bindBidirectional()` keeps two properties synchronized in both directions.

6. How do you bind a DoubleProperty (from a Slider) to a StringProperty (from a TextField)?
   Answer: Use a binding that converts the numeric value to a string, such as `Bindings.format("%.2f", slider.valueProperty())`, or a bidirectional converter.

7. How do you disable a button automatically when a text field is empty using bindings?
   Answer: Bind the button's `disableProperty()` to an expression such as `textField.textProperty().isEmpty()`.

8. Which class provides static methods for complex bindings like .concat()?
   Answer: `Bindings`.

9. If you bind Label A to TextField B, which component acts as the source and which acts as the target?
   Answer: TextField B is the source and Label A is the target.

10. How do you cancel a binding so you can set the value manually again?
   Answer: Call `unbind()` on the target property.

