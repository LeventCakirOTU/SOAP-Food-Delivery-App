package fooddelivery.ui;

import fooddelivery.service.UserService;
import javax.swing.*;
import java.awt.*;

public class RegisterCustomerScreen extends JPanel {

    private final JTextField nameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JTextField addressField;
    private final JTextField latField;
    private final JTextField lngField;

    public RegisterCustomerScreen(MainFrame frame, UserService userService) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(AppColors.BG_DARK);

        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(380, 550));

        JLabel title = UIHelper.label("Create Customer Account", AppColors.FONT_HEADING, AppColors.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField     = UIHelper.styledField("Full name");
        emailField    = UIHelper.styledField("Email address");
        passwordField = UIHelper.stylePassword("Password");
        addressField  = UIHelper.styledField("Delivery address");
        latField      = UIHelper.styledField("e.g. 43.65");
        lngField      = UIHelper.styledField("e.g. -79.38");

        for (JComponent f : new JComponent[]{nameField, emailField, passwordField, addressField, latField, lngField}) {
            f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        }

        // Lat/lng on one row
        JPanel coordRow = new JPanel(new GridLayout(1, 2, 10, 0));
        coordRow.setBackground(AppColors.BG_CARD);
        coordRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JPanel latPanel = new JPanel();
        latPanel.setBackground(AppColors.BG_CARD);
        latPanel.setLayout(new BoxLayout(latPanel, BoxLayout.Y_AXIS));
        latPanel.add(UIHelper.label("Latitude", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        latPanel.add(Box.createVerticalStrut(4));
        latPanel.add(latField);

        JPanel lngPanel = new JPanel();
        lngPanel.setBackground(AppColors.BG_CARD);
        lngPanel.setLayout(new BoxLayout(lngPanel, BoxLayout.Y_AXIS));
        lngPanel.add(UIHelper.label("Longitude", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        lngPanel.add(Box.createVerticalStrut(4));
        lngPanel.add(lngField);

        coordRow.add(latPanel);
        coordRow.add(lngPanel);

        JButton submitBtn = UIHelper.primaryButton("Create Account");
        submitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.addActionListener(e -> handleRegister(frame, userService));

        card.add(title);
        card.add(Box.createVerticalStrut(20));
        addLabeledField(card, "Full Name", nameField);
        addLabeledField(card, "Email", emailField);
        addLabeledField(card, "Password", passwordField);
        addLabeledField(card, "Delivery Address", addressField);
        card.add(UIHelper.label("Location Coordinates (for nearby restaurants)", AppColors.FONT_SMALL, AppColors.TEXT_MUTED));
        card.add(Box.createVerticalStrut(6));
        card.add(coordRow);
        card.add(Box.createVerticalStrut(20));
        card.add(submitBtn);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private void handleRegister(MainFrame frame, UserService userService) {
        String name     = nameField.getText().trim();
        String email    = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String address  = addressField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
            UIHelper.showError(this, "All fields are required.");
            return;
        }

        double lat = 0.0, lng = 0.0;
        try {
            if (!latField.getText().trim().isEmpty()) lat = Double.parseDouble(latField.getText().trim());
            if (!lngField.getText().trim().isEmpty()) lng = Double.parseDouble(lngField.getText().trim());
        } catch (NumberFormatException ex) {
            UIHelper.showError(this, "Coordinates must be valid numbers (e.g. 43.65, -79.38).");
            return;
        }

        fooddelivery.user.Customer c = userService.registerCustomer(name, email, password, address);
        c.setLatitude(lat);
        c.setLongitude(lng);
        UIHelper.showSuccess(this, "Account created. Log in.");
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
        JButton back = UIHelper.secondaryButton("<- Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_LANDING));
        bar.add(back);
        bar.add(UIHelper.label("Customer Registration", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        return bar;
    }
}
