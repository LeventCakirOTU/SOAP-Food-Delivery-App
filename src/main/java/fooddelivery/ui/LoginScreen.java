package fooddelivery.ui;

import fooddelivery.service.UserService;
import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {

    private final JTextField    emailField;
    private final JPasswordField passwordField;

    public LoginScreen(MainFrame frame, UserService userService) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(AppColors.BG_DARK);

        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(360, 320));

        JLabel title = UIHelper.label("Welcome Back", AppColors.FONT_TITLE, AppColors.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = UIHelper.label("Sign in to your account", AppColors.FONT_BODY, AppColors.TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailField    = UIHelper.styledField("Email address");
        passwordField = UIHelper.stylePassword("Password");
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton loginBtn = UIHelper.primaryButton("Sign In");
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> handleLogin(frame, userService));

        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        card.add(Box.createVerticalStrut(24));
        card.add(UIHelper.label("Email", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        card.add(Box.createVerticalStrut(4));
        card.add(emailField);
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.label("Password", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        card.add(Box.createVerticalStrut(4));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(24));
        card.add(loginBtn);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private void handleLogin(MainFrame frame, UserService userService) {
        String email    = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            UIHelper.showError(this, "Please enter your email and password.");
            return;
        }

        fooddelivery.user.User user = userService.login(email, password);

        if (user == null) {
            // Distinguish between wrong credentials and suspended account
            // by checking if the email exists at all
            fooddelivery.user.User found = userService.findByEmail(email);
            if (found != null && userService.isSuspended(found.getId())) {
                UIHelper.showError(this, "This account has been suspended. Please contact an administrator.");
            } else {
                UIHelper.showError(this, "Login failed. Incorrect email or password.");
            }
            return;
        }

        frame.setCurrentUser(user);

        if (user instanceof fooddelivery.user.Customer) {
            frame.showScreen(MainFrame.SCREEN_CUSTOMER_DASHBOARD);
        } else if (user instanceof fooddelivery.user.Driver) {
            frame.showDriverDashboard();
        } else if (user instanceof fooddelivery.user.RestaurantOwner) {
            frame.showScreen(MainFrame.SCREEN_MANAGER_DASHBOARD);
        } else if (user instanceof fooddelivery.user.Admin) {
            frame.showScreen(MainFrame.SCREEN_ADMIN_DASHBOARD);
        } else {
            UIHelper.showError(this, "Unknown account type.");
        }
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton back = UIHelper.secondaryButton("<- Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_LANDING));
        bar.add(back);
        bar.add(UIHelper.label("Login", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        return bar;
    }
}