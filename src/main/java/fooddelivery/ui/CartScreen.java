package fooddelivery.ui;

import fooddelivery.model.MenuItem;
import fooddelivery.model.Order;
import fooddelivery.service.OrderService;
import fooddelivery.user.Customer;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Cart / Checkout screen.
 * Shows items in the current cart, a special-instructions field,
 * the order total, and a Place Order button.
 */
public class CartScreen extends JPanel {

    private final JPanel  itemsPanel;
    private final JLabel  totalLabel;
    private final JTextArea instructionsArea;

    public CartScreen(MainFrame frame, OrderService orderService) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        // Centre scroll
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(AppColors.BG_DARK);
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JScrollPane scroll = new JScrollPane(itemsPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppColors.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        // Bottom panel: instructions + total + checkout
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(AppColors.BG_PANEL);
        bottom.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, AppColors.BORDER),
                BorderFactory.createEmptyBorder(16, 40, 20, 40)
        ));

        JLabel instrLabel = UIHelper.label("Special instructions (optional)", AppColors.FONT_SMALL, AppColors.TEXT_MUTED);
        instrLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(instrLabel);
        bottom.add(Box.createVerticalStrut(6));

        instructionsArea = new JTextArea(3, 40);
        instructionsArea.setFont(AppColors.FONT_BODY);
        instructionsArea.setBackground(AppColors.BG_CARD);
        instructionsArea.setForeground(AppColors.TEXT_PRIMARY);
        instructionsArea.setCaretColor(AppColors.TEXT_PRIMARY);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        JScrollPane instrScroll = new JScrollPane(instructionsArea);
        instrScroll.setBorder(null);
        instrScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        instrScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(instrScroll);
        bottom.add(Box.createVerticalStrut(14));

        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(AppColors.BG_PANEL);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        totalLabel = UIHelper.label("Total: $0.00", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY);
        JButton placeBtn = UIHelper.primaryButton("Place Order");
        placeBtn.setPreferredSize(new Dimension(160, 42));
        placeBtn.addActionListener(e -> placeOrder(frame, orderService));

        row.add(totalLabel, BorderLayout.WEST);
        row.add(placeBtn, BorderLayout.EAST);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottom.add(row);

        add(bottom, BorderLayout.SOUTH);
    }

    // refresh when back to main frame
    public void refresh(MainFrame frame) {
        Order cart = frame.getCurrentCart();
        itemsPanel.removeAll();

        if (cart.getItems().isEmpty()) {
            JLabel empty = UIHelper.label("Your cart is empty.", AppColors.FONT_BODY, AppColors.TEXT_MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemsPanel.add(empty);
        } else {
            JLabel heading = UIHelper.label("Your order", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY);
            heading.setAlignmentX(Component.LEFT_ALIGNMENT);
            heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            itemsPanel.add(heading);

            for (Map.Entry<MenuItem, Integer> entry : cart.getItems().entrySet()) {
                itemsPanel.add(buildItemRow(entry.getKey(), entry.getValue(), frame));
                itemsPanel.add(Box.createVerticalStrut(8));
            }
        }

        totalLabel.setText("Total: $" + String.format("%.2f", cart.getTotalPrice()));
        instructionsArea.setText(cart.getSpecialInstructions());
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private JPanel buildItemRow(MenuItem item, int qty, MainFrame frame) {
        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BorderLayout(10, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel info = new JPanel();
        info.setBackground(AppColors.BG_CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(UIHelper.label(item.getName(), AppColors.FONT_BODY, AppColors.TEXT_PRIMARY));
        info.add(UIHelper.label("x" + qty + "  =  $" + String.format("%.2f", item.getPrice() * qty),
                AppColors.FONT_SMALL, AppColors.TEXT_MUTED));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        right.setBackground(AppColors.BG_CARD);

        // Item quantity in cart
        JButton minus = UIHelper.secondaryButton("-");
        minus.setPreferredSize(new Dimension(44, 30));
        JLabel qtyLabel = UIHelper.label(String.valueOf(qty), AppColors.FONT_BODY, AppColors.TEXT_PRIMARY);
        qtyLabel.setPreferredSize(new Dimension(24, 30));
        qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton plus = UIHelper.primaryButton("+");
        plus.setPreferredSize(new Dimension(44, 30));

        minus.addActionListener(e -> {
            frame.getCurrentCart().updateItem(item, qty - 1);
            refresh(frame);
        });
        plus.addActionListener(e -> {
            frame.getCurrentCart().updateItem(item, qty + 1);
            refresh(frame);
        });

        JButton removeBtn = UIHelper.secondaryButton("Remove");
        removeBtn.setPreferredSize(new Dimension(80, 30));
        removeBtn.addActionListener(e -> {
            frame.getCurrentCart().removeItem(item);
            refresh(frame);
        });

        right.add(minus);
        right.add(qtyLabel);
        right.add(plus);
        right.add(Box.createHorizontalStrut(8));
        right.add(removeBtn);

        card.add(info, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);
        return card;
    }

    private void placeOrder(MainFrame frame, OrderService orderService) {
        Order cart = frame.getCurrentCart();
        if (cart.getItems().isEmpty()) {
            UIHelper.showError(this, "Your cart is empty.");
            return;
        }

        // Attach instructions & ID
        cart.setSpecialInstructions(instructionsArea.getText().trim());
        cart.setId("o" + System.currentTimeMillis());

        orderService.placeOrder(cart);

        // Save to customer history
        if (frame.getCurrentUser() instanceof Customer customer) {
            customer.getOrderHistory().add(cart);
        }

        UIHelper.showSuccess(this,
                "Order placed successfully!\nTotal: $" + String.format("%.2f", cart.getTotalPrice())
                + (cart.getSpecialInstructions().isBlank() ? "" : "\nNote: " + cart.getSpecialInstructions()));

        frame.resetCart();
        frame.showScreen(MainFrame.SCREEN_CUSTOMER_DASHBOARD);
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton back = UIHelper.secondaryButton("<- Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.goBack());
        bar.add(back);
        bar.add(UIHelper.label("Cart & Checkout", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        return bar;
    }
}
