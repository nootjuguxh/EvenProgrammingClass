/*
 * Week 4 - Activity 4: Mouse and Keyboard Events
 * Objective: Use MouseAdapter / MouseMotionAdapter for freehand drawing and
 *            KeyAdapter for moving a square with the arrow keys.
 * Adapter classes let us override only the methods we need.
 * Key events need focus: setFocusable(true) + requestFocusInWindow().
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Activity04MouseAndKeyboard {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity04MouseAndKeyboard::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Mouse & Keyboard Events");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawPanel draw = new DrawPanel();
        JButton clear = new JButton("Clear drawing");
        clear.addActionListener(e -> draw.clear());

        JPanel left = new JPanel(new BorderLayout());
        left.setBorder(BorderFactory.createTitledBorder("Drag the mouse to draw"));
        left.add(draw, BorderLayout.CENTER);
        left.add(clear, BorderLayout.SOUTH);

        GamePanel game = new GamePanel();
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Click, then use the arrow keys"));
        right.add(game, BorderLayout.CENTER);

        JPanel root = new JPanel(new GridLayout(1, 2, 10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(left);
        root.add(right);

        frame.add(root);
        frame.setSize(820, 460);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** Freehand drawing with MouseListener + MouseMotionListener. */
    static class DrawPanel extends JPanel {
        private final ArrayList<ArrayList<Point>> strokes = new ArrayList<>();
        private ArrayList<Point> current;

        DrawPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    current = new ArrayList<>();
                    current.add(e.getPoint());
                    strokes.add(current);
                    repaint();
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (current != null) {
                        current.add(e.getPoint());
                        repaint();
                    }
                }
            });
        }

        void clear() {
            strokes.clear();
            current = null;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(25, 55, 115));
            for (ArrayList<Point> stroke : strokes) {
                if (stroke.size() == 1) {
                    Point p = stroke.get(0);
                    g2.fillOval(p.x - 2, p.y - 2, 4, 4);
                }
                for (int i = 1; i < stroke.size(); i++) {
                    Point a = stroke.get(i - 1);
                    Point b = stroke.get(i);
                    g2.drawLine(a.x, a.y, b.x, b.y);
                }
            }
        }
    }

    /** Move a square with the arrow keys using KeyListener. */
    static class GamePanel extends JPanel {
        private int x = 40, y = 40;
        private static final int SIZE = 30, STEP = 10;
        private boolean focused = false;

        GamePanel() {
            setBackground(new Color(245, 245, 250));
            setFocusable(true); // required to receive key events

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    requestFocusInWindow(); // click to give the panel focus
                }
            });
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) { focused = true; repaint(); }
                @Override
                public void focusLost(FocusEvent e) { focused = false; repaint(); }
            });
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:    y -= STEP; break;
                        case KeyEvent.VK_DOWN:  y += STEP; break;
                        case KeyEvent.VK_LEFT:  x -= STEP; break;
                        case KeyEvent.VK_RIGHT: x += STEP; break;
                        default: return;
                    }
                    x = Math.max(0, Math.min(x, getWidth() - SIZE));
                    y = Math.max(0, Math.min(y, getHeight() - SIZE));
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(200, 60, 60));
            g.fillRect(x, y, SIZE, SIZE);
            g.setColor(Color.DARK_GRAY);
            g.drawString(focused ? "Focused - use the arrow keys" : "Click here to focus", 10, getHeight() - 10);
        }
    }
}
