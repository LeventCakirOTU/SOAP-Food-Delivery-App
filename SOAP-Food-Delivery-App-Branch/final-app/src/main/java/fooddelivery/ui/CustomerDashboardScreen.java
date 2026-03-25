package fooddelivery.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Customer dashboard — shown after successful login.
 * TODO: complete order history, cart, restaurant browsing panels.
 */
public class CustomerDashboardScreen extends JPanel {

    public CustomerDashboardScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(AppColors.BG_DARK);

        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(500, 280));

        JLabel title = UIHelper.label("Customer Dashboard", AppColors.FONT_TITLE, AppColors.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = UIHelper.label("Logged in successfully!", AppColors.FONT_BODY, AppColors.SUCCESS);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton browseBtn = UIHelper.primaryButton("Browse Restaurants");
        browseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseBtn.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_RESTAURANT_LIST));

        JButton historyBtn = UIHelper.secondaryButton("My Orders");
        historyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyBtn.addActionListener(e ->
            UIHelper.showError(this, "Order history not yet implemented."));

        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(sub);
        card.add(Box.createVerticalStrut(30));
        card.add(browseBtn);
        card.add(Box.createVerticalStrut(12));
        card.add(historyBtn);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton logout = UIHelper.secondaryButton("Logout");
        logout.setPreferredSize(new Dimension(90, 34));
        logout.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_LANDING));
        bar.add(UIHelper.label("🍔 FoodDash", AppColors.FONT_HEADING, AppColors.ACCENT));
        bar.add(Box.createHorizontalGlue());
        bar.add(logout);
        return bar;
    }
}
