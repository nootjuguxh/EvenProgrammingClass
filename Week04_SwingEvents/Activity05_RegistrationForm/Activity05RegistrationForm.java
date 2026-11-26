/*
 * Week 4 - Activity 5: Registration Form (validation + shortcuts)
 * Objective: Build an interactive app with proper event handling:
 *   - Read inputs -> validate each field -> process -> give feedback -> clear.
 *   - Passwords handled as char[] (never String) and wiped after use.
 *   - "I agree" checkbox (ItemListener) enables the Submit button.
 *   - Ctrl+S keyboard shortcut using KeyStroke + AbstractAction (works app-wide).
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;

public class Activity05RegistrationForm extends JFrame {

    private final JTextField nameField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JCheckBox agreeBox = new JCheckBox("I agree to the terms and conditions");
    private final JButton submitButton = new JButton("Register (Ctrl+S)");
    private final JButton clearButton = new JButton("Clear");
    private final JLabel statusLabel = new JLabel(" ");

    public Activity05RegistrationForm() {
        super("Registration Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        form.add(new JLabel("Full name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Password (min 6 chars):"));
        form.add(passwordField);
        form.add(new JLabel(""));
        form.add(agreeBox);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(clearButton);
        buttons.add(submitButton);

        JPanel south = new JPanel(new BorderLayout());
        south.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        south.add(statusLabel, BorderLayout.NORTH);
        south.add(buttons, BorderLayout.SOUTH);

        add(form, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        // --- Event wiring ---
        submitButton.setEnabled(false);
        agreeBox.addItemListener(e ->
                submitButton.setEnabled(e.getStateChange() == ItemEvent.SELECTED));
        submitButton.addActionListener(e -> submit());
        clearButton.addActionListener(e -> clearForm());

        // Ctrl+S shortcut (works wherever focus is inside the window)
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ctrl S"), "register");
        getRootPane().getActionMap().put("register", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (submitButton.isEnabled()) {
                    submit();
                } else {
                    showError("Please agree to the terms first");
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void submit() {
        // Step 1: read inputs
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        char[] password = passwordField.getPassword();

        try {
            // Step 2: validate each field
            if (name.isEmpty()) {
                showError("Name is required");
                return;
            }
            if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
                showError("Email must be a valid address (contain @)");
                return;
            }
            if (password.length < 6) {
                showError("Password must be at least 6 characters");
                return;
            }

            // Step 3: process (we never print or store the password in plain text)
            saveRegistration(name, email);

            // Step 4: confirm to the user
            showSuccess("Registered " + name);
            JOptionPane.showMessageDialog(this, "Registration successful!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Step 5: clear the form
            clearForm();
        } finally {
            Arrays.fill(password, ' '); // wipe the password from memory
        }
    }

    private void saveRegistration(String name, String email) {
        // In a real application this would call a database or web service
        // (on a background thread!). Here we just simulate it.
        System.out.println("Saved registration: " + name + " <" + email + ">");
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        agreeBox.setSelected(false);
        nameField.requestFocusInWindow();
    }

    private void showError(String message) {
        statusLabel.setText("Error: " + message);
        statusLabel.setForeground(Color.RED);
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(0, 130, 0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Activity05RegistrationForm().setVisible(true));
    }
}
