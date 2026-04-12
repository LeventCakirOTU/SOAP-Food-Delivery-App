package fooddelivery.ui;

import fooddelivery.model.Order;
import fooddelivery.model.Restaurant;
import fooddelivery.service.OrderService;
import fooddelivery.service.RestaurantService;
import fooddelivery.service.UserService;
import fooddelivery.user.Customer;
import fooddelivery.user.Driver;
import fooddelivery.user.RestaurantOwner;
import fooddelivery.user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDashboardScreen extends JPanel {

    private final UserService       userService;
    private final RestaurantService restaurantService;
    private final OrderService      orderService;

    private DefaultTableModel usersModel;
    private DefaultTableModel restaurantsModel;
    private DefaultTableModel ordersModel;

    public AdminDashboardScreen(MainFrame frame) {
        this.userService       = frame.getUserService();
        this.restaurantService = frame.getRestaurantService();
        this.orderService      = frame.getOrderService();

        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(AppColors.BG_PANEL);
        tabs.setForeground(AppColors.TEXT_PRIMARY);
        tabs.setFont(AppColors.FONT_BODY);

        tabs.addTab("Users",       buildUsersTab(frame));
        tabs.addTab("Restaurants", buildRestaurantsTab());
        tabs.addTab("Orders",      buildOrdersTab());

        add(tabs, BorderLayout.CENTER);
    }

    // user tab

    private JPanel buildUsersTab(MainFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppColors.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        String[] cols = {"Name", "Email", "Role", "Status"};
        usersModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = styledTable(usersModel);
        refreshUsersTable();
        panel.add(styledScroll(table), BorderLayout.CENTER);

        // buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actions.setBackground(AppColors.BG_DARK);

        JButton deleteBtn  = UIHelper.secondaryButton("Delete User");
        JButton suspendBtn = UIHelper.secondaryButton("Suspend / Unsuspend");
        JButton resetBtn   = UIHelper.primaryButton("Reset Password");
        deleteBtn.setForeground(AppColors.ERROR);

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { UIHelper.showError(this, "Select a user first."); return; }
            String email = (String) usersModel.getValueAt(row, 1);
            User user = userService.findByEmail(email);
            if (user == null) return;
            if (user == frame.getCurrentUser()) {
                UIHelper.showError(this, "You cannot delete the currently logged-in admin.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Permanently delete \"" + user.getName() + "\"?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userService.removeUser(user.getId());
                refreshUsersTable();
                UIHelper.showSuccess(this, user.getName() + " deleted.");
            }
        });

        suspendBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { UIHelper.showError(this, "Select a user first."); return; }
            String email = (String) usersModel.getValueAt(row, 1);
            User user = userService.findByEmail(email);
            if (user == null) return;
            if (user == frame.getCurrentUser()) {
                UIHelper.showError(this, "You cannot suspend yourself."); return;
            }
            boolean nowSuspended = userService.toggleSuspend(user.getId());
            refreshUsersTable();
            UIHelper.showSuccess(this, user.getName() + (nowSuspended ? " suspended." : " unsuspended."));
        });

        resetBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { UIHelper.showError(this, "Select a user first."); return; }
            String email = (String) usersModel.getValueAt(row, 1);
            User user = userService.findByEmail(email);
            if (user == null) return;
            String newPass = JOptionPane.showInputDialog(this,
                    "Enter new password for " + user.getName() + ":",
                    "Reset Password", JOptionPane.PLAIN_MESSAGE);
            if (newPass != null && !newPass.trim().isEmpty()) {
                userService.resetPassword(user.getId(), newPass.trim());
                UIHelper.showSuccess(this, "Password reset for " + user.getName() + ".");
            }
        });

        actions.add(deleteBtn);
        actions.add(suspendBtn);
        actions.add(resetBtn);
        panel.add(actions, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshUsersTable() {
        usersModel.setRowCount(0);
        for (User u : userService.getAllUsers()) {
            usersModel.addRow(new Object[]{
                u.getName(),
                u.getEmail(),
                roleOf(u),
                userService.isSuspended(u.getId()) ? "Suspended" : "Active"
            });
        }
    }

    // restaurant tab

    private JPanel buildRestaurantsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppColors.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        String[] cols = {"Name", "Category", "Address", "Hours", "Rating"};
        restaurantsModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = styledTable(restaurantsModel);
        refreshRestaurantsTable();
        panel.add(styledScroll(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actions.setBackground(AppColors.BG_DARK);

        JButton removeBtn  = UIHelper.secondaryButton("Remove Restaurant");
        JButton refreshBtn = UIHelper.secondaryButton("Refresh");
        removeBtn.setForeground(AppColors.ERROR);

        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { UIHelper.showError(this, "Select a restaurant first."); return; }
            String name = (String) restaurantsModel.getValueAt(row, 0);
            Restaurant r = restaurantService.findByName(name);
            if (r == null) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Permanently remove \"" + name + "\" and all its menu items?",
                    "Confirm Remove", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                restaurantService.removeRestaurant(r.getId());
                refreshRestaurantsTable();
                UIHelper.showSuccess(this, "\"" + name + "\" removed.");
            }
        });

        refreshBtn.addActionListener(e -> refreshRestaurantsTable());

        actions.add(removeBtn);
        actions.add(refreshBtn);
        panel.add(actions, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshRestaurantsTable() {
        restaurantsModel.setRowCount(0);
        for (Restaurant r : restaurantService.getAll()) {
            restaurantsModel.addRow(new Object[]{
                r.getName(),
                r.getCategory(),
                r.getAddress(),
                r.getOpenHours(),
                String.format("%.1f", r.getAverageRating())
            });
        }
    }

    // orders tab

    private JPanel buildOrdersTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppColors.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        String[] cols = {"Order #", "Customer ID", "Restaurant ID", "Items", "Total", "Status"};
        ordersModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = styledTable(ordersModel);
        refreshOrdersTable();
        panel.add(styledScroll(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actions.setBackground(AppColors.BG_DARK);

        JButton cancelBtn  = UIHelper.secondaryButton("Cancel Order");
        JButton refreshBtn = UIHelper.secondaryButton("Refresh");
        cancelBtn.setForeground(AppColors.ERROR);

        cancelBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { UIHelper.showError(this, "Select an order first."); return; }
            String orderId = (String) ordersModel.getValueAt(row, 0);
            String status  = (String) ordersModel.getValueAt(row, 5);
            if ("CANCELLED".equals(status) || "DELIVERED".equals(status)) {
                UIHelper.showError(this, "Cannot cancel an order that is already " + status + ".");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Cancel order #" + orderId + "?",
                    "Confirm Cancel", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                orderService.cancelOrder(orderId);
                refreshOrdersTable();
                UIHelper.showSuccess(this, "Order #" + orderId + " cancelled.");
            }
        });

        refreshBtn.addActionListener(e -> refreshOrdersTable());

        actions.add(cancelBtn);
        actions.add(refreshBtn);
        panel.add(actions, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshOrdersTable() {
        ordersModel.setRowCount(0);
        for (Order o : orderService.getAllOrders()) {
            int itemCount = o.getItems() == null ? 0 :
                    o.getItems().values().stream().mapToInt(i -> i).sum();
            ordersModel.addRow(new Object[]{
                o.getId() != null ? o.getId() : "-",
                o.getCustomerId()   != null ? o.getCustomerId()   : "-",
                o.getRestaurantId() != null ? o.getRestaurantId() : "-",
                itemCount + " item(s)",
                "$" + String.format("%.2f", o.getTotalPrice()),
                o.getStatus() != null ? o.getStatus() : "PENDING"
            });
        }
    }

    // helpers

    private JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(AppColors.BG_CARD);
        table.setForeground(AppColors.TEXT_PRIMARY);
        table.setFont(AppColors.FONT_BODY);
        table.setRowHeight(30);
        table.setSelectionBackground(AppColors.ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(AppColors.BORDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(AppColors.BG_PANEL);
        table.getTableHeader().setForeground(AppColors.TEXT_PRIMARY);
        table.getTableHeader().setFont(AppColors.FONT_BODY);
        return table;
    }

    private JScrollPane styledScroll(JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(AppColors.BORDER));
        scroll.getViewport().setBackground(AppColors.BG_CARD);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private String roleOf(User u) {
        if (u instanceof Customer)        return "Customer";
        if (u instanceof Driver)          return "Driver";
        if (u instanceof RestaurantOwner) return "Manager";
        if (u instanceof fooddelivery.user.Admin) return "Admin";
        return "Unknown";
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton logout = UIHelper.secondaryButton("Logout");
        logout.setPreferredSize(new Dimension(90, 34));
        logout.addActionListener(e -> {
            frame.setCurrentUser(null);
            frame.showScreen(MainFrame.SCREEN_LANDING);
        });
        bar.add(UIHelper.label("Admin", AppColors.FONT_HEADING, AppColors.ACCENT));
        bar.add(Box.createHorizontalGlue());
        bar.add(logout);
        return bar;
    }
}
