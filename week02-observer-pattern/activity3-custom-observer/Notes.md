# Activity 3 – Notes: Tight Coupling vs. Observer Pattern

## Step 2: Identify the Coupling Problem

**1. What happens if we want to add a third display class? Do we have to modify DataModel?**
Yes. In `TightlyCoupledSystem`, `DataModel.setValue()` hard-codes calls to `new LabelDisplay()` and `new BarChartDisplay()`. A third display (e.g. `TextLogger`) means opening `DataModel` and adding another line. This violates the Open/Closed Principle (open for extension, closed for modification), and every edit risks introducing bugs into code that already worked.

**2. What happens if LabelDisplay is deleted or renamed?**
`DataModel` no longer compiles, because it references `LabelDisplay` directly. A change in a display class forces a change in the data model — the dependency points the wrong way (the model depends on the UI).

**3. Can we easily test DataModel in isolation?**
No. `DataModel` creates its display objects internally, so you can't test it without them (and without their console output/side effects). You can't substitute a mock or a fake display either, because the concrete classes are baked in.

## Step 4: Comparison

| Aspect | Tightly Coupled | Observer Pattern |
|--------|-----------------|------------------|
| Adding a new display | Modify `DataModel` | Just register a new observer (`model.addObserver(new TextLogger())`) |
| Testing `DataModel` in isolation | Hard — it needs the display classes | Easy — no display classes required; register a test observer |
| Removing a display | Modify `DataModel` | Just don't register it (or call `removeObserver`) |
| Who creates the displays? | `DataModel` (inside `setValue`) | The client code (`RefactoredMain`) |
| What does `DataModel` depend on? | Concrete classes `LabelDisplay`, `BarChartDisplay` | Only the `DataObserver` interface |
| Runtime flexibility | None — fixed at compile time | Observers can be added/removed while the program runs |

## Key takeaways
- **Dependency inversion:** `DataModel` now depends on the abstraction (`DataObserver`), not on concrete displays.
- **Proof it works:** `TextLogger` was added without touching a single line of `DataModel`.
- **Same behaviour:** for the first two displays the output is identical to the "before" version; the new logger just adds extra lines.
- **Bonus fix:** the original created a *new* display object on every `setValue()` call; the refactored version creates each observer once and reuses it, so observers can keep state (as in Activity 1's `StatisticsDisplay`).

## Deliverable checklist
- [x] TightlyCoupledSystem.java (the "before" example)
- [x] DataObserver, DataSubject, DataModel, LabelDisplay, BarChartDisplay, TextLogger, RefactoredMain (the 7 refactored files)
- [x] All compile and run successfully
- [x] Notes comparing the two approaches (this file)
