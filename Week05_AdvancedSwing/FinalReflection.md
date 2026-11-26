# Week 5 Final Reflection: Advanced Swing

**Name:** Mohamednoor Dhuh  **Student ID:** 23ZAD108490  **Date:** 2026-11-21

## 1. What I built this week
| Activity | What it demonstrates |
|---|---|
| Activity 1: Contact List | JList, DefaultListModel, ListSelectionListener, custom ListCellRenderer |
| Activity 2: Student Table | JTable, DefaultTableModel, read-only cells, TableCellRenderer for GPA, sorting, TableModelListener |
| Activity 3: Folder Tree and Tabs | JTree, DefaultMutableTreeNode, TreeSelectionListener, JTabbedPane, JSplitPane |
| Activity 4: Notes Editor | JMenuBar, JToolBar, JOptionPane, JFileChooser, JPopupMenu, SwingWorker file I/O |
| Activity 5: Mini File Manager | JSplitPane with tree + table, synced navigation, Home/Up, double-click, status bar |

## 2. Key concepts I learned
- **Model/view separation:** data lives in a model (DefaultListModel, DefaultTableModel); you update the model, not the visual component.
- **Always wrap** JList, JTable, JTree and JTextArea in a JScrollPane.
- **Renderers** customise how cells are painted; they run constantly while scrolling, so load resources once and reuse them.
- **Dialogs and menus:** JOptionPane for message/confirm/input, JFileChooser for open/save, JPopupMenu with `isPopupTrigger()`.
- **Multi-pane apps:** selection listeners keep panes in sync, and file I/O belongs on a background thread.

## 3. Self-assessment answers
1. **JList vs JTable:** JList shows a single column of items; JTable shows rows and columns.
2. **Non-editable column/table:** override `isCellEditable` in the table model to return false.
3. **DefaultMutableTreeNode:** a node used to build the hierarchy shown in a JTree.
4. **Modifying a single column's appearance:** `getColumnModel().getColumn(i).setCellRenderer(...)`.
5. **Confirmation pop-ups:** `JOptionPane.showConfirmDialog`.
6. **Opening a file from disk:** `JFileChooser` (`showOpenDialog`).
7. **JSplitPane:** divides the window into two resizable panes.
8. **JScrollPane with JTable:** it provides scrollbars and shows the column headers.
9. **JList selection interface:** ListSelectionListener (check `!getValueIsAdjusting()`).
10. **Sub-menu:** add a JMenu inside another JMenu (instead of a JMenuItem).

## 4. Challenges I faced and how I solved them
Synchronizing the tree and table in the mini file manager was one of the most challenging parts. I solved it by updating the table from the tree selection and by keeping navigation actions in separate methods.

Custom rendering and editor behaviour also needed careful testing. I checked selection state, read-only cells, and data updates before extending the examples to larger lists or files.

## 5. What I would improve
I would add keyboard shortcuts, improve filtering, and persist recently opened files. I would also split the file-manager logic into smaller classes so the UI code is easier to maintain.

## 6. Confidence rating (1-5)
JList/JTable/JTree: 4  Renderers: 4  Menus and dialogs: 4  Multi-pane apps: 4
