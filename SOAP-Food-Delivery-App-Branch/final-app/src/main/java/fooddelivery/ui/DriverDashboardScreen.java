package fooddelivery.ui;

import fooddelivery.model.DeliveryTask;
import fooddelivery.model.Order;
import fooddelivery.service.DeliveryService;
import fooddelivery.service.OrderService;
import fooddelivery.user.Driver;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DriverDashboardScreen extends JPanel {

    private final OrderService    orderService;
    private final DeliveryService deliveryService;
    private       JLabel          availabilityLabel;
    private       JButton         toggleAvailBtn;

    public DriverDashboardScreen(MainFrame frame, OrderService orderService, DeliveryService deliveryService) {
        this.orderService    = orderService;
        this.deliveryService = deliveryService;

        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(AppColors.BG_DARK);

        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(520, 340));

        JLabel title = UIHelper.label("Driver Dashboard", AppColors.FONT_TITLE, AppColors.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // driver availabilty
        JPanel availRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        availRow.setBackground(AppColors.BG_CARD);
        availabilityLabel = UIHelper.label("Status: Offline", AppColors.FONT_BODY, AppColors.ERROR);
        toggleAvailBtn = UIHelper.secondaryButton("Go Online");
        toggleAvailBtn.setPreferredSize(new Dimension(120, 32));
        toggleAvailBtn.addActionListener(e -> toggleAvailability(frame));
        availRow.add(availabilityLabel);
        availRow.add(toggleAvailBtn);

        JButton availableBtn = UIHelper.primaryButton("View Available Orders");
        availableBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        availableBtn.addActionListener(e -> showAvailableOrders(frame));

        JButton statusBtn = UIHelper.secondaryButton("Update Delivery Status");
        statusBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBtn.addActionListener(e -> showUpdateStatusDialog(frame));

        card.add(title);
        card.add(Box.createVerticalStrut(16));
        card.add(availRow);
        card.add(Box.createVerticalStrut(24));
        card.add(availableBtn);
        card.add(Box.createVerticalStrut(12));
        card.add(statusBtn);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    // reflects current driver state
    public void refresh(MainFrame frame) {
        if (frame.getCurrentUser() instanceof Driver driver) {
            updateAvailUI(driver.isAvailable());
        }
    }

    private void toggleAvailability(MainFrame frame) {
        if (!(frame.getCurrentUser() instanceof Driver driver)) return;
        driver.setAvailable(!driver.isAvailable());
        updateAvailUI(driver.isAvailable());
    }

    private void updateAvailUI(boolean online) {
        if (online) {
            availabilityLabel.setText("Status: Online");
            availabilityLabel.setForeground(AppColors.SUCCESS);
            toggleAvailBtn.setText("Go Offline");
        } else {
            availabilityLabel.setText("Status: Offline");
            availabilityLabel.setForeground(AppColors.ERROR);
            toggleAvailBtn.setText("Go Online");
        }
    }

    // Avaiable orders - dialog

    private void showAvailableOrders(MainFrame frame) {
        List<Order> orders = orderService.getAllOrders();

        JDialog dialog = new JDialog(frame, "Available Orders", true);
        dialog.setSize(520, 400);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(AppColors.BG_DARK);
        dialog.setLayout(new BorderLayout());

        JLabel heading = UIHelper.label("Available Orders", AppColors.FONT_HEADING, AppColors.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));
        dialog.add(heading, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(AppColors.BG_DARK);
        listPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));

        if (orders.isEmpty()) {
            listPanel.add(UIHelper.label("No orders available right now.", AppColors.FONT_BODY, AppColors.TEXT_MUTED));
        } else {
            for (Order o : orders) {
                listPanel.add(buildOrderCard(o, frame, dialog));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppColors.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        dialog.add(scroll, BorderLayout.CENTER);

        JButton closeBtn = UIHelper.secondaryButton("Close");
        closeBtn.setPreferredSize(new Dimension(100, 36));
        closeBtn.addActionListener(e -> dialog.dispose());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        bottom.setBackground(AppColors.BG_DARK);
        bottom.add(closeBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel buildOrderCard(Order order, MainFrame frame, JDialog parent) {
        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BorderLayout(10, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel info = new JPanel();
        info.setBackground(AppColors.BG_CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        String itemCount = order.getItems() != null ? order.getItems().size() + " item(s)" : "0 items";
        info.add(UIHelper.label("Order #" + order.getId(), AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        info.add(UIHelper.label(itemCount + "  •  $" + String.format("%.2f", order.getTotalPrice()),
                AppColors.FONT_SMALL, AppColors.TEXT_MUTED));

        // Show special instructions if present
        if (order.getSpecialInstructions() != null && !order.getSpecialInstructions().isBlank()) {
            info.add(UIHelper.label("Note: " + order.getSpecialInstructions(),
                    AppColors.FONT_SMALL, new Color(255, 200, 50)));
        }

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setBackground(AppColors.BG_CARD);
        right.add(UIHelper.label(order.getStatus() != null ? order.getStatus() : "PENDING",
                AppColors.FONT_SMALL, statusColor(order.getStatus())));

        if ("PENDING".equals(order.getStatus())) {
            // accept
            JButton acceptBtn = UIHelper.primaryButton("Accept");
            acceptBtn.setPreferredSize(new Dimension(90, 32));
            acceptBtn.addActionListener(e -> {
                DeliveryTask task = new DeliveryTask();
                task.setId("t" + System.currentTimeMillis());
                task.setOrderId(order.getId());
                if (frame.getCurrentUser() != null) task.setDriverId(frame.getCurrentUser().getId());
                task.setStatus("PICKED_UP");
                deliveryService.assignTask(task);
                order.setStatus("IN_PROGRESS");
                UIHelper.showSuccess(this, "Order #" + order.getId() + " accepted!");
                parent.dispose();
            });

            // Reject
            JButton rejectBtn = UIHelper.secondaryButton("Reject");
            rejectBtn.setPreferredSize(new Dimension(90, 32));
            rejectBtn.addActionListener(e -> {
                order.setStatus("CANCELLED");
                UIHelper.showSuccess(this, "Order #" + order.getId() + " rejected.");
                parent.dispose();
            });

            right.add(rejectBtn);
            right.add(acceptBtn);
        }

        card.add(info, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);
        return card;
    }

    // update status dialog
    private void showUpdateStatusDialog(MainFrame frame) {
        List<DeliveryTask> tasks = deliveryService.getAllTasks();

        JDialog dialog = new JDialog(frame, "Update Delivery Status", true);
        dialog.setSize(420, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(AppColors.BG_DARK);
        dialog.setLayout(new BorderLayout());

        JLabel heading = UIHelper.label("Update Delivery Status", AppColors.FONT_HEADING, AppColors.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));
        dialog.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(AppColors.BG_DARK);
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        if (tasks.isEmpty()) {
            form.add(UIHelper.label("No active delivery tasks found.", AppColors.FONT_BODY, AppColors.TEXT_MUTED));
            dialog.add(form, BorderLayout.CENTER);
        } else {
            String[] taskLabels = tasks.stream()
                    .map(t -> "Task #" + t.getId() + " — Order #" + t.getOrderId())
                    .toArray(String[]::new);
            JComboBox<String> taskSelector = new JComboBox<>(taskLabels);
            taskSelector.setBackground(AppColors.BG_CARD);
            taskSelector.setForeground(AppColors.TEXT_PRIMARY);
            taskSelector.setFont(AppColors.FONT_BODY);
            taskSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            String[] statuses = {"PICKED_UP", "IN_TRANSIT", "DELIVERED", "CANCELLED"};
            JComboBox<String> statusSelector = new JComboBox<>(statuses);
            statusSelector.setBackground(AppColors.BG_CARD);
            statusSelector.setForeground(AppColors.TEXT_PRIMARY);
            statusSelector.setFont(AppColors.FONT_BODY);
            statusSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            form.add(UIHelper.label("Select Task", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
            form.add(Box.createVerticalStrut(6));
            form.add(taskSelector);
            form.add(Box.createVerticalStrut(16));
            form.add(UIHelper.label("New Status", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
            form.add(Box.createVerticalStrut(6));
            form.add(statusSelector);
            dialog.add(form, BorderLayout.CENTER);

            JButton confirmBtn = UIHelper.primaryButton("Update Status");
            confirmBtn.setPreferredSize(new Dimension(140, 36));
            confirmBtn.addActionListener(e -> {
                int idx = taskSelector.getSelectedIndex();
                String newStatus = (String) statusSelector.getSelectedItem();
                DeliveryTask selected = tasks.get(idx);
                deliveryService.updateTaskStatus(selected.getId(), newStatus);
                if ("DELIVERED".equals(newStatus)) {
                    Order linked = orderService.getOrder(selected.getOrderId());
                    if (linked != null) linked.setStatus("DELIVERED");
                }
                UIHelper.showSuccess(this, "Task #" + selected.getId() + " → " + newStatus);
                dialog.dispose();
            });

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
            bottom.setBackground(AppColors.BG_DARK);
            JButton cancelBtn = UIHelper.secondaryButton("Cancel");
            cancelBtn.setPreferredSize(new Dimension(90, 36));
            cancelBtn.addActionListener(e -> dialog.dispose());
            bottom.add(cancelBtn);
            bottom.add(confirmBtn);
            dialog.add(bottom, BorderLayout.SOUTH);
        }

        dialog.setVisible(true);
    }

    private Color statusColor(String status) {
        if (status == null) return AppColors.TEXT_MUTED;
        return switch (status) {
            case "PENDING"    -> new Color(255, 200, 50);
            case "IN_PROGRESS", "PICKED_UP", "IN_TRANSIT" -> AppColors.ACCENT;
            case "DELIVERED"  -> AppColors.SUCCESS;
            case "CANCELLED"  -> AppColors.ERROR;
            default           -> AppColors.TEXT_MUTED;
        };
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton logout = UIHelper.secondaryButton("Logout");
        logout.setPreferredSize(new Dimension(90, 34));
        logout.addActionListener(e -> { frame.setCurrentUser(null); frame.showScreen(MainFrame.SCREEN_LANDING); });
        bar.add(UIHelper.label("Driver", AppColors.FONT_HEADING, AppColors.ACCENT));
        bar.add(Box.createHorizontalGlue());
        bar.add(logout);
        return bar;
    }
}
