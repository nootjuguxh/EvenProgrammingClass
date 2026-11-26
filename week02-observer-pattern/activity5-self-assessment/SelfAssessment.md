# Activity 5 – Final Self-Assessment

## Learning Outcome 1: Design and Implement the Observer Pattern

**1. UML diagram of the Observer pattern**

```
            <<interface>>                         <<interface>>
              Subject                                Observer
        ---------------------                  ---------------------
        + registerObserver(o)  o------------->  + update(...)
        + removeObserver(o)    (observers 0..*)
        + notifyObservers()                              ^
                ^                                        | implements
                | implements                             |
        ------------------                     ----------+-----------
        |  WeatherData   |                     |                    |
        |(ConcreteSubject)|   notifies  ---->  CurrentConditions  StatisticsDisplay  ForecastDisplay
        ------------------                     (ConcreteObservers)
        - observers: List
        - temperature, humidity, pressure
        + setMeasurements(...)
```

- **Subject** – interface with `registerObserver`, `removeObserver`, `notifyObservers`.
- **Observer** – interface with `update(...)`.
- **ConcreteSubject** – `WeatherData` (holds state and the observer list).
- **ConcreteObservers** – `CurrentConditionsDisplay`, `StatisticsDisplay`, `ForecastDisplay`.

**2. Subject interface from memory**

```java
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
```

**3. Why does the Subject need a list of observers?**
It must know who to notify. When its state changes, it loops through the list and calls each observer's `update()`. The list also lets observers be added and removed at runtime, so the subject never needs to know about specific observer classes.

## Learning Outcome 2: Evaluate Legacy java.util.Observer

**4. Three problems with java.util.Observable**
1. It is a class, so subjects cannot extend any other class (single inheritance).
2. Notification order is unspecified and cannot be controlled.
3. `setChanged()` is easy to forget, and then observers silently aren't notified. (Others: pull model with untyped `Object`, not thread-safe, not extensible.)

**5. Why was this API deprecated?**
Because of the design flaws above. It was deprecated in Java 9 since it doesn't provide a rich event model, lacks type safety and thread-safety guarantees, and better alternatives exist (custom typed observers, `PropertyChangeListener`, `java.util.concurrent.Flow`, JavaFX properties).

**6. What is the "pull model" and why is it harder to use?**
In the pull model the subject only announces that something changed; each observer must call getters on the subject to fetch the data. It is harder because observers need a reference to the concrete subject, require casts/`instanceof` checks, make extra method calls, and the data can change between notification and retrieval.

## Learning Outcome 3: Create a Custom Event Notification System

**7. What methods must a custom Subject interface provide?**
A way to add an observer (`registerObserver`/`addObserver`), a way to remove one (`removeObserver`), and a way to notify all of them (`notifyObservers`).

**8. How does the Subject notify all observers?**
It loops through its list of registered observers and calls each one's `update()` (or `onDataChanged()`) method, passing the new data (push model) — e.g. `for (Observer o : observers) o.update(temperature, humidity, pressure);`.

**9. How do you prevent a memory leak when observers are no longer needed?**
Unregister them with `removeObserver()` when they are no longer needed. Otherwise the subject's list keeps a strong reference to each observer, preventing garbage collection (the "lapsed listener" problem). Alternatives: use weak references in the list, or tie registration/removal to the observer's lifecycle (e.g. close/dispose methods).

## Learning Outcome 4: Compare Different Mechanisms

**10. Four event notification mechanisms in Java**
1. Custom Observer (own interfaces)
2. `java.util.Observer` / `Observable` (deprecated)
3. `PropertyChangeListener` / `PropertyChangeSupport` (`java.beans`)
4. JavaFX Properties and Bindings

**11. Which one is best for JavaFX?**
JavaFX Properties/Bindings.

**12. Which one is deprecated?**
`java.util.Observer` / `java.util.Observable`.

## Score Yourself

Answer key check against the work above: 12 / 12 answered.

| Score | Meaning |
|-------|---------|
| 10–12 correct | Excellent! You have mastered the Observer pattern. |

> Submission check: re-read each answer and rewrite it in your own words. Score yourself honestly and re-do any matching activity that you do not understand.
