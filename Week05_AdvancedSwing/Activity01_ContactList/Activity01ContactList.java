/*
 * Week 5 - Activity 1: Contact List (JList + DefaultListModel + custom renderer)
 * Objective: Use JList with a DefaultListModel, handle selection with a
 *            ListSelectionListener (checking getValueIsAdjusting), and colour
 *            rows with a custom ListCellRenderer. Always wrap a JList in a JScrollPane.
 */
import javax.swing.*;
import java.awt.*;

public class Activity01ContactList {

    // Created once and reused - renderers are called constantly while scrolling
    private static final Color ONLINE_GREEN = new Color(0, 140, 0);
    private static final Color OFFLINE_GRAY = Color.GRAY;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity01ContactList::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Contact List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // 1. Data model
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Alice Wanjiru - Online");
        model.addElement("Brian Otieno - Offline");
        model.addElement("Cynthia Achieng - Online");
        model.addElement("David Mutua - Offline");
        model.addElement("Esther Njeri - Online");

        // 2. List + scroll pane
        JList<String> contactList = new JList<>(model);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 3. Custom renderer
        contactList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                boolean online = value.toString().contains("Online");
                if (!isSelected) {
                    label.setForeground(online ? ONLINE_GREEN : OFFLINE_GRAY);
                }
                label.setFont(label.getFont().deriveFont(online ? Font.BOLD : Font.PLAIN));
                return label;
            }
        });

        // 4. Selection handling
        JLabel details = new JLabel("Select a contact", SwingConstants.CENTER);
        details.setFont(new Font("Arial", Font.BOLD, 14));
        contactList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // prevents double firing
                String selected = contactList.getSelectedValue();
                details.setText(selected == null ? "Select a contact" : "Selected: " + selected);
            }
        });

        // 5. Controls: add, toggle status, remove
        JTextField nameField = new JTextField(12);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Online", "Offline"});
        JButton add = new JButton("Add");
        JButton toggle = new JButton("Toggle status");
        JButton remove = new JButton("Remove");

        add.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a name.",
                        "Missing name", JOptionPane.WARNING_MESSAGE);
                return;
            }
            model.addElement(name + " - " + statusBox.getSelectedItem());
            nameField.setText("");
        });

        toggle.addActionListener(e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                String item = model.get(index);
                String name = item.substring(0, item.lastIndexOf(" - "));
                boolean wasOnline = item.endsWith("Online");
                model.set(index, name + " - " + (wasOnline ? "Offline" : "Online"));
            }
        });

        remove.addActionListener(e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                int choice = JOptionPane.showConfirmDialog(frame, "Remove this contact?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    model.remove(index);
                }
            }
        });

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        controls.add(new JLabel("Name:"));
        controls.add(nameField);
        controls.add(statusBox);
        controls.add(add);
        controls.add(toggle);
        controls.add(remove);

        frame.add(new JScrollPane(contactList), BorderLayout.CENTER);
        frame.add(controls, BorderLayout.NORTH);
        frame.add(details, BorderLayout.SOUTH);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setSize(640, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
