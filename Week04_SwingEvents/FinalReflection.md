# Week 4 Final Reflection: Swing Events

**Name:** Mohamednoor Dhuh  **Student ID:** 23ZAD108490  **Date:** 2026-11-26

## 1. What I built this week
| Activity | What it demonstrates |
|---|---|
| Activity 1: ActionListener Techniques | Named class, anonymous class, lambda, method reference; getSource() and getActionCommand(); Enter in a text field |
| Activity 2: Pizza Order | ItemListener for checkboxes and radio buttons, ActionListener for a combo box, real-time total |
| Activity 3: Live Search | DocumentListener for live filtering and a character counter |
| Activity 4: Mouse and Keyboard | MouseAdapter, MouseMotionAdapter, KeyAdapter, focus handling |
| Activity 5: Registration Form | Validation steps, char[] passwords, ItemListener enabling a button, Ctrl+S key binding |

## 2. Key concepts I learned
- **Event flow:** user action -> event source -> event object -> listener -> handler method.
- **Choosing the right listener:** ActionListener for clicks and Enter, ItemListener for selection state, DocumentListener for text changes.
- **DocumentListener** has three methods, so it is not a functional interface (no direct lambda), and it is registered on `getDocument()`.
- **Adapter classes** (MouseAdapter, KeyAdapter, WindowAdapter) let you override only the methods you need.
- **Key events need focus:** `setFocusable(true)` and `requestFocusInWindow()`.
- **Handler pattern:** read state, validate, update state, update the GUI.

## 3. Self-assessment answers
1. **ActionListener vs ItemListener:** ActionListener fires on clicks/Enter; ItemListener fires when a selection state changes.
2. **Named class vs lambda:** use a named class when the same listener logic is reused by several components.
3. **Identifying the source:** `e.getSource()` or `e.getActionCommand()`.
4. **DocumentListener methods:** insertUpdate, removeUpdate, changedUpdate.
5. **Not functional:** it has three abstract methods, and a lambda needs exactly one.
6. **ButtonGroup:** makes radio buttons mutually exclusive; without it they behave like checkboxes.
7. **Checkbox state changes:** ItemListener.
8. **Live character counter:** DocumentListener.
9. **MouseListener vs MouseMotionListener:** MouseListener handles clicks/press/release/enter/exit; MouseMotionListener handles moves and drags.
10. **Focus for KeyListener:** key events go only to the focused component.
11. **Adapter classes:** empty default implementations so you override only what you need.
12. **Ctrl+S shortcut:** bind a KeyStroke in the InputMap to an AbstractAction in the ActionMap.
13. **Instance variables for state:** handlers run at unpredictable times, and local variables go out of scope (and captured ones must be effectively final).
14. **Validation steps:** read inputs, validate each field, process the data, confirm to the user, clear the form.
15. **char[] for passwords:** a String is immutable and lingers in memory; a char[] can be wiped.

## 4. Challenges I faced and how I solved them
The registration form was difficult because several events had to work together: text changes, checkbox state, password validation, and keyboard shortcuts. I separated reading, validation, processing, and confirmation into clear handler steps.

Key events also needed component focus. I used focus handling and Swing key bindings so shortcuts worked only when expected.

## 5. What I would improve
I would improve feedback with clearer field-level messages and reduce repeated validation code by extracting shared validation methods.

## 6. Confidence rating (1-5)
ActionListener: 5  ItemListener: 4  DocumentListener: 4  Mouse/Key events: 4
