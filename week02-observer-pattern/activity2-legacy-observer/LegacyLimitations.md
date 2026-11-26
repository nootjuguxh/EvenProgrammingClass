# Limitations of java.util.Observer and java.util.Observable

## Limitation 1: Single Inheritance
Explain: Why is it a problem that Observable is a CLASS, not an INTERFACE?

Your answer: Java allows a class to extend only one parent class. Because `Observable` is a class, any subject that extends it can never extend anything else. For example, a `WeatherData` class that already extends `SensorBase` could not also extend `Observable`. Developers are forced to choose between their natural class hierarchy and observer behaviour, or to use awkward workarounds such as holding an internal `Observable` object (composition) — but even that is hard, because `setChanged()` is `protected`, so it cannot be called from outside a subclass. Had `Observable` been an interface, any class could implement it.

## Limitation 2: Cannot Change the Notification Order
Explain: In what order are observers notified? Can you control this?

Your answer: The order is unspecified and is not part of the API contract. In practice the OpenJDK implementation notifies observers in the *reverse* order of registration (last added is notified first), but this is an implementation detail that could change. There is no priority mechanism or ordering option, so you cannot control it. Code that depends on one observer running before another (for example, a validator before a logger) is fragile and may break.

## Limitation 3: setChanged() is Confusing
Explain: Why must you call setChanged() before notifyObservers()?

Your answer: `Observable` keeps an internal boolean "changed" flag. `notifyObservers()` only notifies if that flag is true, and it clears the flag afterwards. The idea was to let the subject batch several changes and notify once, or to skip notifications when nothing meaningful changed. In practice it is confusing and error-prone: if you forget `setChanged()`, the call to `notifyObservers()` does nothing and no exception or warning tells you why — observers just silently never fire. Also, `setChanged()` is `protected`, which further restricts how the class can be used.

## Limitation 4: Pull Model by Default
Explain: How does the observer get the data? Is this efficient?

Your answer: `notifyObservers()` with no argument sends no data. Each observer receives only the `Observable` reference and must cast it to the concrete type and call getters (`data.getTemperature()`) to pull the values it needs (as `LegacyDisplay` does with `instanceof` and a cast). This is less efficient and less safe: it needs extra method calls, runtime casts and `instanceof` checks (no compile-time type safety), and the data might change between the notification and the pull. The alternative, `notifyObservers(Object arg)`, pushes a single untyped `Object`, which also needs casting. A custom observer can push exactly the typed data it needs, as in Activity 1.

## Limitation 5: Deprecated Since Java 9
Explain: Why did Java deprecate these classes?

Your answer: They were deprecated in Java 9 because the design was judged flawed and not worth extending: `Observable` is a class (not an interface), notification order is unspecified, the `setChanged()` mechanism is easy to misuse, the API passes untyped `Object` arguments with no type safety, and it does not support rich event models or provide sufficient thread-safety guarantees (the Observer pattern is better served by `java.beans.PropertyChangeListener`, the `java.util.concurrent.Flow` reactive-streams API, or a custom/typed implementation).

Hint: The reasons include:
- Observable is a class, so you cannot extend any other class
- Observers are notified in an unspecified order
- setChanged() is easy to forget — observers silently don't get notified
- Observers must pull data from the Observable (extra method calls)
- The API is not thread-safe and not extensible

---

# Activity 2 – Self-Check

**1. Why can't you extend another class if you already extend Observable?**
Because Java supports single class inheritance only. Once `LegacyWeatherData extends Observable`, its one `extends` slot is used up, so it cannot extend any other class.

**2. What is the difference between the push model and the pull model?**
- **Push model:** the subject sends the changed data directly to the observers in the notification (e.g. `update(float temp, float humidity, float pressure)` in Activity 1). Observers get everything immediately but may receive data they don't need.
- **Pull model:** the subject only signals "something changed" and observers call getters on the subject to fetch the data they care about (e.g. `LegacyDisplay` calling `data.getTemperature()`). Observers choose what they read, but it takes extra calls and a reference back to the subject.

**3. Why did Java deprecate these classes?**
Because of the design problems listed above: class-based (single inheritance), unspecified notification order, the easy-to-forget `setChanged()`, the untyped pull-oriented API, and no thread-safety/extensibility. Better alternatives (custom typed observers, `PropertyChangeListener`, `Flow`, JavaFX properties) exist.
