/*
 * Week 5 - Activity 2: Student Records Table (JTable + DefaultTableModel)
 * Objective: Display tabular data with JTable, keep data in a TableModel (never edit
 *            the JTable directly), make cells read-only, colour the GPA column with
 *            a TableCellRenderer, enable sorting, and react to selection and model changes.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Activity02StudentTable {

    private static int nextId = 7;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity02StudentTable::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Student Records");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        String[] columns = {"ID", "Name", "Course", "Units", "GPA"};
        Class<?>[] types = {Integer.class, String.class, String.class, Integer.class, Double.class};
        Object[][] data = {
            {1, "Alice Wanjiru", "Computer Science", 48, 3.85},
            {2, "Brian Otieno", "Information Technology", 44, 2.70},
            {3, "Cynthia Achieng", "Computer Science", 52, 3.40},
            {4, "David Mutua", "Software Engineering", 40, 2.95},
            {5, "Esther Njeri", "Information Technology", 56, 3.90},
            {6, "Farah Hassan", "Software Engineering", 46, 3.15}
        };

        // Model: read-only cells, typed columns (so sorting is numeric)
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setAutoCreateRowSorter(true); // click headers to sort
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Renderer for the GPA column: red below 3.0
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        tbl, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (!isSelected && value instanceof Double) {
                    c.setForeground(((Double) value) < 3.0 ? Color.RED : Color.BLACK);
                }
                return c;
            }
        });

        JLabel statsLabel = new JLabel();
        statsLabel.setFont(new Font("Arial", Font.BOLD, 13));
        Runnable updateStats = () -> {
            double sum = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                sum += (Double) model.getValueAt(i, 4);
            }
            double avg = model.getRowCount() == 0 ? 0 : sum / model.getRowCount();
            statsLabel.setText(String.format("Students: %d   |   Average GPA: %.2f",
                    model.getRowCount(), avg));
        };
        model.addTableModelListener(e -> updateStats.run());
        updateStats.run();

        // Selection listener (check getValueIsAdjusting)
        JLabel selectedLabel = new JLabel("No student selected");
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = table.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = table.convertRowIndexToModel(viewRow); // sorting-safe
                    selectedLabel.setText("Selected: " + model.getValueAt(modelRow, 1));
                } else {
                    selectedLabel.setText("No student selected");
                }
            }
        });

        // Add form
        JTextField nameField = new JTextField(10);
        JTextField courseField = new JTextField(10);
        JTextField unitsField = new JTextField(3);
        JTextField gpaField = new JTextField(4);
        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete selected");

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String course = courseField.getText().trim();
                if (name.isEmpty() || course.isEmpty()) {
                    throw new IllegalArgumentException("Name and course are required.");
                }
                int units = Integer.parseInt(unitsField.getText().trim());
                double gpa = Double.parseDouble(gpaField.getText().trim());
                if (gpa < 0 || gpa > 4) {
                    throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
                }
                model.addRow(new Object[]{nextId++, name, course, units, gpa}); // update the MODEL
                nameField.setText("");
                courseField.setText("");
                unitsField.setText("");
                gpaField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Units must be a whole number and GPA a decimal number.",
                        "Invalid input", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(),
                        "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteBtn.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow < 0) {
                JOptionPane.showMessageDialog(frame, "Select a row first.");
                return;
            }
            int choice = JOptionPane.showConfirmDialog(frame, "Delete this record?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                model.removeRow(table.convertRowIndexToModel(viewRow));
            }
        });

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Course:"));
        form.add(courseField);
        form.add(new JLabel("Units:"));
        form.add(unitsField);
        form.add(new JLabel("GPA:"));
        form.add(gpaField);
        form.add(addBtn);
        form.add(deleteBtn);

        JPanel south = new JPanel(new BorderLayout());
        south.add(selectedLabel, BorderLayout.WEST);
        south.add(statsLabel, BorderLayout.EAST);

        frame.add(form, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER); // mandatory for headers
        frame.add(south, BorderLayout.SOUTH);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setSize(900, 460);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
