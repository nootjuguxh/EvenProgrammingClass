/*
 * Week 4 - Activity 2: Pizza Order (State changes with ItemListener)
 * Objective: Handle state changes in JCheckBox, JRadioButton (+ButtonGroup) and
 *            JComboBox using ItemListener / ActionListener, and recalculate the
 *            total in real time. State is kept in instance variables / fields.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Activity02PizzaOrder extends JFrame {

    // --- State / model ---
    private static final String[] TOPPING_NAMES = {"Cheese", "Pepperoni", "Mushrooms", "Olives"};
    private static final int[] TOPPING_PRICES = {100, 150, 120, 80};
    private static final String[] SIZE_NAMES = {"Small", "Medium", "Large"};
    private static final int[] SIZE_PRICES = {800, 1000, 1200};
    private static final String[] CRUST_NAMES = {"Thin", "Thick", "Stuffed"};
    private static final int[] CRUST_PRICES = {0, 100, 200};

    // --- UI components ---
    private final JCheckBox[] toppingBoxes = new JCheckBox[TOPPING_NAMES.length];
    private final JRadioButton[] sizeButtons = new JRadioButton[SIZE_NAMES.length];
    private final JComboBox<String> crustBox = new JComboBox<>(CRUST_NAMES);
    private final JLabel totalLabel = new JLabel("Total: KES 0", SwingConstants.CENTER);
    private final JTextArea summary = new JTextArea(6, 20);

    public Activity02PizzaOrder() {
        super("Pizza Order");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        ItemListener recalc = e -> updateTotal(); // lambda: ItemListener is functional

        // Toppings (independent toggles)
        JPanel toppingPanel = new JPanel(new GridLayout(0, 1));
        toppingPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        for (int i = 0; i < TOPPING_NAMES.length; i++) {
            toppingBoxes[i] = new JCheckBox(TOPPING_NAMES[i] + " (+" + TOPPING_PRICES[i] + ")");
            toppingBoxes[i].addItemListener(recalc);
            toppingPanel.add(toppingBoxes[i]);
        }

        // Sizes (mutually exclusive -> ButtonGroup)
        JPanel sizePanel = new JPanel(new GridLayout(0, 1));
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
        ButtonGroup sizeGroup = new ButtonGroup();
        for (int i = 0; i < SIZE_NAMES.length; i++) {
            sizeButtons[i] = new JRadioButton(SIZE_NAMES[i] + " (" + SIZE_PRICES[i] + ")");
            sizeButtons[i].addItemListener(recalc);
            sizeGroup.add(sizeButtons[i]);
            sizePanel.add(sizeButtons[i]);
        }
        sizeButtons[0].setSelected(true);

        // Crust (combo box fires ActionEvent when a new item is chosen)
        JPanel crustPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust"));
        crustBox.addActionListener(e -> updateTotal());
        crustPanel.add(crustBox);

        JPanel options = new JPanel(new GridLayout(1, 3, 10, 10));
        options.add(toppingPanel);
        options.add(sizePanel);
        options.add(crustPanel);

        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        summary.setEditable(false);

        JButton order = new JButton("Place Order");
        order.addActionListener(e -> JOptionPane.showMessageDialog(this,
                summary.getText() + "\n" + totalLabel.getText(),
                "Order placed", JOptionPane.INFORMATION_MESSAGE));

        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.add(totalLabel, BorderLayout.NORTH);
        bottom.add(new JScrollPane(summary), BorderLayout.CENTER);
        bottom.add(order, BorderLayout.SOUTH);

        add(options, BorderLayout.NORTH);
        add(bottom, BorderLayout.CENTER);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        updateTotal();
        pack();
        setLocationRelativeTo(null);
    }

    /** Read state from the GUI -> compute -> update the GUI. */
    private void updateTotal() {
        int total = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sizeButtons.length; i++) {
            if (sizeButtons[i].isSelected()) {
                total += SIZE_PRICES[i];
                sb.append("Size: ").append(SIZE_NAMES[i]).append('\n');
            }
        }
        int crust = crustBox.getSelectedIndex();
        total += CRUST_PRICES[crust];
        sb.append("Crust: ").append(CRUST_NAMES[crust]).append('\n');

        sb.append("Toppings: ");
        boolean any = false;
        for (int i = 0; i < toppingBoxes.length; i++) {
            if (toppingBoxes[i].isSelected()) {
                total += TOPPING_PRICES[i];
                sb.append(any ? ", " : "").append(TOPPING_NAMES[i]);
                any = true;
            }
        }
        if (!any) sb.append("none");

        summary.setText(sb.toString());
        totalLabel.setText("Total: KES " + total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Activity02PizzaOrder().setVisible(true));
    }
}
