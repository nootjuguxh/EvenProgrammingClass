/*
 * Week 5 - Activity 3: Folder Tree + Tabs (JTree, JTabbedPane, JSplitPane)
 * Objective: Display hierarchical data with JTree and DefaultMutableTreeNode,
 *            show details in a JTabbedPane, and coordinate the two panes with a
 *            TreeSelectionListener inside a JSplitPane.
 */
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class Activity03FolderTreeAndTabs {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity03FolderTreeAndTabs::createAndShowGUI);
    }

    private static DefaultMutableTreeNode buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("My Computer");

        DefaultMutableTreeNode docs = new DefaultMutableTreeNode("Documents");
        docs.add(new DefaultMutableTreeNode("Resume.pdf"));
        docs.add(new DefaultMutableTreeNode("Notes.txt"));
        DefaultMutableTreeNode project = new DefaultMutableTreeNode("Project");
        project.add(new DefaultMutableTreeNode("Report.docx"));
        project.add(new DefaultMutableTreeNode("Budget.xlsx"));
        docs.add(project);

        DefaultMutableTreeNode pictures = new DefaultMutableTreeNode("Pictures");
        pictures.add(new DefaultMutableTreeNode("Holiday.png"));
        pictures.add(new DefaultMutableTreeNode("Family.jpg"));

        DefaultMutableTreeNode music = new DefaultMutableTreeNode("Music");
        music.add(new DefaultMutableTreeNode("Song1.mp3"));
        music.add(new DefaultMutableTreeNode("Song2.mp3"));

        DefaultMutableTreeNode downloads = new DefaultMutableTreeNode("Downloads");
        downloads.add(new DefaultMutableTreeNode("installer.exe"));

        root.add(docs);
        root.add(pictures);
        root.add(music);
        root.add(downloads);
        return root;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Folder Tree & Tabs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTree tree = new JTree(buildTree());
        tree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);

        // --- Right side: three tabs ---
        JLabel detailsLabel = new JLabel("<html>Select an item in the tree.</html>");
        detailsLabel.setVerticalAlignment(SwingConstants.TOP);
        JPanel detailsTab = new JPanel(new BorderLayout());
        detailsTab.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        detailsTab.add(detailsLabel, BorderLayout.CENTER);

        DefaultListModel<String> contentsModel = new DefaultListModel<>();
        JList<String> contentsList = new JList<>(contentsModel);
        JPanel contentsTab = new JPanel(new BorderLayout());
        contentsTab.add(new JScrollPane(contentsList), BorderLayout.CENTER);

        JTextArea about = new JTextArea(
                "This demo shows how a JTree (hierarchy) can drive other components.\n\n"
              + "Click a node: the Details tab shows its path and type, and the Contents tab\n"
              + "lists its children.");
        about.setEditable(false);
        about.setLineWrap(true);
        about.setWrapStyleWord(true);
        about.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Details", detailsTab);
        tabs.addTab("Contents", contentsTab);
        tabs.addTab("About", new JScrollPane(about));

        // --- Coordinate navigation: tree selection updates the tabs ---
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;

            StringBuilder path = new StringBuilder();
            for (Object part : node.getUserObjectPath()) {
                if (path.length() > 0) path.append(" > ");
                path.append(part);
            }
            detailsLabel.setText("<html><b>Name:</b> " + node.getUserObject()
                    + "<br><b>Path:</b> " + path
                    + "<br><b>Type:</b> " + (node.isLeaf() ? "File" : "Folder")
                    + "<br><b>Depth:</b> " + node.getLevel()
                    + "<br><b>Children:</b> " + node.getChildCount() + "</html>");

            contentsModel.clear();
            for (int i = 0; i < node.getChildCount(); i++) {
                contentsModel.addElement(String.valueOf(
                        ((DefaultMutableTreeNode) node.getChildAt(i)).getUserObject()));
            }
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree), tabs);
        split.setDividerLocation(220);

        frame.add(split, BorderLayout.CENTER);
        frame.setSize(760, 440);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
