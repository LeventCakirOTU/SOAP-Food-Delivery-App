package com.fooddelivery.ui;

import com.fooddelivery.service.UserService;
import javax.swing.*;
import java.awt.*;

/*
 * TODO: wire up UserService.registerManager() on submit.
 */
public class RegisterManagerScreen extends JPanel {

    private final JTextField nameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;

    public RegisterManagerScreen(MainFrame frame, UserService userService) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(AppColors.BG_DARK);

        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(380, 330));

        JLabel title = UIHelper.label("Register as Manager", AppColors.FONT_HEADING, AppColors.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField     = UIHelper.styledField("Full name");
        emailField    = UIHelper.styledField("Email address");
        passwordField = UIHelper.stylePassword("Password");

        for (JComponent f : new JComponent[]{nameField, emailField, passwordField}) {
            f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }

        JButton submitBtn = UIHelper.primaryButton("Create Account");
        submitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.addActionListener(e -> handleRegister(frame, userService));

        card.add(title);
        card.add(Box.createVerticalStrut(20));
        addLabeledField(card, "Full Name", nameField);
        addLabeledField(card, "Email", emailField);
        addLabeledField(card, "Password", passwordField);
        card.add(Box.createVerticalStrut(20));
        card.add(submitBtn);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private void handleRegister(MainFrame frame, UserService userService) {
        String name  = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pass  = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            UIHelper.showError(this, "All fields are required.");
            return;
        }

        userService.registerManager(name, email, pass);
        UIHelper.showSuccess(this, "Manager account created! You can now log in.");
        frame.showScreen(MainFrame.SCREEN_LOGIN);
    }

    private void addLabeledField(JPanel card, String labelText, JComponent field) {
        card.add(UIHelper.label(labelText, AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        card.add(Box.createVerticalStrut(4));
        card.add(field);
        card.add(Box.createVerticalStrut(12));
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton back = UIHelper.secondaryButton("← Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_LANDING));
        bar.add(back);
        bar.add(UIHelper.label("Manager Registration", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        return bar;
    }
}
