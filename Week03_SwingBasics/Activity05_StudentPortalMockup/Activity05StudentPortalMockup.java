/*
 * Week 3 - Activity 5: University Student Portal (Static Mockup)
 * Objective: Build a static, non-interactive UI using the Application Shell pattern:
 *            Header (NORTH) + Sidebar (WEST) + Content (CENTER) + Footer (SOUTH).
 * Uses nested layouts, EmptyBorder padding, a consistent colour palette and
 * realistic data. No event handling - that comes in Week 4.
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Activity05StudentPortalMockup {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity05StudentPortalMockup::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("University Student Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createHeader(), BorderLayout.NORTH);
        frame.add(createSidebar(), BorderLayout.WEST);
        frame.add(createContent(), BorderLayout.CENTER);
        frame.add(createFooter(), BorderLayout.SOUTH);

        frame.setSize(950, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 55, 115));
        header.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel title = new JLabel("University Student Portal");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton logout = new JButton("Logout");
        logout.setBackground(new Color(200, 60, 60));
        logout.setForeground(Color.WHITE);
        logout.setFocusPainted(false);

        header.add(title, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);
        return header;
    }

    private static JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(6, 1, 5, 5));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBackground(new Color(240, 240, 245));
        sidebar.setBorder(new EmptyBorder(15, 10, 15, 10));

        String[] menuItems = {"Dashboard", "Courses", "Grades", "Fees", "Timetable", "Messages"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            sidebar.add(btn);
        }
        return sidebar;
    }

    private static JPanel createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.setBackground(Color.WHITE);

        JLabel welcome = new JLabel("Welcome, John Doe!");
        welcome.setFont(new Font("Arial", Font.BOLD, 22));
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(welcome);
        content.add(Box.createVerticalStrut(20));
        content.add(createStatCards());
        content.add(Box.createVerticalStrut(30));
        content.add(createNotifications());
        return content;
    }

    private static JPanel createStatCards() {
        JPanel cards = new JPanel(new GridLayout(1, 3, 15, 15));
        cards.setOpaque(false);
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        cards.setAlignmentX(Component.LEFT_ALIGNMENT);

        cards.add(createStatCard("GPA", "3.75", new Color(100, 180, 100)));
        cards.add(createStatCard("Units", "48 / 56", new Color(80, 140, 220)));
        cards.add(createStatCard("Fees", "Paid", new Color(230, 150, 60)));
        return cards;
    }

    private static JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, SwingConstants.RIGHT);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private static JScrollPane createNotifications() {
        JTextArea notes = new JTextArea(
                "- Semester exams start on Monday, 8:00 AM, Main Hall.\n"
              + "- Library books are due this Friday.\n"
              + "- Event Programming: weekly activities due Sunday.\n"
              + "- Fee statement for this semester is available under Fees.");
        notes.setEditable(false);
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(notes);
        scroll.setBorder(BorderFactory.createTitledBorder("Notifications"));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        return scroll;
    }

    private static JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(230, 230, 235));
        footer.setBorder(new EmptyBorder(5, 15, 5, 15));
        footer.add(new JLabel("Status: Ready"), BorderLayout.WEST);
        footer.add(new JLabel("Student Portal v1.0"), BorderLayout.EAST);
        return footer;
    }
}
