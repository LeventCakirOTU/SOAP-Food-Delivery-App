package com.fooddelivery.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

/**
 * Factory methods for styled Swing components.
 * Use these instead of raw JButton/JTextField so the whole app looks consistent.
 */
public class UIHelper {

    /** Styled primary button (orange) */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(AppColors.FONT_BUTTON);
        btn.setBackground(AppColors.ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 42));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(AppColors.ACCENT_HOVER); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(AppColors.ACCENT); }
        });
        return btn;
    }

    /** Styled secondary button (outline style) */
    public static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(AppColors.FONT_BUTTON);
        btn.setBackground(AppColors.BG_CARD);
        btn.setForeground(AppColors.TEXT_PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(AppColors.BORDER, 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 42));
        return btn;
    }

    /** Styled text field */
    public static JTextField styledField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(AppColors.FONT_BODY);
        field.setBackground(AppColors.BG_CARD);
        field.setForeground(AppColors.TEXT_PRIMARY);
        field.setCaretColor(AppColors.TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(260, 40));
        // Placeholder behaviour
        field.setText(placeholder);
        field.setForeground(AppColors.TEXT_MUTED);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(AppColors.TEXT_PRIMARY);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(AppColors.TEXT_MUTED);
                }
            }
        });
        return field;
    }

    public static JPasswordField stylePassword(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setFont(AppColors.FONT_BODY);
        field.setBackground(AppColors.BG_CARD);
        field.setForeground(AppColors.TEXT_PRIMARY);
        field.setCaretColor(AppColors.TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(260, 40));
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(AppColors.TEXT_MUTED);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(AppColors.TEXT_PRIMARY);
                    field.setEchoChar('•');
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(AppColors.TEXT_MUTED);
                }
            }
        });
        return field;
    }

    //label
    public static JLabel label(String text, Font font, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        return lbl;
    }

    //darker panel
    public static JPanel darkPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(AppColors.BG_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        return panel;
    }

    // card
    public static JPanel cardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(AppColors.BG_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppColors.BORDER, 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        return panel;
    }

    // show error
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    // check
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
