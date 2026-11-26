/*
 * Week 3 - Activity 1: My First Swing Window
 * Objective: Create a functional Swing application with a main window (JFrame),
 *            started safely on the Event Dispatch Thread (EDT).
 *
 * Run:  javac Activity01FirstWindow.java
 *       java Activity01FirstWindow
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Activity01FirstWindow {

    public static void main(String[] args) {
        // CRITICAL: always create Swing GUIs on the EDT
        SwingUtilities.invokeLater(Activity01FirstWindow::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("My First Swing App");

        // Custom close handling: ask before exiting
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setResizable(true);

        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());

        JLabel hello = new JLabel("Hello, Swing!", SwingConstants.CENTER);
        hello.setFont(new Font("Arial", Font.BOLD, 28));
        hello.setForeground(new Color(25, 55, 115));
        frame.add(hello, BorderLayout.CENTER); // same as frame.getContentPane().add(...)

        JLabel info = new JLabel("Running on thread: " + Thread.currentThread().getName(),
                SwingConstants.CENTER);
        frame.add(info, BorderLayout.SOUTH);

        frame.setVisible(true); // hidden by default
    }
}
