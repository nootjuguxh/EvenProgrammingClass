# Activity 1 – Self-Check Notes

**1. What is the role of the Subject interface?**
It defines the contract every "publisher" must follow: `registerObserver()`, `removeObserver()` and `notifyObservers()`. Code that depends on `Subject` doesn't care which concrete class it is talking to, so the notification mechanism stays consistent and can be reused for different kinds of data sources.

**2. What happens if you call `notifyObservers()` without registering any observers?**
Nothing visible. The `for` loop iterates over an empty list, so zero `update()` calls are made. No exception is thrown.

**3. What is the difference between `registerObserver()` and `removeObserver()`?**
`registerObserver()` adds an observer to the subject's list, so it will receive future notifications. `removeObserver()` takes it off the list, so it stops receiving them (as shown when `ForecastDisplay` was removed and no longer printed a forecast).

**4. Why is the observers list stored as an instance variable?**
The list must outlive any single method call. Observers are registered in one call (`registerObserver`) and used later in another (`notifyObservers`), so the list has to persist for the lifetime of the `WeatherData` object. Each subject also needs its own separate list.

---

## Observation from the run
- `ForecastDisplay` starts with `lastPressure = 1013`, so the first reading (1013) gives "More of the same."
- 1012 < 1013 → "Cooler, rainy weather coming."
- 1015 > 1012 → "Improving weather on the way!"
- After removing `ForecastDisplay`, only Current and Statistics respond.

## Small code note
`Float.MIN_VALUE` is the smallest *positive* float, not the most negative. It works with this activity's data (all positive), but `-Float.MAX_VALUE` would be correct for sub-zero temperatures.
