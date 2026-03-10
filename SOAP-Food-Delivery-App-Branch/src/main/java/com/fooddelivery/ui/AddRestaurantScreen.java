package com.fooddelivery.ui;

import com.fooddelivery.service.RestaurantService;
import com.fooddelivery.service.UserService;
import javax.swing.*;
import java.awt.*;

/**
 * Screen for a manager to create a restaurant and add menu items.
 * TODO: complete RestaurantService.createRestaurant() and manager.addMenuItem().
 */
public class AddRestaurantScreen extends JPanel {

    private final JTextField restNameField;
    private final JTextField addressField;
    private final JTextField categoryField;
    private final JTextField hoursField;

    // Menu item fields
    private final JTextField itemNameField;
    private final JTextField itemDescField;
    private final JTextField itemPriceField;
    private final JTextField itemCategoryField;

    private final JPanel menuPreviewPanel;

    public AddRestaurantScreen(MainFrame frame, RestaurantService restaurantService, UserService userService) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        // ---- Split layout: left = restaurant form, right = menu builder ----
        JPanel content = new JPanel(new GridLayout(1, 2, 20, 0));
        content.setBackground(AppColors.BG_DARK);
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- Left: Restaurant Info ---
        JPanel leftCard = UIHelper.cardPanel();
        leftCard.setLayout(new BoxLayout(leftCard, BoxLayout.Y_AXIS));

        JLabel leftTitle = UIHelper.label("Restaurant Info", AppColors.FONT_HEADING, AppColors.ACCENT);
        leftTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        restNameField = UIHelper.styledField("Restaurant name");
        addressField  = UIHelper.styledField("Address");
        categoryField = UIHelper.styledField("Category (e.g. Italian, Fast Food)");
        hoursField    = UIHelper.styledField("Open hours (e.g. 9AM - 10PM)");

        for (JComponent f : new JComponent[]{restNameField, addressField, categoryField, hoursField}) {
            f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            f.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        JButton createBtn = UIHelper.primaryButton("Create Restaurant");
        createBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        createBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        createBtn.addActionListener(e -> handleCreateRestaurant(restaurantService, userService));

        leftCard.add(leftTitle);
        leftCard.add(Box.createVerticalStrut(16));
        addField(leftCard, "Name", restNameField);
        addField(leftCard, "Address", addressField);
        addField(leftCard, "Category", categoryField);
        addField(leftCard, "Hours", hoursField);
        leftCard.add(Box.createVerticalStrut(16));
        leftCard.add(createBtn);

        // --- Right: Menu Item Builder ---
        JPanel rightCard = UIHelper.cardPanel();
        rightCard.setLayout(new BoxLayout(rightCard, BoxLayout.Y_AXIS));

        JLabel rightTitle = UIHelper.label("Add Menu Items", AppColors.FONT_HEADING, AppColors.ACCENT);
        rightTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        itemNameField     = UIHelper.styledField("Item name");
        itemDescField     = UIHelper.styledField("Description");
        itemPriceField    = UIHelper.styledField("Price (e.g. 9.99)");
        itemCategoryField = UIHelper.styledField("Category (e.g. Main, Dessert)");

        for (JComponent f : new JComponent[]{itemNameField, itemDescField, itemPriceField, itemCategoryField}) {
            f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            f.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        JButton addItemBtn = UIHelper.secondaryButton("+ Add Item");
        addItemBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        addItemBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        addItemBtn.addActionListener(e -> handleAddMenuItem());

        menuPreviewPanel = new JPanel();
        menuPreviewPanel.setLayout(new BoxLayout(menuPreviewPanel, BoxLayout.Y_AXIS));
        menuPreviewPanel.setBackground(AppColors.BG_CARD);
        menuPreviewPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane menuScroll = new JScrollPane(menuPreviewPanel);
        menuScroll.setPreferredSize(new Dimension(Integer.MAX_VALUE, 140));
        menuScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        menuScroll.setBorder(BorderFactory.createLineBorder(AppColors.BORDER));
        menuScroll.getViewport().setBackground(AppColors.BG_CARD);
        menuScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        rightCard.add(rightTitle);
        rightCard.add(Box.createVerticalStrut(16));
        addField(rightCard, "Item Name", itemNameField);
        addField(rightCard, "Description", itemDescField);
        addField(rightCard, "Price", itemPriceField);
        addField(rightCard, "Category", itemCategoryField);
        rightCard.add(Box.createVerticalStrut(8));
        rightCard.add(addItemBtn);
        rightCard.add(Box.createVerticalStrut(12));
        rightCard.add(UIHelper.label("Menu Preview:", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        rightCard.add(Box.createVerticalStrut(4));
        rightCard.add(menuScroll);

        content.add(leftCard);
        content.add(rightCard);
        add(content, BorderLayout.CENTER);
    }

    private void handleCreateRestaurant(RestaurantService restaurantService, UserService userService) {
        String name     = restNameField.getText().trim();
        String address  = addressField.getText().trim();
        String category = categoryField.getText().trim();
        String hours    = hoursField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || category.isEmpty() || hours.isEmpty()) {
            UIHelper.showError(this, "All restaurant fields are required.");
            return;
        }

        // todo: complete createRestaurant() and implerment
        UIHelper.showError(this, "Create restaurant not yet implemented — add to handleCreateRestaurant()");
    }

    private void handleAddMenuItem() {
        String name     = itemNameField.getText().trim();
        String desc     = itemDescField.getText().trim();
        String priceStr = itemPriceField.getText().trim();
        String category = itemCategoryField.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty() || category.isEmpty()) {
            UIHelper.showError(this, "Name, price, and category are required.");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            // todo : compete manager.addMenuItem(name, desc, price, category)

            // prev not yet done
            JLabel preview = UIHelper.label("• [" + category + "] " + name + " — $" + price,
                    AppColors.FONT_SMALL, AppColors.TEXT_PRIMARY);
            menuPreviewPanel.add(preview);
            menuPreviewPanel.revalidate();
            menuPreviewPanel.repaint();

        } catch (NumberFormatException ex) {
            UIHelper.showError(this, "Price must be a number (e.g. 9.99).");
        }
    }

    private void addField(JPanel panel, String labelText, JComponent field) {
        panel.add(UIHelper.label(labelText, AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        panel.add(Box.createVerticalStrut(4));
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton back = UIHelper.secondaryButton("← Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_LANDING));
        bar.add(back);
        bar.add(UIHelper.label("Add Restaurant & Menu", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        return bar;
    }
}
