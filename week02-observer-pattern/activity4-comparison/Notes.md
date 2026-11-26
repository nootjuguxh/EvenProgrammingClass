# Activity 4 – Self-Check Notes

**1. Which mechanism carries both the old and new value of a property?**
`PropertyChangeListener`. The `PropertyChangeEvent` provides `getPropertyName()`, `getOldValue()` and `getNewValue()`.

**2. Which mechanism requires a helper class to manage the listener list?**
`PropertyChangeListener`, via `PropertyChangeSupport`. It stores the listeners and fires events so you don't write that boilerplate yourself (compare with the manual `ArrayList` in Activity 1's `WeatherData`).

**3. Which two mechanisms are recommended for modern Java?**
Custom Observer (for plain Java with custom needs) and JavaFX Properties/Bindings (for JavaFX apps).

**4. Which mechanism is deprecated and should be avoided?**
`java.util.Observer` / `java.util.Observable` (deprecated since Java 9).

## Demo output (PropertyChangeDemo)
```
Listener A: name changed from null to Alice
Listener B: noticed name is now Alice
Listener A: name changed from Alice to Bob
Listener B: noticed name is now Bob
```
Both listeners are notified for every change, in registration order, and the first event shows `oldValue = null` because `name` had not been set yet.
