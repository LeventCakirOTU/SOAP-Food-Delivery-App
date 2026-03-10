package com.fooddelivery.ui;

import javax.swing.*;
import java.awt.*;

/*
 * TODO: show restaurant details, menu management, availability toggles.
 */
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
        menuBtn.addActionListener(e ->
            UIHelper.showError(this, "Menu management not yet implemented."));

        JButton hoursBtn = UIHelper.secondaryButton("Set Business Hours");
        hoursBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        hoursBtn.addActionListener(e ->
            UIHelper.showError(this, "Business hours not yet implemented."));

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

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton logout = UIHelper.secondaryButton("Logout");
        logout.setPreferredSize(new Dimension(90, 34));
        logout.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_LANDING));
        bar.add(UIHelper.label("SOAP - Manager", AppColors.FONT_HEADING, AppColors.ACCENT));
        bar.add(Box.createHorizontalGlue());
        bar.add(logout);
        return bar;
    }
}
