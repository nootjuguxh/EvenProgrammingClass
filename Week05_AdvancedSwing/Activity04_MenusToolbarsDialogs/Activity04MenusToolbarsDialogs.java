/*
 * Week 5 - Activity 4: Notes Editor (JMenuBar, JToolBar, dialogs, JFileChooser, JPopupMenu)
 * Objective: Build a small text editor that uses
 *   - JMenuBar > JMenu > JMenuItem (with keyboard accelerators and a separator)
 *   - JToolBar sharing the SAME Action objects as the menu
 *   - JOptionPane (confirm, input, message) and JFileChooser (open / save)
 *   - JPopupMenu (right-click) using isPopupTrigger()
 *   - SwingWorker so file I/O never blocks the EDT
 */
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;

public class Activity04MenusToolbarsDialogs extends JFrame {

    private final JTextArea textArea = new JTextArea();
    private final JLabel statusBar = new JLabel(" Ready");
    private File currentFile = null;
    private boolean modified = false;

    // Actions are shared by the menu and the toolbar
    private final Action newAction = new AbstractAction("New") {
        @Override public void actionPerformed(ActionEvent e) { guardUnsaved(Activity04MenusToolbarsDialogs.this::newDocument); }
    };
    private final Action openAction = new AbstractAction("Open...") {
        @Override public void actionPerformed(ActionEvent e) { guardUnsaved(Activity04MenusToolbarsDialogs.this::openDocument); }
    };
    private final Action saveAction = new AbstractAction("Save") {
        @Override public void actionPerformed(ActionEvent e) { save(null); }
    };
    private final Action exitAction = new AbstractAction("Exit") {
        @Override public void actionPerformed(ActionEvent e) { guardUnsaved(() -> System.exit(0)); }
    };

    public Activity04MenusToolbarsDialogs() {
        super("Notes Editor");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });

        setLayout(new BorderLayout());
        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);

        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        add(statusBar, BorderLayout.SOUTH);

        // Track modifications
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { markModified(); }
            @Override public void removeUpdate(DocumentEvent e)  { markModified(); }
            @Override public void changedUpdate(DocumentEvent e) { markModified(); }
        });

        installPopupMenu();
        updateTitle();
        setSize(760, 520);
        setLocationRelativeTo(null);
    }

    // ------------------------------------------------------------ UI builders

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem newItem = new JMenuItem(newAction);
        newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        JMenuItem openItem = new JMenuItem(openAction);
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        JMenuItem saveItem = new JMenuItem(saveAction);
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        saveAsItem.addActionListener(e -> saveAs(null));
        file.add(newItem);
        file.add(openItem);
        file.add(saveItem);
        file.add(saveAsItem);
        file.addSeparator(); // horizontal line
        file.add(new JMenuItem(exitAction));

        JMenu edit = new JMenu("Edit");
        edit.add(editItem(new DefaultEditorKit.CutAction(), "Cut"));
        edit.add(editItem(new DefaultEditorKit.CopyAction(), "Copy"));
        edit.add(editItem(new DefaultEditorKit.PasteAction(), "Paste"));
        edit.addSeparator();
        JMenuItem find = new JMenuItem("Find...");
        find.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
        find.addActionListener(e -> find());
        edit.add(find);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Notes Editor\nWeek 5 - Advanced Swing", "About", JOptionPane.INFORMATION_MESSAGE));
        help.add(about);

        bar.add(file);
        bar.add(edit);
        bar.add(help);
        return bar;
    }

    private JMenuItem editItem(Action action, String text) {
        JMenuItem item = new JMenuItem(action);
        item.setText(text);
        return item;
    }

    private JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false); // prevents dragging the toolbar
        toolbar.add(new JButton(newAction));
        toolbar.add(new JButton(openAction));
        toolbar.add(new JButton(saveAction));
        return toolbar;
    }

    private void installPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        popup.add(editItem(new DefaultEditorKit.CutAction(), "Cut"));
        popup.add(editItem(new DefaultEditorKit.CopyAction(), "Copy"));
        popup.add(editItem(new DefaultEditorKit.PasteAction(), "Paste"));

        textArea.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e)  { maybeShow(e); }
            @Override public void mouseReleased(MouseEvent e) { maybeShow(e); }

            private void maybeShow(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    // ------------------------------------------------------------ Document logic

    private void markModified() {
        if (!modified) {
            modified = true;
            updateTitle();
        }
    }

    private void updateTitle() {
        String name = currentFile == null ? "Untitled" : currentFile.getName();
        setTitle("Notes Editor - " + name + (modified ? " *" : ""));
    }

    /** Runs the action immediately, or after the user decides what to do with unsaved changes. */
    private void guardUnsaved(Runnable action) {
        if (!modified) {
            action.run();
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this,
                "Save changes before continuing?", "Unsaved changes",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            save(action);          // runs 'action' after a successful save
        } else if (choice == JOptionPane.NO_OPTION) {
            action.run();
        } // CANCEL: do nothing
    }

    private void newDocument() {
        textArea.setText("");
        currentFile = null;
        modified = false;
        updateTitle();
        statusBar.setText(" New document");
    }

    private void openDocument() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        statusBar.setText(" Opening " + file.getName() + "...");

        new SwingWorker<String, Void>() {        // file I/O off the EDT
            @Override
            protected String doInBackground() throws Exception {
                return Files.readString(file.toPath());
            }

            @Override
            protected void done() {              // back on the EDT
                try {
                    textArea.setText(get());
                    textArea.setCaretPosition(0);
                    currentFile = file;
                    modified = false;
                    updateTitle();
                    statusBar.setText(" Opened " + file.getName());
                } catch (Exception ex) {
                    showIoError("open", ex);
                }
            }
        }.execute();
    }

    private void save(Runnable after) {
        if (currentFile == null) {
            saveAs(after);
        } else {
            write(currentFile, after);
        }
    }

    private void saveAs(Runnable after) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            write(chooser.getSelectedFile(), after);
        }
    }

    private void write(File file, Runnable after) {
        String text = textArea.getText();
        statusBar.setText(" Saving...");
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Files.writeString(file.toPath(), text);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    currentFile = file;
                    modified = false;
                    updateTitle();
                    statusBar.setText(" Saved " + file.getName());
                    if (after != null) after.run();
                } catch (Exception ex) {
                    showIoError("save", ex);
                }
            }
        }.execute();
    }

    private void find() {
        String query = JOptionPane.showInputDialog(this, "Find:", "Find", JOptionPane.QUESTION_MESSAGE);
        if (query == null || query.isEmpty()) return;
        int index = textArea.getText().indexOf(query);
        if (index >= 0) {
            textArea.requestFocusInWindow();
            textArea.select(index, index + query.length());
        } else {
            JOptionPane.showMessageDialog(this, "\"" + query + "\" was not found.",
                    "Find", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showIoError(String what, Exception ex) {
        statusBar.setText(" Could not " + what + " the file");
        JOptionPane.showMessageDialog(this,
                "Could not " + what + " the file:\n" + ex.getMessage(),
                "File error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Activity04MenusToolbarsDialogs().setVisible(true));
    }
}
