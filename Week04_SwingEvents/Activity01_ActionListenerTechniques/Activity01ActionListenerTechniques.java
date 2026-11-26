/*
 * Week 4 - Activity 1: Four Ways to Write an ActionListener
 * Objective: Implement ActionListener using (1) a named class, (2) an anonymous
 *            inner class, (3) a lambda expression, (4) a method reference.
 *            Also detect the event source with getSource() and getActionCommand(),
 *            and handle Enter in a JTextField.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Activity01ActionListenerTechniques {

    private static JTextArea logArea;

    // Technique 1: Named class (reusable)
    static class NamedHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            log("Technique 1 (Named class): " + e.getActionCommand());
        }
    }

    // Technique 4: Method reference target
    private static void handleClick(ActionEvent e) {
        log("Technique 4 (Method reference): " + e.getActionCommand());
    }

    private static void log(String message) {
        logArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity01ActionListenerTechniques::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ActionListener Techniques");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        logArea = new JTextArea(12, 40);
        logArea.setEditable(false);

        // --- The four techniques ---
        JButton b1 = new JButton("1. Named Class");
        JButton b2 = new JButton("2. Anonymous Class");
        JButton b3 = new JButton("3. Lambda");
        JButton b4 = new JButton("4. Method Reference");

        b1.addActionListener(new NamedHandler());

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log("Technique 2 (Anonymous class): " + e.getActionCommand());
            }
        });

        b3.addActionListener(e -> log("Technique 3 (Lambda): " + e.getActionCommand()));

        b4.addActionListener(Activity01ActionListenerTechniques::handleClick);

        JPanel techniques = new JPanel(new GridLayout(1, 4, 8, 8));
        techniques.setBorder(BorderFactory.createTitledBorder("Four techniques"));
        techniques.add(b1);
        techniques.add(b2);
        techniques.add(b3);
        techniques.add(b4);

        // --- One shared listener, detect the source ---
        JButton btnA = new JButton("A");
        JButton btnB = new JButton("B");
        JButton btnC = new JButton("C");
        btnA.setActionCommand("CMD_A");
        btnB.setActionCommand("CMD_B");
        btnC.setActionCommand("CMD_C");

        ActionListener shared = e -> {
            if (e.getSource() == btnA) {
                log("Shared listener: getSource() says A | command = " + e.getActionCommand());
            } else if (e.getSource() == btnB) {
                log("Shared listener: getSource() says B | command = " + e.getActionCommand());
            } else {
                log("Shared listener: got " + e.getActionCommand());
            }
        };
        btnA.addActionListener(shared);
        btnB.addActionListener(shared);
        btnC.addActionListener(shared);

        JPanel sharedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        sharedPanel.setBorder(BorderFactory.createTitledBorder("One shared listener"));
        sharedPanel.add(btnA);
        sharedPanel.add(btnB);
        sharedPanel.add(btnC);

        // --- Enter key in a text field fires an ActionEvent ---
        JTextField field = new JTextField(20);
        field.addActionListener(e -> {
            log("Text field (Enter pressed): \"" + field.getText() + "\"");
            field.setText("");
        });
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        fieldPanel.setBorder(BorderFactory.createTitledBorder("Type and press Enter"));
        fieldPanel.add(field);

        JPanel top = new JPanel(new GridLayout(3, 1, 5, 5));
        top.add(techniques);
        top.add(sharedPanel);
        top.add(fieldPanel);

        frame.add(top, BorderLayout.NORTH);
        frame.add(new JScrollPane(logArea), BorderLayout.CENTER);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
