# Week 3 Final Reflection: Swing Basics

**Name:** Mohamednoor Dhuh  **Student ID:** 23ZAD108490  **Date:** 2026-11-18

## 1. What I built this week
| Activity | What it demonstrates |
|---|---|
| Activity 1: First Window | JFrame, close operations, content pane, starting on the EDT |
| Activity 2: Components Showcase | JLabel, JTextField, JPasswordField, JTextArea + JScrollPane, JRadioButton + ButtonGroup, JComboBox, JCheckBox, JButton |
| Activity 3: Layout Managers | FlowLayout, BorderLayout, GridLayout, BoxLayout, nested panels |
| Activity 4: EDT Demo | Blocking the EDT vs background thread + invokeLater vs SwingWorker |
| Activity 5: Student Portal Mockup | Application shell (header, sidebar, content, footer), EmptyBorder, static mockup |

## 2. Key concepts I learned
- **JFrame and the content pane:** the JFrame is the top-level window; `frame.add()` actually adds to the content pane inside it.
- **Layout managers:** they decide how components are arranged, so we avoid absolute positioning. Nesting panels with different layouts combines their strengths.
- **The EDT:** Swing is not thread-safe, so all GUI creation and updates must happen on the Event Dispatch Thread, and long tasks must never run on it.
- **Static mockups:** a UI design with no logic lets you agree on the look before writing behaviour.

## 3. Self-assessment answers
1. **JFrame vs JPanel:** a JFrame is a top-level window with a title bar and controls; a JPanel is a lightweight container used to group components inside a window.
2. **EXIT_ON_CLOSE:** clicking the X terminates the whole JVM.
3. **Four layouts:** FlowLayout (toolbars), BorderLayout (app shell), GridLayout (calculator keypad), BoxLayout (vertical form).
4. **GridLayout vs FlowLayout:** use GridLayout for equal-sized cells in a fixed grid; FlowLayout for natural, wrapping rows.
5. **Why nest panels:** no single layout handles every case; nesting combines them.
6. **EDT:** the single thread that processes all Swing events and repaints; it exists because Swing components are not thread-safe.
7. **Two golden rules:** (1) all GUI work on the EDT; (2) never block the EDT.
8. **Thread.sleep() on the EDT:** the UI freezes because events and repaints cannot be processed.
9. **Safe label update from a background thread:** `SwingUtilities.invokeLater(() -> label.setText("..."))`.
10. **Application shell:** header (top), sidebar (left), main content (centre), footer (bottom).
11. **EmptyBorder:** adds padding between a panel's edge and its children.
12. **Static mockup:** shows the visual design before logic is added.

## 4. Challenges I faced and how I solved them
My main challenge was choosing layout managers that produced a predictable application shell. I used nested panels and learned that one layout manager is rarely enough for a complete interface.

The EDT activity also showed me why blocking UI code is harmful. I used background-thread and `SwingUtilities.invokeLater()` examples to keep the interface responsive.

## 5. What I would improve
I would complete the student portal mockup by adding real form fields, navigation, validation, and a persistent login state. I would also add more consistent spacing and visual styling.

## 6. Confidence rating (1-5)
Swing components: 4  Layout managers: 4  EDT and threading: 4
