/*
 * Week 5 - Activity 5: Mini File Manager (multi-pane application)
 * Objective: Combine everything from Week 5:
 *   - JSplitPane: folder JTree (left) + file JTable (right)
 *   - TreeSelectionListener syncs the table with the selected folder
 *   - Home / Up buttons and double-click navigation
 *   - Menu bar, toolbar, status bar (application shell)
 *   - Directory listing is done in a SwingWorker so the EDT is never blocked
 */
import javax.swing.*;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Activity05FileManager extends JFrame {

    /** Tree node holding a folder; children (sub-folders) are loaded lazily. */
    static class FolderNode extends DefaultMutableTreeNode {
        private boolean loaded = false;

        FolderNode(File folder) {
            super(folder);
        }

        File folder() {
            return (File) getUserObject();
        }

        @Override
        public boolean isLeaf() {
            return false; // always show the expand handle; children load on demand
        }

        @Override
        public String toString() {
            String name = folder().getName();
            return name.isEmpty() ? folder().getPath() : name;
        }

        void loadChildren(DefaultTreeModel model) {
            if (loaded) return;
            loaded = true;
            removeAllChildren();
            File[] subs = folder().listFiles(f -> f.isDirectory() && !f.isHidden());
            if (subs != null) {
                Arrays.sort(subs, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
                for (File sub : subs) {
                    add(new FolderNode(sub));
                }
            }
            model.nodeStructureChanged(this);
        }
    }

    private final DefaultTreeModel treeModel;
    private final JTree tree;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JLabel pathLabel = new JLabel();
    private final JLabel statusLabel = new JLabel(" Ready");
    private final JLabel countLabel = new JLabel("0 items ");
    private final List<File> shownFiles = new ArrayList<>();
    private File currentFolder;

    public Activity05FileManager() {
        super("Mini File Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---- Left pane: folder tree
        File home = new File(System.getProperty("user.home"));
        FolderNode root = new FolderNode(home);
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        root.loadChildren(treeModel);

        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) {
                Object last = event.getPath().getLastPathComponent();
                if (last instanceof FolderNode) {
                    ((FolderNode) last).loadChildren(treeModel);
                }
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) { }
        });

        // Selecting a folder updates the table (pane synchronisation)
        tree.addTreeSelectionListener(e -> {
            Object node = tree.getLastSelectedPathComponent();
            if (node instanceof FolderNode) {
                navigateTo(((FolderNode) node).folder());
            }
        });

        // ---- Right pane: file table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Type", "Size", "Modified"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(260);

        // Double-click a folder to open it
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0 && row < shownFiles.size()) {
                        File f = shownFiles.get(row);
                        if (f.isDirectory()) navigateTo(f);
                    }
                }
            }
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree), new JScrollPane(table));
        split.setDividerLocation(260);

        // ---- Menu bar, toolbar, status bar
        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);

        JPanel status = new JPanel(new BorderLayout());
        status.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        status.add(statusLabel, BorderLayout.WEST);
        status.add(countLabel, BorderLayout.EAST);
        add(status, BorderLayout.SOUTH);

        setSize(980, 600);
        setLocationRelativeTo(null);

        navigateTo(home);
        tree.setSelectionRow(0);
        tree.expandRow(0);
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem refresh = new JMenuItem("Refresh");
        refresh.setAccelerator(KeyStroke.getKeyStroke("F5"));
        refresh.addActionListener(e -> navigateTo(currentFolder));
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(refresh);
        file.addSeparator();
        file.add(exit);

        JMenu go = new JMenu("Go");
        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.addActionListener(e -> goHome());
        JMenuItem upItem = new JMenuItem("Up");
        upItem.addActionListener(e -> goUp());
        go.add(homeItem);
        go.add(upItem);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Mini File Manager\nWeek 5 - Advanced Swing", "About",
                JOptionPane.INFORMATION_MESSAGE));
        help.add(about);

        bar.add(file);
        bar.add(go);
        bar.add(help);
        return bar;
    }

    private JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton homeBtn = new JButton("Home");
        JButton upBtn = new JButton("Up");
        JButton refreshBtn = new JButton("Refresh");
        homeBtn.addActionListener(e -> goHome());
        upBtn.addActionListener(e -> goUp());
        refreshBtn.addActionListener(e -> navigateTo(currentFolder));

        toolbar.add(homeBtn);
        toolbar.add(upBtn);
        toolbar.add(refreshBtn);
        toolbar.addSeparator();
        toolbar.add(new JLabel(" Location: "));
        toolbar.add(pathLabel);
        return toolbar;
    }

    private void goHome() {
        navigateTo(new File(System.getProperty("user.home")));
    }

    private void goUp() {
        if (currentFolder != null && currentFolder.getParentFile() != null) {
            navigateTo(currentFolder.getParentFile());
        }
    }

    /** Loads a folder's contents into the table using a background thread. */
    private void navigateTo(File folder) {
        if (folder == null || !folder.isDirectory()) return;
        currentFolder = folder;
        pathLabel.setText(folder.getAbsolutePath());
        statusLabel.setText(" Loading...");

        new SwingWorker<File[], Void>() {
            @Override
            protected File[] doInBackground() {
                File[] files = folder.listFiles(f -> !f.isHidden());
                if (files == null) return new File[0];
                Arrays.sort(files, (a, b) -> {
                    if (a.isDirectory() != b.isDirectory()) return a.isDirectory() ? -1 : 1;
                    return a.getName().compareToIgnoreCase(b.getName());
                });
                return files;
            }

            @Override
            protected void done() {
                try {
                    if (!folder.equals(currentFolder)) return; // user already moved on
                    File[] files = get();
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    tableModel.setRowCount(0);
                    shownFiles.clear();
                    for (File f : files) {
                        shownFiles.add(f);
                        tableModel.addRow(new Object[]{
                            f.getName(),
                            f.isDirectory() ? "Folder" : "File",
                            f.isDirectory() ? "-" : humanSize(f.length()),
                            fmt.format(new Date(f.lastModified()))
                        });
                    }
                    countLabel.setText(files.length + " items ");
                    statusLabel.setText(files.length == 0 && !folder.canRead()
                            ? " Access denied" : " Ready");
                } catch (Exception ex) {
                    statusLabel.setText(" Could not read folder");
                }
            }
        }.execute();
    }

    private static String humanSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024L * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024));
        return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Activity05FileManager().setVisible(true));
    }
}
