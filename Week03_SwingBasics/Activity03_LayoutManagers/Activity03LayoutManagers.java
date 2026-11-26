/*
 * Week 3 - Activity 3: Layout Managers Gallery
 * Objective: See FlowLayout, BorderLayout, GridLayout and BoxLayout side by side,
 *            and practise nesting panels (a GridLayout shell holding four panels,
 *            each with its own layout manager).
 */
import javax.swing.*;
import java.awt.*;

public class Activity03LayoutManagers {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity03LayoutManagers::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Layout Managers Gallery");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel shell = new JPanel(new GridLayout(2, 2, 10, 10));
        shell.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        shell.add(titled("FlowLayout (left to right, wraps)", flowDemo()));
        shell.add(titled("BorderLayout (5 regions)", borderDemo()));
        shell.add(titled("GridLayout (3 x 3, equal cells)", gridDemo()));
        shell.add(titled("BoxLayout (vertical stack)", boxDemo()));

        frame.add(shell);
        frame.setSize(760, 560);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel titled(String title, JPanel panel) {
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private static JPanel flowDemo() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        for (int i = 1; i <= 6; i++) {
            panel.add(new JButton("Button " + i));
        }
        return panel;
    }

    private static JPanel borderDemo() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JButton("North"), BorderLayout.NORTH);
        panel.add(new JButton("South"), BorderLayout.SOUTH);
        panel.add(new JButton("East"), BorderLayout.EAST);
        panel.add(new JButton("West"), BorderLayout.WEST);
        panel.add(new JButton("Center"), BorderLayout.CENTER);
        return panel;
    }

    private static JPanel gridDemo() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));
        for (int i = 1; i <= 9; i++) {
            panel.add(new JButton(String.valueOf(i)));
        }
        return panel;
    }

    private static JPanel boxDemo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        JButton submit = new JButton("Submit");

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        submit.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(10)); // fixed 10px spacer
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(submit);
        return panel;
    }
}
