# Comparison of Event Notification Mechanisms in Java

| Feature | Custom Observer | java.util.Observer | PropertyChangeListener | JavaFX Bindings |
|---------|-----------------|--------------------|-----------------------|-----------------|
| **Push or Pull?** | Both possible | Pull | Push (carries old + new) | Push |
| **Is it deprecated?** | No | Yes (Java 9+) | No | No |
| **Requires inheritance?** | No (interface) | Yes (class) | No | No |
| **Thread-safe?** | Depends on you | No | Yes (with the right setup) | Yes |
| **Auto-generates boilerplate?** | No | No | Yes (via PropertyChangeSupport) | Yes |
| **Best for...** | Custom systems | Legacy code | JavaBeans / Swing | JavaFX apps |
| **Can have multiple listeners?** | Yes | Yes | Yes | Yes |
| **Can be typed to send any data?** | Yes | No (Object) | Yes (via generics not typical) | Yes |
| **Recommended today?** | Yes | No | Only for legacy | Yes |

## Which One Should You Use?

- For a **plain Java project with custom needs** → Custom Observer
- For a **JavaFX application** → JavaFX Bindings
- For **legacy code using JavaBeans** → PropertyChangeListener
- **Never** → java.util.Observer

## Notes on the table (accuracy caveats)

The table above follows the guide's template. A few cells are simplifications worth knowing:

- **PropertyChangeListener "Thread-safe? Yes"** – `PropertyChangeSupport` keeps its listener list in a thread-safe way, but your own property fields and the listener code are not automatically thread-safe.
- **PropertyChangeListener "Typed to send any data"** – `getOldValue()`/`getNewValue()` return `Object`, so values must be cast; it is not generic.
- **JavaFX "Thread-safe? Yes"** – JavaFX properties are *not* generally thread-safe; UI-bound properties must be changed on the JavaFX Application Thread.
- **Custom Observer "Both possible"** – push (send data in `update(...)`) or pull (observer calls getters on the subject).
