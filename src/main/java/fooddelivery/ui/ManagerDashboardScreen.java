package fooddelivery.ui;

import fooddelivery.model.MenuItem;
import fooddelivery.model.Restaurant;
import fooddelivery.service.RestaurantService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManagerDashboardScreen extends JPanel {

    public ManagerDashboardScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(AppColors.BG_DARK);

        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(500, 280));

        JLabel title = UIHelper.label("Manager Dashboard", AppColors.FONT_TITLE, AppColors.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addRestBtn = UIHelper.primaryButton("Add / Edit Restaurant");
        addRestBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRestBtn.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_ADD_RESTAURANT));

        JButton menuBtn = UIHelper.secondaryButton("Manage Menu Items");
        menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuBtn.addActionListener(e -> showManageMenuDialog(frame));

        JButton hoursBtn = UIHelper.secondaryButton("Set Business Hours");
        hoursBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        hoursBtn.addActionListener(e -> showSetHoursDialog(frame));

        card.add(title);
        card.add(Box.createVerticalStrut(30));
        card.add(addRestBtn);
        card.add(Box.createVerticalStrut(12));
        card.add(menuBtn);
        card.add(Box.createVerticalStrut(12));
        card.add(hoursBtn);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    // manage menu items

    private void showManageMenuDialog(MainFrame frame) {
        RestaurantService rs = frame.getRestaurantService();
        List<Restaurant> restaurants = rs.getAll();

        if (restaurants.isEmpty()) {
            UIHelper.showError(this, "No restaurants registered yet. Add a restaurant first.");
            return;
        }

        JDialog dialog = new JDialog(frame, "Manage Menu Items", true);
        dialog.setSize(560, 520);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(AppColors.BG_DARK);
        dialog.setLayout(new BorderLayout());

        JLabel heading = UIHelper.label("Manage Menu Items", AppColors.FONT_HEADING, AppColors.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));
        dialog.add(heading, BorderLayout.NORTH);

        // Restaurant picker
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        topBar.setBackground(AppColors.BG_DARK);
        topBar.add(UIHelper.label("Restaurant:", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        JComboBox<Restaurant> restPicker = new JComboBox<>(restaurants.toArray(new Restaurant[0]));
        restPicker.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel lbl = new JLabel(value != null ? value.getName() : "");
            lbl.setFont(AppColors.FONT_BODY);
            lbl.setForeground(AppColors.TEXT_PRIMARY);
            lbl.setBackground(isSelected ? AppColors.ACCENT : AppColors.BG_CARD);
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            return lbl;
        });
        restPicker.setBackground(AppColors.BG_CARD);
        restPicker.setForeground(AppColors.TEXT_PRIMARY);
        restPicker.setFont(AppColors.FONT_BODY);
        restPicker.setPreferredSize(new Dimension(220, 34));
        topBar.add(restPicker);

        // Item list panel (refreshed when restaurant change)
        JPanel itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        itemListPanel.setBackground(AppColors.BG_DARK);
        itemListPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JScrollPane scroll = new JScrollPane(itemListPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppColors.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        Runnable refreshItems = () -> {
            itemListPanel.removeAll();
            Restaurant selected = (Restaurant) restPicker.getSelectedItem();
            if (selected == null) return;
            List<MenuItem> items = selected.getMenu().getItems();
            if (items.isEmpty()) {
                itemListPanel.add(UIHelper.label("No items yet.", AppColors.FONT_BODY, AppColors.TEXT_MUTED));
            } else {
                for (MenuItem item : items) {
                    itemListPanel.add(buildItemRow(item, selected, rs, () -> {
                        itemListPanel.removeAll();
                        List<MenuItem> updated = selected.getMenu().getItems();
                        if (updated.isEmpty()) {
                            itemListPanel.add(UIHelper.label("No items yet.", AppColors.FONT_BODY, AppColors.TEXT_MUTED));
                        } else {
                            for (MenuItem i : updated) {
                                itemListPanel.add(buildItemRow(i, selected, rs, null));
                                itemListPanel.add(Box.createVerticalStrut(6));
                            }
                        }
                        itemListPanel.revalidate();
                        itemListPanel.repaint();
                    }));
                    itemListPanel.add(Box.createVerticalStrut(6));
                }
            }
            itemListPanel.revalidate();
            itemListPanel.repaint();
        };

        restPicker.addActionListener(e -> refreshItems.run());
        refreshItems.run();

        // Add item form
        JPanel addForm = new JPanel();
        addForm.setLayout(new BoxLayout(addForm, BoxLayout.Y_AXIS));
        addForm.setBackground(AppColors.BG_PANEL);
        addForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, AppColors.BORDER),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        addForm.add(UIHelper.label("Add New Item", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        addForm.add(Box.createVerticalStrut(10));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row1.setBackground(AppColors.BG_PANEL);
        JTextField nameField  = styledField(140);
        JTextField priceField = styledField(70);
        JTextField catField   = styledField(100);
        row1.add(labeledField("Name",     nameField));
        row1.add(labeledField("Price $",  priceField));
        row1.add(labeledField("Category", catField));
        addForm.add(row1);
        addForm.add(Box.createVerticalStrut(8));

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row2.setBackground(AppColors.BG_PANEL);
        JTextField descField = styledField(260);
        JTextField prepField = styledField(60);
        row2.add(labeledField("Description", descField));
        row2.add(labeledField("Prep (min)",  prepField));
        addForm.add(row2);
        addForm.add(Box.createVerticalStrut(10));

        JButton addBtn = UIHelper.primaryButton("Add Item");
        addBtn.setPreferredSize(new Dimension(110, 34));
        addBtn.setMaximumSize(new Dimension(110, 34));
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            if (name.isEmpty() || priceText.isEmpty()) {
                UIHelper.showError(dialog, "Name and Price are required.");
                return;
            }
            double price;
            try { price = Double.parseDouble(priceText); }
            catch (NumberFormatException ex) { UIHelper.showError(dialog, "Price must be a number."); return; }

            int prep = 0;
            try { if (!prepField.getText().trim().isEmpty()) prep = Integer.parseInt(prepField.getText().trim()); }
            catch (NumberFormatException ex) { UIHelper.showError(dialog, "Prep time must be a whole number."); return; }

            Restaurant selected = (Restaurant) restPicker.getSelectedItem();
            if (selected == null) return;

            MenuItem item = new MenuItem();
            item.setId("mi" + System.currentTimeMillis());
            item.setName(name);
            item.setDescription(descField.getText().trim());
            item.setPrice(price);
            item.setCategory(catField.getText().trim().isEmpty() ? "General" : catField.getText().trim());
            item.setPreparationTime(prep);
            item.setAvailable(true);

            rs.addMenuItemToRestaurant(selected.getId(), item);
            nameField.setText(""); priceField.setText(""); catField.setText("");
            descField.setText(""); prepField.setText("");
            refreshItems.run();
            UIHelper.showSuccess(this, "\"" + item.getName() + "\" added to " + selected.getName() + ".");
        });
        addForm.add(addBtn);

        // Assemble center section
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(AppColors.BG_DARK);
        main.add(topBar,  BorderLayout.NORTH);
        main.add(scroll,  BorderLayout.CENTER);
        main.add(addForm, BorderLayout.SOUTH);
        dialog.add(main, BorderLayout.CENTER);

        JButton closeBtn = UIHelper.secondaryButton("Close");
        closeBtn.setPreferredSize(new Dimension(90, 34));
        closeBtn.addActionListener(e -> dialog.dispose());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        bottom.setBackground(AppColors.BG_DARK);
        bottom.add(closeBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel buildItemRow(MenuItem item, Restaurant restaurant, RestaurantService rs, Runnable onRemove) {
        JPanel row = UIHelper.cardPanel();
        row.setLayout(new BorderLayout(10, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel info = new JPanel();
        info.setBackground(AppColors.BG_CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(UIHelper.label(item.getName() + "  —  $" + String.format("%.2f", item.getPrice()),
                AppColors.FONT_BODY, AppColors.TEXT_PRIMARY));
        info.add(UIHelper.label(item.getCategory() + (item.isAvailable() ? "" : "  [unavailable]"),
                AppColors.FONT_SMALL, item.isAvailable() ? AppColors.TEXT_MUTED : AppColors.ERROR));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        right.setBackground(AppColors.BG_CARD);

        JButton toggleBtn = UIHelper.secondaryButton(item.isAvailable() ? "Disable" : "Enable");
        toggleBtn.setPreferredSize(new Dimension(80, 28));
        toggleBtn.addActionListener(e -> {
            item.setAvailable(!item.isAvailable());
            toggleBtn.setText(item.isAvailable() ? "Disable" : "Enable");
            info.removeAll();
            info.add(UIHelper.label(item.getName() + "  —  $" + String.format("%.2f", item.getPrice()),
                    AppColors.FONT_BODY, AppColors.TEXT_PRIMARY));
            info.add(UIHelper.label(item.getCategory() + (item.isAvailable() ? "" : "  [unavailable]"),
                    AppColors.FONT_SMALL, item.isAvailable() ? AppColors.TEXT_MUTED : AppColors.ERROR));
            info.revalidate(); info.repaint();
        });

        JButton removeBtn = UIHelper.secondaryButton("Remove");
        removeBtn.setPreferredSize(new Dimension(80, 28));
        removeBtn.setForeground(AppColors.ERROR);
        removeBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Remove \"" + item.getName() + "\" from " + restaurant.getName() + "?",
                    "Confirm Remove", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                rs.removeMenuItemFromRestaurant(restaurant.getId(), item.getId());
                if (onRemove != null) onRemove.run();
            }
        });

        right.add(toggleBtn);
        right.add(removeBtn);
        row.add(info,  BorderLayout.CENTER);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    // Set hours
    private void showSetHoursDialog(MainFrame frame) {
        RestaurantService rs = frame.getRestaurantService();
        List<Restaurant> restaurants = rs.getAll();

        if (restaurants.isEmpty()) {
            UIHelper.showError(this, "No restaurants registered yet. Add a restaurant first.");
            return;
        }

        JDialog dialog = new JDialog(frame, "Set Business Hours", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(AppColors.BG_DARK);
        dialog.setLayout(new BorderLayout());

        JLabel heading = UIHelper.label("Set Business Hours", AppColors.FONT_HEADING, AppColors.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));
        dialog.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(AppColors.BG_DARK);
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        form.add(UIHelper.label("Restaurant", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        form.add(Box.createVerticalStrut(6));
        JComboBox<Restaurant> restPicker = new JComboBox<>(restaurants.toArray(new Restaurant[0]));
        restPicker.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel lbl = new JLabel(value != null ? value.getName() : "");
            lbl.setFont(AppColors.FONT_BODY);
            lbl.setForeground(AppColors.TEXT_PRIMARY);
            lbl.setBackground(isSelected ? AppColors.ACCENT : AppColors.BG_CARD);
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            return lbl;
        });
        restPicker.setBackground(AppColors.BG_CARD);
        restPicker.setForeground(AppColors.TEXT_PRIMARY);
        restPicker.setFont(AppColors.FONT_BODY);
        restPicker.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        form.add(restPicker);
        form.add(Box.createVerticalStrut(16));

        form.add(UIHelper.label("Hours  (e.g. 9AM - 10PM)", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        form.add(Box.createVerticalStrut(6));
        JTextField hoursField = styledField(300);
        hoursField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        Runnable prefill = () -> {
            Restaurant r = (Restaurant) restPicker.getSelectedItem();
            hoursField.setText(r != null && r.getOpenHours() != null ? r.getOpenHours() : "");
        };
        restPicker.addActionListener(e -> prefill.run());
        prefill.run();
        form.add(hoursField);

        dialog.add(form, BorderLayout.CENTER);

        JButton saveBtn = UIHelper.primaryButton("Save");
        saveBtn.setPreferredSize(new Dimension(90, 34));
        saveBtn.addActionListener(e -> {
            Restaurant selected = (Restaurant) restPicker.getSelectedItem();
            String hours = hoursField.getText().trim();
            if (selected == null || hours.isEmpty()) {
                UIHelper.showError(dialog, "Please select a restaurant and enter hours.");
                return;
            }
            rs.updateOpenHours(selected.getId(), hours);
            UIHelper.showSuccess(this, selected.getName() + " hours updated to: " + hours);
            dialog.dispose();
        });

        JButton cancelBtn = UIHelper.secondaryButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(90, 34));
        cancelBtn.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        bottom.setBackground(AppColors.BG_DARK);
        bottom.add(cancelBtn);
        bottom.add(saveBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // helpers
    private JTextField styledField(int width) {
        JTextField f = new JTextField();
        f.setFont(AppColors.FONT_BODY);
        f.setBackground(AppColors.BG_CARD);
        f.setForeground(AppColors.TEXT_PRIMARY);
        f.setCaretColor(AppColors.TEXT_PRIMARY);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        f.setPreferredSize(new Dimension(width, 34));
        return f;
    }

    private JPanel labeledField(String labelText, JTextField field) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(AppColors.BG_PANEL);
        JLabel lbl = UIHelper.label(labelText, AppColors.FONT_SMALL, AppColors.TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(lbl);
        p.add(Box.createVerticalStrut(4));
        p.add(field);
        return p;
    }

    // Top bar
    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton logout = UIHelper.secondaryButton("Logout");
        logout.setPreferredSize(new Dimension(90, 34));
        logout.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_LANDING));
        bar.add(UIHelper.label("Manager", AppColors.FONT_HEADING, AppColors.ACCENT));
        bar.add(Box.createHorizontalGlue());
        bar.add(logout);
        return bar;
    }
}
