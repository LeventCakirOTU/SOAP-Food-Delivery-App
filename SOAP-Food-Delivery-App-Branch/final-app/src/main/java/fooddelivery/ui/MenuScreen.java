package fooddelivery.ui;

import fooddelivery.model.MenuItem;
import fooddelivery.model.Restaurant;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuScreen extends JPanel {

    private final JLabel  restaurantTitle;
    private final JLabel  restaurantDetails;
    private final JPanel  menuPanel;

    public MenuScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);

        // Header info (populated by loadRestaurant)
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(AppColors.BG_PANEL);
        header.setBorder(BorderFactory.createEmptyBorder(16, 32, 16, 32));

        restaurantTitle   = UIHelper.label("", AppColors.FONT_TITLE, AppColors.TEXT_PRIMARY);
        restaurantDetails = UIHelper.label("", AppColors.FONT_SMALL, AppColors.TEXT_MUTED);
        header.add(restaurantTitle);
        header.add(Box.createVerticalStrut(4));
        header.add(restaurantDetails);

        // Combine top bar and restaurant header into one NORTH panel so
        // the header does not overwrite the back button
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(AppColors.BG_PANEL);
        northPanel.add(buildTopBar(frame), BorderLayout.NORTH);
        northPanel.add(header, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        // Scrollable menu body
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

    /**
     * Call this before navigating to MenuScreen.
     * Populates the header and builds the categorized menu list.
     */
    public void loadRestaurant(Restaurant restaurant) {
        restaurantTitle.setText(restaurant.getName());
        restaurantDetails.setText(
                restaurant.getCategory() + "  •  " + restaurant.getAddress()
                        + "  •  " + restaurant.getOpenHours()
                        + "  •  ★ " + restaurant.getRating()
        );

        menuPanel.removeAll();

        List<MenuItem> items = restaurant.getMenu().getItems();

        if (items.isEmpty()) {
            JLabel empty = UIHelper.label("No menu items available yet.", AppColors.FONT_BODY, AppColors.TEXT_MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuPanel.add(empty);
        } else {
            // Group items by category preserving insertion order
            LinkedHashMap<String, List<MenuItem>> grouped = new LinkedHashMap<>();
            for (MenuItem item : items) {
                String cat = item.getCategory() != null && !item.getCategory().isBlank()
                        ? item.getCategory() : "Other";
                grouped.computeIfAbsent(cat, k -> new ArrayList<>()).add(item);
            }

            // Render each category section
            for (Map.Entry<String, List<MenuItem>> entry : grouped.entrySet()) {
                menuPanel.add(buildCategoryHeader(entry.getKey()));
                menuPanel.add(Box.createVerticalStrut(8));
                for (MenuItem item : entry.getValue()) {
                    menuPanel.add(buildMenuItemCard(item));
                    menuPanel.add(Box.createVerticalStrut(8));
                }
                menuPanel.add(Box.createVerticalStrut(16));
            }
        }

        menuPanel.revalidate();
        menuPanel.repaint();
    }

    // Category section header
    private JLabel buildCategoryHeader(String category) {
        JLabel label = UIHelper.label(category.toUpperCase(), AppColors.FONT_HEADING, AppColors.ACCENT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        return label;
    }

    // Single menu item card
    private JPanel buildMenuItemCard(MenuItem item) {
        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BorderLayout(12, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left: name + description
        JPanel info = new JPanel();
        info.setBackground(AppColors.BG_CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        info.add(UIHelper.label(item.getName(), AppColors.FONT_BODY, AppColors.TEXT_PRIMARY));

        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            info.add(UIHelper.label(item.getDescription(), AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        }

        // Right: price
        JLabel price = UIHelper.label("$" + String.format("%.2f", item.getPrice()),
                AppColors.FONT_HEADING, AppColors.ACCENT);
        price.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 4));

        card.add(info, BorderLayout.CENTER);
        card.add(price, BorderLayout.EAST);
        return card;
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton back = UIHelper.secondaryButton("<- Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_RESTAURANT_LIST));
        bar.add(back);
        bar.add(UIHelper.label("Menu", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        return bar;
    }
}