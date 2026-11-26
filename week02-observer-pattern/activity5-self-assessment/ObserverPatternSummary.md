# The Observer Pattern – One-Page Summary

## What it is
The Observer pattern defines a one-to-many relationship between objects: when one object (the **Subject**) changes state, all objects that depend on it (the **Observers**) are notified automatically. The subject keeps a list of observers and, on change, calls the `update()` method that every observer promises to provide through a shared interface.

## The parts
- **Subject interface** – `registerObserver()`, `removeObserver()`, `notifyObservers()`.
- **Observer interface** – `update(...)`.
- **Concrete Subject** – holds the real state and the list of observers (`WeatherData`).
- **Concrete Observers** – react to the change in their own way (`CurrentConditionsDisplay`, `StatisticsDisplay`, `ForecastDisplay`).

## Why it matters
The subject only knows that observers implement an interface, not what they are or what they do. This is **loose coupling**: you can add, remove or replace observers (such as the `TextLogger` in Activity 3) without modifying the subject. Compare the tightly-coupled system, where `DataModel` called each display directly and had to be edited for every change.

## Push vs. pull
- **Push:** the subject sends the new data in the notification (`update(temp, humidity, pressure)`).
- **Pull:** the subject only says "I changed" and observers call getters (the legacy `java.util.Observable`).

## The legacy API and its limits
`java.util.Observable`/`Observer` were deprecated in Java 9: `Observable` is a class (blocks inheritance), notification order is unspecified, `setChanged()` is easy to forget, data is pulled via untyped `Object`, and the API is not thread-safe.

## Alternatives in Java
1. **Custom Observer** – full control; best for plain Java projects.
2. **`java.util.Observer`** – deprecated; avoid.
3. **`PropertyChangeListener`** – JavaBeans/Swing; carries old and new value; `PropertyChangeSupport` manages the listeners.
4. **JavaFX Properties/Bindings** – modern and reactive; best for JavaFX.

## Things to remember
- Always unregister observers you no longer need (avoids memory leaks).
- Keep observers quick; a slow observer delays the others in a synchronous loop.
- Observer is the foundation of **event-driven programming**: events are published, and any interested listeners respond.
