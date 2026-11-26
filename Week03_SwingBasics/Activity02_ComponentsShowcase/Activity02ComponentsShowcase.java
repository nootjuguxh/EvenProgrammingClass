/*
 * Week 3 - Activity 2: Components Showcase (Registration-style form)
 * Objective: Add and customize core Swing components:
 *            JLabel, JTextField, JPasswordField, JTextArea (+JScrollPane),
 *            JRadioButton (+ButtonGroup), JComboBox, JCheckBox, JButton.
 * This activity is a static layout - no event handling yet.
 */
import javax.swing.*;
import java.awt.*;

public class Activity02ComponentsShowcase {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity02ComponentsShowcase::createAndShowGUI);
    }

    private static JLabel boldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLUE);
        return label;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Components Showcase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField userField = new JTextField(20);
        form.add(boldLabel("Username:"));
        form.add(userField);

        JPasswordField passField = new JPasswordField(20);
        form.add(boldLabel("Password:"));
        form.add(passField);

        // Radio buttons need a ButtonGroup to be mutually exclusive
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        JRadioButton other = new JRadioButton("Other");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);
        female.setSelected(true);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(other);
        form.add(boldLabel("Gender:"));
        form.add(genderPanel);

        JComboBox<String> countryBox = new JComboBox<>(
                new String[]{"Kenya", "Tanzania", "Uganda", "Rwanda"});
        countryBox.addItem("Ethiopia"); // add dynamically
        countryBox.setSelectedIndex(0);
        form.add(boldLabel("Country:"));
        form.add(countryBox);

        JTextArea comments = new JTextArea(4, 20);
        comments.setLineWrap(true);
        comments.setWrapStyleWord(true);
        form.add(boldLabel("Comments:"));
        form.add(new JScrollPane(comments)); // always wrap JTextArea

        JCheckBox remember = new JCheckBox("Remember me");
        remember.setSelected(true);
        form.add(new JLabel(""));
        form.add(remember);

        JButton submit = new JButton("Submit");
        submit.setBackground(new Color(100, 200, 100));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Arial", Font.BOLD, 14));
        submit.setFocusPainted(false);
        form.add(new JLabel(""));
        form.add(submit);

        frame.add(form);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
