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
        JButton manualBtn      = UIHelper.primaryButton("Manual");
        JButton regCustomer    = UIHelper.secondaryButton("Register as Customer");
        JButton regDriver      = UIHelper.secondaryButton("Register as Driver");
        JButton regManager     = UIHelper.secondaryButton("Register as Manager");

        loginBtn.addActionListener(e      -> frame.showScreen(MainFrame.SCREEN_LOGIN));
        browseBtn.addActionListener(e     -> frame.showScreen(MainFrame.SCREEN_RESTAURANT_LIST));
        manualBtn.addActionListener(e     -> showInstructions(frame));
        regCustomer.addActionListener(e   -> frame.showScreen(MainFrame.SCREEN_REGISTER_CUSTOMER));
        regDriver.addActionListener(e     -> frame.showScreen(MainFrame.SCREEN_REGISTER_DRIVER));
        regManager.addActionListener(e    -> frame.showScreen(MainFrame.SCREEN_REGISTER_MANAGER));

        btnGrid.add(loginBtn);
        btnGrid.add(browseBtn);
        btnGrid.add(manualBtn);
        btnGrid.add(regCustomer);
        btnGrid.add(regDriver);
        btnGrid.add(regManager);

        hero.add(btnGrid);
        add(hero, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(AppColors.BG_DARK);
        add(footer, BorderLayout.SOUTH);
    }

    private void showInstructions(MainFrame frame) {
        JDialog dialog = new JDialog(frame, "How to Use SOAP FDA", true);
        dialog.setSize(520, 480);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(AppColors.BG_DARK);
        dialog.setLayout(new BorderLayout());

        JLabel heading = new JLabel("How to Use SOAP FDA");
        heading.setFont(AppColors.FONT_HEADING);
        heading.setForeground(AppColors.ACCENT);
        heading.setBorder(BorderFactory.createEmptyBorder(14, 20, 10, 20));
        dialog.add(heading, BorderLayout.NORTH);

        String[] sections = {
                "Getting Started",
                " Open the app to reach the Landing Screen. You can browse restaurants without an account,",
                " but you need to log in to place orders.",
                "",
                "Registering",
                " Customer: click 'Register as Customer': enter your name, email, password, delivery address,",
                " and optional coordinates (e.g. 43.65 / -79.38) for nearby restaurant sorting.",
                " Driver: click 'Register as Driver': enter name, email, password, and your vehicle info.",
                " Manager: click 'Register as Manager': enter your name, email, and password.",
                "",
                "Logging In",
                " Click 'Login', enter your email and password, then click Sign In.",
                " Where you will be taken to your role's dashboard automatically.",
                "",
                "Customers",
                " 1. Browse Restaurants – restaurants are sorted by distance if you've set coordinates.(you may have to refresh)",
                " 2. Click 'View Menu' on any restaurant card.",
                " 3. Click '+ Add' next to an item to add it to your cart.",
                " 4. Click 'Cart' (top-right) to review items, adjust quantities, or remove items.",
                " 5. You can add optional special instructions, then click 'Place Order'.",
                " 6. Check 'Order History' to track order status and rate a delivered order with the 'Rate' button.",
                "",
                "Drivers",
                " 1. Click 'Go Online' to make yourself available.",
                " 2. Click 'View Available Orders' and Accept or Reject pending orders.",
                " 3. Click 'Update Delivery Status' to move a task through:",
                "    PICKED_UP -> IN_TRANSIT -> DELIVERED.",
                "",
                "Managers",
                " 1. Click 'Add / Edit Restaurant', fill in the details, and click 'Create Restaurant'.",
                " 2. Add menu items in the right panel (name, price, category, prep time required).",
                " 3. Use 'Manage Menu Items' to enable, disable, or remove existing items.",
                " 4. Use 'Set Business Hours' to update a restaurant's opening hours.",
                "",
                "Demo Accounts",
                " Customer : alice@email.com  /  password!",
                " Driver   : bob@gmail.com    /  12345",
                " Manager  : mike@teamail.com /  mikey77",
                " Admin  : admin@email.com /  admin123",
        };

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(AppColors.BG_DARK);
        content.setBorder(BorderFactory.createEmptyBorder(8, 20, 16, 20));

        for (String line : sections) {
            if (line.isEmpty()) {
                content.add(Box.createVerticalStrut(10));
                continue;
            }
            boolean isHeader = !line.startsWith(" ") && !line.matches("^\\d.*");
            JLabel lbl = new JLabel("<html>" + line.replace("\n", "<br>&nbsp;&nbsp;&nbsp;") + "</html>");
            lbl.setFont(isHeader ? AppColors.FONT_HEADING : AppColors.FONT_BODY);
            lbl.setForeground(isHeader ? AppColors.ACCENT : AppColors.TEXT_PRIMARY);
            lbl.setBorder(BorderFactory.createEmptyBorder(isHeader ? 6 : 2, 0, 2, 0));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            content.add(lbl);
        }

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppColors.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        dialog.add(scroll, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(AppColors.FONT_BODY);
        closeBtn.addActionListener(e -> dialog.dispose());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        bottom.setBackground(AppColors.BG_DARK);
        bottom.add(closeBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}