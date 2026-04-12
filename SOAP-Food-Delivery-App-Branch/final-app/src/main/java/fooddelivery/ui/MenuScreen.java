package fooddelivery.ui;

import fooddelivery.model.MenuItem;
import fooddelivery.model.Order;
import fooddelivery.model.Restaurant;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Menu screen shows categorised items, availability badges, and
 * for each item an "Add to Cart" button. Cart count updates live.
 */
public class MenuScreen extends JPanel {

    private final JLabel  restaurantTitle;
    private final JLabel  restaurantDetails;
    private final JPanel  menuPanel;
    private       JButton cartBtn;
    private       Restaurant currentRestaurant;

    public MenuScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(AppColors.BG_PANEL);
        header.setBorder(BorderFactory.createEmptyBorder(16, 32, 16, 32));

        restaurantTitle   = UIHelper.label("", AppColors.FONT_TITLE, AppColors.TEXT_PRIMARY);
        restaurantDetails = UIHelper.label("", AppColors.FONT_SMALL, AppColors.TEXT_MUTED);
        header.add(restaurantTitle);
        header.add(Box.createVerticalStrut(4));
        header.add(restaurantDetails);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(AppColors.BG_PANEL);
        northPanel.add(buildTopBar(frame), BorderLayout.NORTH);
        northPanel.add(header, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(AppColors.BG_DARK);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 32, 20, 32));

        JScrollPane scroll = new JScrollPane(menuPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppColors.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    public void loadRestaurant(Restaurant restaurant) {
        this.currentRestaurant = restaurant;
        restaurantTitle.setText(restaurant.getName());
        restaurantDetails.setText(
                restaurant.getCategory() + "  •  " + restaurant.getAddress()
                        + "  •  " + restaurant.getOpenHours()
                        + "  •  ★ " + String.format("%.1f", restaurant.getAverageRating())
        );

        menuPanel.removeAll();
        List<MenuItem> items = restaurant.getMenu().getItems();

        if (items.isEmpty()) {
            JLabel empty = UIHelper.label("No menu items available yet.", AppColors.FONT_BODY, AppColors.TEXT_MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuPanel.add(empty);
        } else {
            LinkedHashMap<String, List<MenuItem>> grouped = new LinkedHashMap<>();
            for (MenuItem item : items) {
                String cat = (item.getCategory() != null && !item.getCategory().isBlank())
                        ? item.getCategory() : "Other";
                grouped.computeIfAbsent(cat, k -> new ArrayList<>()).add(item);
            }
            for (Map.Entry<String, List<MenuItem>> entry : grouped.entrySet()) {
                menuPanel.add(buildCategoryHeader(entry.getKey()));
                menuPanel.add(Box.createVerticalStrut(8));
                for (MenuItem item : entry.getValue()) {
                    menuPanel.add(buildMenuItemCard(item, getFrame()));
                    menuPanel.add(Box.createVerticalStrut(8));
                }
                menuPanel.add(Box.createVerticalStrut(16));
            }
        }
        menuPanel.revalidate();
        menuPanel.repaint();
        refreshCartBadge();
    }

    // Called from MainFrame after cart changes
    public void refreshCartBadge() {
        if (cartBtn == null) return;
        MainFrame f = getFrame();
        if (f == null) return;
        int count = f.getCurrentCart().totalItemCount();
        cartBtn.setText(count > 0 ? "Cart (" + count + ")" : "Cart");
    }

    private MainFrame getFrame() {
        Container c = getParent();
        while (c != null && !(c instanceof MainFrame)) c = c.getParent();
        return (MainFrame) c;
    }

    private JLabel buildCategoryHeader(String category) {
        JLabel label = UIHelper.label(category.toUpperCase(), AppColors.FONT_HEADING, AppColors.ACCENT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        return label;
    }

    private JPanel buildMenuItemCard(MenuItem item, MainFrame frame) {
        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BorderLayout(12, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left: name + description
        JPanel info = new JPanel();
        info.setBackground(AppColors.BG_CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(UIHelper.label(item.getName(), AppColors.FONT_BODY, AppColors.TEXT_PRIMARY));
        if (item.getDescription() != null && !item.getDescription().isBlank())
            info.add(UIHelper.label(item.getDescription(), AppColors.FONT_SMALL, AppColors.TEXT_MUTED));

        // Availability badge
        JLabel avail = UIHelper.label(item.isAvailable() ? "Available" : "Unavailable",
                AppColors.FONT_SMALL,
                item.isAvailable() ? AppColors.SUCCESS : AppColors.ERROR);
        avail.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        info.add(avail);

        // Prep time badge
        if (item.getPreparationTime() > 0) {
            JLabel prep = UIHelper.label("⏱ " + item.getPreparationTime() + " min prep",
                    AppColors.FONT_SMALL, AppColors.TEXT_MUTED);
            prep.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
            info.add(prep);
        }

        // Right: price + add button
        JPanel right = new JPanel();
        right.setBackground(AppColors.BG_CARD);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel price = UIHelper.label("$" + String.format("%.2f", item.getPrice()),
                AppColors.FONT_HEADING, AppColors.ACCENT);
        price.setAlignmentX(Component.RIGHT_ALIGNMENT);
        price.setBorder(BorderFactory.createEmptyBorder(0, 12, 6, 4));

        JButton addBtn = UIHelper.primaryButton("+ Add");
        addBtn.setPreferredSize(new Dimension(90, 30));
        addBtn.setMaximumSize(new Dimension(90, 30));
        addBtn.setEnabled(item.isAvailable());
        addBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        addBtn.addActionListener(e -> {
            if (frame == null) return;
            // If cart is from a different restaurant, warn and clear
            Order cart = frame.getCurrentCart();
            if (currentRestaurant != null
                    && cart.getRestaurantId() != null
                    && !cart.getRestaurantId().equals(currentRestaurant.getId())
                    && !cart.getItems().isEmpty()) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "Your cart contains items from another restaurant.\nClear cart and start a new order?",
                        "New Restaurant", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) return;
                frame.resetCart();
                cart = frame.getCurrentCart();
            }
            if (currentRestaurant != null) {
                cart.setRestaurantId(currentRestaurant.getId());
                if (frame.getCurrentUser() != null)
                    cart.setCustomerId(frame.getCurrentUser().getId());
            }
            cart.addItem(item, 1);
            refreshCartBadge();
            UIHelper.showSuccess(this, item.getName() + " added to cart!");
        });

        right.add(price);
        right.add(addBtn);

        card.add(info, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);
        return card;
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));

        JButton back = UIHelper.secondaryButton("<- Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_RESTAURANT_LIST));

        cartBtn = UIHelper.primaryButton("Cart");
        cartBtn.setPreferredSize(new Dimension(110, 34));
        cartBtn.addActionListener(e -> {
            frame.setPreviousScreen(MainFrame.SCREEN_MENU);
            frame.getCartScreen().refresh(frame);
            frame.showScreen(MainFrame.SCREEN_CART);
        });

        bar.add(back);
        bar.add(UIHelper.label("Menu", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        bar.add(Box.createHorizontalGlue());
        bar.add(cartBtn);
        return bar;
    }
}
