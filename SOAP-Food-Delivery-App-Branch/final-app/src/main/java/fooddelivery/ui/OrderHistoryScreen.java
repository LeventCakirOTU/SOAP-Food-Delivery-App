package fooddelivery.ui;

import fooddelivery.model.MenuItem;
import fooddelivery.model.Order;
import fooddelivery.model.Rating;
import fooddelivery.service.RatingService;
import fooddelivery.user.Customer;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Shows a customer's past orders.
 * Delivered orders can be rated (once per order).
 */
public class OrderHistoryScreen extends JPanel {

    private final JPanel      listPanel;
    private final RatingService ratingService;

    public OrderHistoryScreen(MainFrame frame, RatingService ratingService) {
        this.ratingService = ratingService;
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(AppColors.BG_DARK);
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppColors.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    /** Call whenever navigating to this screen */
    public void refresh(MainFrame frame) {
        listPanel.removeAll();

        if (!(frame.getCurrentUser() instanceof Customer customer)) {
            listPanel.add(UIHelper.label("Not logged in as a customer.", AppColors.FONT_BODY, AppColors.TEXT_MUTED));
            listPanel.revalidate();
            listPanel.repaint();
            return;
        }

        List<Order> history = customer.getOrderHistory();
        if (history.isEmpty()) {
            JLabel empty = UIHelper.label("No orders yet. Start browsing!", AppColors.FONT_BODY, AppColors.TEXT_MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPanel.add(empty);
        } else {
            // Most recent first
            for (int i = history.size() - 1; i >= 0; i--) {
                listPanel.add(buildOrderCard(history.get(i), frame));
                listPanel.add(Box.createVerticalStrut(12));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildOrderCard(Order order, MainFrame frame) {
        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BorderLayout(10, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left: order summary
        JPanel info = new JPanel();
        info.setBackground(AppColors.BG_CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        info.add(UIHelper.label("Order #" + order.getId(), AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        info.add(Box.createVerticalStrut(4));

        // Items list
        for (Map.Entry<MenuItem, Integer> entry : order.getItems().entrySet()) {
            info.add(UIHelper.label("  " + entry.getValue() + "x " + entry.getKey().getName(),
                    AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        }

        info.add(Box.createVerticalStrut(6));
        info.add(UIHelper.label("Total: $" + String.format("%.2f", order.getTotalPrice()),
                AppColors.FONT_BODY, AppColors.ACCENT));

        if (order.getSpecialInstructions() != null && !order.getSpecialInstructions().isBlank()) {
            info.add(UIHelper.label("Note: " + order.getSpecialInstructions(),
                    AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        }

        // Right: status + rate button
        JPanel right = new JPanel();
        right.setBackground(AppColors.BG_CARD);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setAlignmentY(Component.TOP_ALIGNMENT);

        String status = order.getStatus() != null ? order.getStatus() : "PENDING";
        JLabel statusLbl = UIHelper.label(status, AppColors.FONT_SMALL, statusColor(status));
        statusLbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
        right.add(statusLbl);

        // Show Rate button for delivered, not-yet-rated orders
        if ("DELIVERED".equals(status) && !ratingService.hasRated(order.getId())) {
            right.add(Box.createVerticalStrut(8));
            JButton rateBtn = UIHelper.primaryButton("Rate");
            rateBtn.setPreferredSize(new Dimension(90, 32));
            rateBtn.setMaximumSize(new Dimension(90, 32));
            rateBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
            rateBtn.addActionListener(e -> showRatingDialog(order, frame));
            right.add(rateBtn);
        }

        card.add(info, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);
        return card;
    }

    private void showRatingDialog(Order order, MainFrame frame) {
        JDialog dialog = new JDialog(frame, "Rate your order", true);
        dialog.setSize(380, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(AppColors.BG_DARK);
        dialog.setLayout(new BorderLayout());

        JLabel heading = UIHelper.label("Rate Order #" + order.getId(), AppColors.FONT_HEADING, AppColors.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));
        dialog.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(AppColors.BG_DARK);
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Star selector 1-5
        form.add(UIHelper.label("Stars (1-5)", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        form.add(Box.createVerticalStrut(6));
        Integer[] starsOptions = {1, 2, 3, 4, 5};
        JComboBox<Integer> starsBox = new JComboBox<>(starsOptions);
        starsBox.setSelectedIndex(4); // default 5 stars
        starsBox.setBackground(AppColors.BG_CARD);
        starsBox.setForeground(AppColors.TEXT_PRIMARY);
        starsBox.setFont(AppColors.FONT_BODY);
        starsBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        form.add(starsBox);
        form.add(Box.createVerticalStrut(14));

        form.add(UIHelper.label("Comment (optional)", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        form.add(Box.createVerticalStrut(6));
        JTextArea commentArea = new JTextArea(3, 30);
        commentArea.setFont(AppColors.FONT_BODY);
        commentArea.setBackground(AppColors.BG_CARD);
        commentArea.setForeground(AppColors.TEXT_PRIMARY);
        commentArea.setCaretColor(AppColors.TEXT_PRIMARY);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        JScrollPane cs = new JScrollPane(commentArea);
        cs.setBorder(null);
        cs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        form.add(cs);

        dialog.add(form, BorderLayout.CENTER);

        JButton submitBtn = UIHelper.primaryButton("Submit Rating");
        submitBtn.setPreferredSize(new Dimension(150, 36));
        submitBtn.addActionListener(e -> {
            Rating r = new Rating();
            r.setId("r" + System.currentTimeMillis());
            r.setOrderId(order.getId());
            r.setCustomerId(order.getCustomerId());
            r.setRestaurantId(order.getRestaurantId());
            r.setStars((Integer) starsBox.getSelectedItem());
            r.setComment(commentArea.getText().trim());
            ratingService.addRating(r);
            UIHelper.showSuccess(this, "Thanks for your rating!");
            dialog.dispose();
            refresh(frame);   // re-render so Rate button disappears
        });

        JButton cancelBtn = UIHelper.secondaryButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(90, 36));
        cancelBtn.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        bottom.setBackground(AppColors.BG_DARK);
        bottom.add(cancelBtn);
        bottom.add(submitBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private Color statusColor(String status) {
        if (status == null) return AppColors.TEXT_MUTED;
        return switch (status) {
            case "PENDING", "PLACED"     -> new Color(255, 200, 50);
            case "IN_PROGRESS", "PICKED_UP", "IN_TRANSIT" -> AppColors.ACCENT;
            case "DELIVERED"             -> AppColors.SUCCESS;
            case "CANCELLED"             -> AppColors.ERROR;
            default                      -> AppColors.TEXT_MUTED;
        };
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton back = UIHelper.secondaryButton("<- Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_CUSTOMER_DASHBOARD));
        bar.add(back);
        bar.add(UIHelper.label("Order History", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        return bar;
    }
}
