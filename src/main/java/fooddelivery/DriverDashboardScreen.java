package com.fooddelivery.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Driver dashboard — shown after driver logs in.
 * TODO: show the available orders list, current delivery status....
 */
public class DriverDashboardScreen extends JPanel {

    public DriverDashboardScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(AppColors.BG_DARK);

        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(500, 240));

        JLabel title = UIHelper.label("Driver Dashboard", AppColors.FONT_TITLE, AppColors.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton availableBtn = UIHelper.primaryButton("View Available Orders");
        availableBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        availableBtn.addActionListener(e ->
            UIHelper.showError(this, "Available orders view not yet implemented."));

        JButton statusBtn = UIHelper.secondaryButton("Update Delivery Status");
        statusBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBtn.addActionListener(e ->
            UIHelper.showError(this, "Delivery status update not yet implemented."));

        card.add(title);
        card.add(Box.createVerticalStrut(30));
        card.add(availableBtn);
        card.add(Box.createVerticalStrut(12));
        card.add(statusBtn);

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
        bar.add(UIHelper.label("SOAP - Driver", AppColors.FONT_HEADING, AppColors.ACCENT));
        bar.add(Box.createHorizontalGlue());
        bar.add(logout);
        return bar;
    }
}
