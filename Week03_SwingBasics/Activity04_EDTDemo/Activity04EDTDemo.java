/*
 * Week 3 - Activity 4: The Event Dispatch Thread (EDT)
 * Objective: Prove the two golden rules of Swing threading.
 *   Rule 1: All GUI creation/updates happen on the EDT (use invokeLater).
 *   Rule 2: Never block the EDT with long operations.
 *
 * Try it: press the WRONG button, then try clicking "Click me" or moving the window
 * - the UI is frozen for 3 seconds. Then try the RIGHT and BEST buttons.
 */
import javax.swing.*;
import java.awt.*;

public class Activity04EDTDemo {

    private static JLabel statusLabel;
    private static JLabel threadLabel;
    private static JLabel clickLabel;
    private static int clicks = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity04EDTDemo::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("EDT Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        statusLabel = new JLabel("Status: idle", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        threadLabel = new JLabel("Thread: " + threadInfo(), SwingConstants.CENTER);
        clickLabel = new JLabel("Clicks: 0", SwingConstants.CENTER);

        JButton wrong = new JButton("WRONG: Thread.sleep() on the EDT");
        JButton right = new JButton("RIGHT: background Thread + invokeLater");
        JButton best = new JButton("BEST: SwingWorker");
        JButton click = new JButton("Click me (tests responsiveness)");

        // WRONG - blocks the EDT: window cannot move, clicks are ignored
        wrong.addActionListener(e -> {
            statusLabel.setText("Sleeping on the EDT...");
            sleep(3000);
            statusLabel.setText("Done - but the UI was frozen!");
            threadLabel.setText("Thread: " + threadInfo());
        });

        // RIGHT - heavy work off the EDT, UI update back on the EDT
        right.addActionListener(e -> {
            statusLabel.setText("Working in a background thread...");
            right.setEnabled(false);
            new Thread(() -> {
                sleep(3000); // runs OFF the EDT
                String where = threadInfo();
                SwingUtilities.invokeLater(() -> { // back on the EDT
                    statusLabel.setText("Done - UI stayed responsive!");
                    threadLabel.setText("Worked on: " + where + " | Updated on: " + threadInfo());
                    right.setEnabled(true);
                });
            }).start();
        });

        // BEST - SwingWorker handles the thread switching for us
        best.addActionListener(e -> {
            statusLabel.setText("SwingWorker running...");
            best.setEnabled(false);
            new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() { // background thread
                    sleep(3000);
                    return "doInBackground ran on: " + threadInfo();
                }

                @Override
                protected void done() { // EDT
                    try {
                        statusLabel.setText("SwingWorker finished!");
                        threadLabel.setText(get() + " | done() on: " + threadInfo());
                    } catch (Exception ex) {
                        statusLabel.setText("Error: " + ex.getMessage());
                    }
                    best.setEnabled(true);
                }
            }.execute();
        });

        click.addActionListener(e -> clickLabel.setText("Clicks: " + (++clicks)));

        panel.add(statusLabel);
        panel.add(threadLabel);
        panel.add(wrong);
        panel.add(right);
        panel.add(best);
        panel.add(click);
        panel.add(clickLabel);

        frame.add(panel);
        frame.setSize(560, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String threadInfo() {
        return Thread.currentThread().getName()
                + (SwingUtilities.isEventDispatchThread() ? " (EDT)" : " (not EDT)");
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
