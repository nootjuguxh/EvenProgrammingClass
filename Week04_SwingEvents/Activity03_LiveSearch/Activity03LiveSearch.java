/*
 * Week 4 - Activity 3: Live Search + Character Counter (DocumentListener)
 * Objective: Use DocumentListener to react to every text change.
 *   - A search box filters a JList of countries in real time.
 *   - A tweet-style text area shows a live character counter.
 * Remember: DocumentListener is NOT a functional interface (3 methods), and it is
 * registered on the Document: textField.getDocument().addDocumentListener(...)
 */
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Activity03LiveSearch {

    private static final String[] COUNTRIES = {
        "Kenya", "Tanzania", "Uganda", "Rwanda", "Burundi", "Ethiopia", "Somalia",
        "South Sudan", "Egypt", "Nigeria", "Ghana", "South Africa", "Morocco",
        "Senegal", "Zambia", "Zimbabwe", "Namibia", "Botswana"
    };
    private static final int MAX_CHARS = 280;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity03LiveSearch::createAndShowGUI);
    }

    /** Helper: wraps a Runnable in a DocumentListener so we write the 3 methods once. */
    private static DocumentListener onChange(Runnable action) {
        return new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { action.run(); }
            @Override public void removeUpdate(DocumentEvent e)  { action.run(); }
            @Override public void changedUpdate(DocumentEvent e) { /* attributes only - ignore */ }
        };
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("DocumentListener Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1, 10, 10));

        // ----- Panel 1: live search -----
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String c : COUNTRIES) model.addElement(c);

        JTextField searchField = new JTextField(20);
        JLabel resultCount = new JLabel(model.size() + " result(s)");
        JList<String> list = new JList<>(model);

        searchField.getDocument().addDocumentListener(onChange(() -> {
            String query = searchField.getText().trim().toLowerCase();
            model.clear();
            for (String c : COUNTRIES) {
                if (c.toLowerCase().contains(query)) model.addElement(c);
            }
            resultCount.setText(model.size() + " result(s)");
        }));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Live search (countries)"));
        JPanel searchTop = new JPanel(new BorderLayout(5, 5));
        searchTop.add(new JLabel("Search: "), BorderLayout.WEST);
        searchTop.add(searchField, BorderLayout.CENTER);
        searchTop.add(resultCount, BorderLayout.EAST);
        searchPanel.add(searchTop, BorderLayout.NORTH);
        searchPanel.add(new JScrollPane(list), BorderLayout.CENTER);

        // ----- Panel 2: character counter -----
        JTextArea tweet = new JTextArea(4, 30);
        tweet.setLineWrap(true);
        tweet.setWrapStyleWord(true);
        JLabel counter = new JLabel(MAX_CHARS + " characters left", SwingConstants.RIGHT);

        tweet.getDocument().addDocumentListener(onChange(() -> {
            int left = MAX_CHARS - tweet.getText().length();
            counter.setText(left + " characters left");
            counter.setForeground(left < 0 ? Color.RED : Color.DARK_GRAY);
        }));

        JPanel tweetPanel = new JPanel(new BorderLayout(5, 5));
        tweetPanel.setBorder(BorderFactory.createTitledBorder("Character counter"));
        tweetPanel.add(new JScrollPane(tweet), BorderLayout.CENTER);
        tweetPanel.add(counter, BorderLayout.SOUTH);

        frame.add(searchPanel);
        frame.add(tweetPanel);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setSize(520, 560);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
