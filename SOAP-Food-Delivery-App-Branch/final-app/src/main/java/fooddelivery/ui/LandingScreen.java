package fooddelivery.ui;
import javax.swing.*;
import java.awt.*;

public class LandingScreen extends JPanel {

    public LandingScreen(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);

        // header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        header.setBackground(AppColors.BG_PANEL);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JLabel logo = UIHelper.label("SOAP FDA", AppColors.FONT_TITLE, AppColors.ACCENT);
        header.add(logo);
        add(header, BorderLayout.NORTH);

        // center
        JPanel hero = new JPanel();
        hero.setBackground(AppColors.BG_DARK);
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        JLabel tagline = UIHelper.label("SOAP", AppColors.FONT_TITLE, AppColors.TEXT_PRIMARY);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = UIHelper.label("Software Oriented Analysis Project",
                AppColors.FONT_BODY, AppColors.TEXT_MUTED);
                sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        hero.add(tagline);
        hero.add(Box.createVerticalStrut(10));
        hero.add(sub);
        hero.add(Box.createVerticalStrut(40));

        // button grid
        JPanel btnGrid = new JPanel(new GridLayout(2, 3, 14, 14));
        btnGrid.setBackground(AppColors.BG_DARK);
        btnGrid.setMaximumSize(new Dimension(620, 110));
        btnGrid.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginBtn       = UIHelper.primaryButton("Login");
        JButton browseBtn      = UIHelper.primaryButton("Browse Restaurants");
        JButton regCustomer    = UIHelper.secondaryButton("Register as Customer");
        JButton regDriver      = UIHelper.secondaryButton("Register as Driver");
        JButton regManager     = UIHelper.secondaryButton("Register as Manager");
        JButton addRestaurant  = UIHelper.secondaryButton("Add Restaurant");

        loginBtn.addActionListener(e      -> frame.showScreen(MainFrame.SCREEN_LOGIN));
        browseBtn.addActionListener(e     -> frame.showScreen(MainFrame.SCREEN_RESTAURANT_LIST));
        regCustomer.addActionListener(e   -> frame.showScreen(MainFrame.SCREEN_REGISTER_CUSTOMER));
        regDriver.addActionListener(e     -> frame.showScreen(MainFrame.SCREEN_REGISTER_DRIVER));
        regManager.addActionListener(e    -> frame.showScreen(MainFrame.SCREEN_REGISTER_MANAGER));
        addRestaurant.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_ADD_RESTAURANT));

        btnGrid.add(loginBtn);
        btnGrid.add(browseBtn);
        btnGrid.add(regCustomer);
        btnGrid.add(regDriver);
        btnGrid.add(regManager);
        btnGrid.add(addRestaurant);

        hero.add(btnGrid);
        add(hero, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(AppColors.BG_DARK);
        add(footer, BorderLayout.SOUTH);
    }
}
