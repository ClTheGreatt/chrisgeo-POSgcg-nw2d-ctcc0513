package com.mycompany.posgrocerystoree;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class POSGrocerystoree {
    private static final Map<String, Double> PRODUCTS = new LinkedHashMap<>();
    private static DefaultTableModel cartTableModel;
    private static JTextField subtotalField;
    private static JTextField taxField;
    private static JTextField totalField;

    public static void main(String[] args) {
        initializeProducts();
        SwingUtilities.invokeLater(POSGrocerystoree::showLoginMenu);
    }

    private static void initializeProducts() {
        // products to the map with categories
        PRODUCTS.put("Beverages - Bottled Water (500ml)", 20.00);
        PRODUCTS.put("Beverages - Soft Drinks (1.5L)", 75.00);
        PRODUCTS.put("Beverages - Juice (1L)", 65.00);
        PRODUCTS.put("Beverages - Coffee (Instant)", 50.00);
        PRODUCTS.put("Seafood - Salmon (1 kg)", 800.00);
        PRODUCTS.put("Seafood - Shrimp (1 kg)", 600.00);
        PRODUCTS.put("Seafood - Tuna (1 kg)", 350.00);
        PRODUCTS.put("Meat - Chicken Breast (1 kg)", 200.00);
        PRODUCTS.put("Meat - Pork Chops (1 kg)", 280.00);
        PRODUCTS.put("Meat - Ground Beef (1 kg)", 350.00);
        PRODUCTS.put("Meat - Beef Steak (1 kg)", 750.00);
        PRODUCTS.put("Fruits & Vegetables - Apples (1 kg)", 150.00);
        PRODUCTS.put("Fruits & Vegetables - Bananas (1 kg)", 80.00);
        PRODUCTS.put("Fruits & Vegetables - Tomatoes (1 kg)", 50.00);
        PRODUCTS.put("Fruits & Vegetables - Potatoes (1 kg)", 60.00);
        PRODUCTS.put("Fruits & Vegetables - Carrots (1 kg)", 70.00);
        PRODUCTS.put("Fruits & Vegetables - Lettuce (per head)", 40.00);
        PRODUCTS.put("Dairy - Milk (1L)", 75.00);
        PRODUCTS.put("Dairy - Cheese (500g)", 150.00);
        PRODUCTS.put("Dairy - Yogurt (4-pack)", 200.00);
        PRODUCTS.put("Snacks - Chips (Large Bag)", 80.00);
        PRODUCTS.put("Snacks - Cookies (Box)", 120.00);
        PRODUCTS.put("Snacks - Crackers (Box)", 50.00);
        PRODUCTS.put("Snacks - Chocolate Bar", 60.00);
    }
         //login
    private static void showLoginMenu() {
        JFrame frame = new JFrame("GCG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("GCG POS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.add(usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            showHomeScreen();
            frame.dispose();
        });

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(e -> {
            showSignupScreen();
            frame.dispose();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        panel.add(buttonPanel);

        frame.add(panel);
        frame.setVisible(true);
    }
         //signup
    private static void showSignupScreen() {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.add(usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        panel.add(confirmPasswordField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            frame.dispose();
            showLoginMenu();
        });
        panel.add(signupButton);

        frame.add(panel);
        frame.setVisible(true);
    }
        //main or pos 
    private static void showHomeScreen() {
        JFrame frame = new JFrame("Chris & Geo POS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Tabs for categories
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Beverages", createCategoryPanel("Beverages"));
        tabbedPane.addTab("Seafood", createCategoryPanel("Seafood"));
        tabbedPane.addTab("Meat", createCategoryPanel("Meat"));
        tabbedPane.addTab("Fruits & Vegetables", createCategoryPanel("Fruits & Vegetables"));
        tabbedPane.addTab("Dairy", createCategoryPanel("Dairy"));
        tabbedPane.addTab("Snacks", createCategoryPanel("Snacks"));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(createCartPanel(), BorderLayout.EAST);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createCategoryPanel(String category) {
        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10));

        PRODUCTS.forEach((name, price) -> {
            if (name.startsWith(category)) {
                JButton button = new JButton(name.split(" - ")[1] + " ₱" + price);
                button.addActionListener(e -> addToCart(name.split(" - ")[1], price));
                panel.add(button);
            }
        });

        return panel;
    }
           //cart panel
    private static JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        // Cart Table
        cartTableModel = new DefaultTableModel(new Object[]{"Item", "Qty", "Amount"}, 0);
        JTable cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel paymentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentPanel.add(new JLabel("SubTotal:"), gbc);

        gbc.gridx = 1;
        subtotalField = new JTextField("₱0.00");
        subtotalField.setEditable(false);
        paymentPanel.add(subtotalField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        paymentPanel.add(new JLabel("Tax (5%):"), gbc);

        gbc.gridx = 1;
        taxField = new JTextField("₱0.00");
        taxField.setEditable(false);
        paymentPanel.add(taxField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        paymentPanel.add(new JLabel("Total:"), gbc);

        gbc.gridx = 1;
        totalField = new JTextField("₱0.00");
        totalField.setEditable(false);
        paymentPanel.add(totalField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        paymentPanel.add(new JLabel("Payment Method:"), gbc);

        gbc.gridx = 1;
        String[] paymentMethods = {"Cash", "GCash", "PayMaya", "Credit Card"};
        JComboBox<String> paymentMethodDropdown = new JComboBox<>(paymentMethods);
        paymentPanel.add(paymentMethodDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        paymentPanel.add(new JLabel("Cash:"), gbc);

        gbc.gridx = 1;
        JTextField cashField = new JTextField();
        paymentPanel.add(cashField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        paymentPanel.add(new JLabel("Change:"), gbc);

        gbc.gridx = 1;
        JTextField changeField = new JTextField();
        changeField.setEditable(false);
        paymentPanel.add(changeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton payButton = new JButton("Pay");
        payButton.addActionListener(e -> {
    try {
        double total = Double.parseDouble(totalField.getText().substring(1));
        double cash = Double.parseDouble(cashField.getText());
        if (cash < total) {
            JOptionPane.showMessageDialog(cartPanel, "Insufficient cash!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            double change = cash - total;
            changeField.setText("₱" + String.format("%.2f", change));
            JOptionPane.showMessageDialog(cartPanel, "Payment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Reset the cart after successful payment
            resetCart();
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(cartPanel, "Invalid cash amount!", "Error", JOptionPane.ERROR_MESSAGE);
    }
});

        paymentPanel.add(payButton, gbc);

        cartPanel.add(paymentPanel, BorderLayout.SOUTH);

        return cartPanel;
    }

    private static void addToCart(String item, double price) {
        boolean found = false;
        // Update quantity and amount if item already in cart
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            if (cartTableModel.getValueAt(i, 0).equals(item)) {
                int qty = (int) cartTableModel.getValueAt(i, 1);
                cartTableModel.setValueAt(qty + 1, i, 1);
                cartTableModel.setValueAt(price * (qty + 1), i, 2);
                found = true;
                break;
            }
        }
        // Add new item to cart if not found
        if (!found) {
            cartTableModel.addRow(new Object[]{item, 1, price});
        }

        updateCartTotals();
    }
       private static void resetCart() {
    // Clear all rows 
    cartTableModel.setRowCount(0);
    // Reset 
    subtotalField.setText("₱0.00");
    taxField.setText("₱0.00");
    totalField.setText("₱0.00");
   
    JTextField cashField = new JTextField();
    JTextField changeField = new JTextField();
    cashField.setText("");
    changeField.setText("");
}

    private static void updateCartTotals() {
        double subtotal = 0.0;

        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            subtotal += (double) cartTableModel.getValueAt(i, 2);
        }

        double tax = subtotal * 0.05; // 5% tax
        double total = subtotal + tax;

        subtotalField.setText("₱" + String.format("%.2f", subtotal));
        taxField.setText("₱" + String.format("%.2f", tax));
        totalField.setText("₱" + String.format("%.2f", total));
    }
}
