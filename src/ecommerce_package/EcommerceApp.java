

package ecommerce_package;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcommerceApp extends JFrame {

    private static final long serialVersionUID = 1L;

    private final CustomerData customerData = new CustomerData();
    private final AdminLogic adminService = new AdminLogic();
    private final ProductData productData = new ProductData();
    private final OrderData orderService = new OrderData();

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Map<Integer, Integer> cart;
    private UserData loggedInUser;

    public EcommerceApp() {
        setTitle("CARTIFY BY BTSS");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createHomePanel(), "home");
        mainPanel.add(createCustomerPanel(), "customer");
        mainPanel.add(createAdminPanel(), "admin");

        add(mainPanel);
        cardLayout.show(mainPanel, "home");
        setVisible(true);
    }

    // ---------------- Home Panel -----------------
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel title = new JLabel("=====  CARTIFY BY BTSS  =====", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title);

        JButton registerBtn = new JButton("Register");
        JButton loginBtn = new JButton("Customer Login");
        JButton viewProductsBtn = new JButton("View Products");
        JButton adminLoginBtn = new JButton("Admin Login");
        JButton exitBtn = new JButton("Exit");

        panel.add(registerBtn);
        panel.add(loginBtn);
        panel.add(viewProductsBtn);
        panel.add(adminLoginBtn);
        panel.add(exitBtn);

        registerBtn.addActionListener(e -> registerAction());
        loginBtn.addActionListener(e -> customerLoginAction());
        viewProductsBtn.addActionListener(e -> viewProductsAction());
        adminLoginBtn.addActionListener(e -> adminLoginAction());
        exitBtn.addActionListener(e -> System.exit(0));

        return panel;
    }

    // ---------------- Customer Panel -----------------
    private JPanel customerPanel;
    private JPanel createCustomerPanel() {
        customerPanel = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        customerPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JButton viewProductsBtn = new JButton("View Products");
        JButton addToCartBtn = new JButton("Add to Cart");
        JButton viewCartBtn = new JButton("View Cart");
        JButton checkoutBtn = new JButton("Checkout");
        JButton logoutBtn = new JButton("Logout");

        btnPanel.add(viewProductsBtn);
        btnPanel.add(addToCartBtn);
        btnPanel.add(viewCartBtn);
        btnPanel.add(checkoutBtn);
        btnPanel.add(logoutBtn);

        customerPanel.add(btnPanel, BorderLayout.SOUTH);

        viewProductsBtn.addActionListener(e -> viewProductsAction());
        addToCartBtn.addActionListener(e -> addToCartAction(cart));
        viewCartBtn.addActionListener(e -> viewCartAction(cart));
        checkoutBtn.addActionListener(e -> checkoutAction());
        logoutBtn.addActionListener(e -> {
            cart.clear();
            cardLayout.show(mainPanel, "home");
        });

        return customerPanel;
    }

    // ---------------- Admin Panel -----------------
    private JPanel adminPanel;
    private JPanel createAdminPanel() {
        adminPanel = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        adminPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JButton viewProductsBtn = new JButton("View Products");
        JButton addProductBtn = new JButton("Add Product");
        JButton editProductBtn = new JButton("Edit Product");
        JButton deleteProductBtn = new JButton("Delete Product");
        JButton logoutBtn = new JButton("Logout");

        btnPanel.add(viewProductsBtn);
        btnPanel.add(addProductBtn);
        btnPanel.add(editProductBtn);
        btnPanel.add(deleteProductBtn);
        btnPanel.add(logoutBtn);

        adminPanel.add(btnPanel, BorderLayout.SOUTH);

        viewProductsBtn.addActionListener(e -> viewProductsAction());
        addProductBtn.addActionListener(e -> addProductAction());
        editProductBtn.addActionListener(e -> editProductAction());
        deleteProductBtn.addActionListener(e -> deleteProductAction());
        logoutBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));

        return adminPanel;
    }

    // ---------------- Actions -----------------
    private void registerAction() {
        JTextField unameField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Username:")); panel.add(unameField);
        panel.add(new JLabel("Password:")); panel.add(passField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            boolean registered = customerData.register(
                    unameField.getText().trim(),
                    new String(passField.getPassword()).trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim()
            );
            JOptionPane.showMessageDialog(this, registered ? "✅ Registered!" : "❌ Registration failed!");
        }
    }

    private void customerLoginAction() {
        JTextField unameField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Username:")); panel.add(unameField);
        panel.add(new JLabel("Password:")); panel.add(passField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            loggedInUser = customerData.login(unameField.getText().trim(),
                    new String(passField.getPassword()).trim());
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(this, "✅ Welcome, " + loggedInUser.getUsername());
                cart = new HashMap<>();
                cardLayout.show(mainPanel, "customer");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid credentials!");
            }
        }
    }

    private void adminLoginAction() {
        JTextField unameField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Admin Username:")); panel.add(unameField);
        panel.add(new JLabel("Admin Password:")); panel.add(passField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            boolean isAdmin = adminService.login(unameField.getText().trim(),
                    new String(passField.getPassword()).trim());
            if (isAdmin) {
                JOptionPane.showMessageDialog(this, "✅ Admin logged in");
                cardLayout.show(mainPanel, "admin");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Wrong admin credentials!");
            }
        }
    }

    private void viewProductsAction() {
        List<Product> products = productData.getAllProducts();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products available.");
            return;
        }
        StringBuilder sb = new StringBuilder("Available Products:\n");
        for (Product p : products) {
            sb.append("ID: ").append(p.getProductId())
                    .append(", Name: ").append(p.getName())
                    .append(", Price: ₹").append(p.getPrice())
                    .append(", Stock: ").append(p.getStock())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void addProductAction() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField catField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Price:")); panel.add(priceField);
        panel.add(new JLabel("Stock:")); panel.add(stockField);
        panel.add(new JLabel("Category ID:")); panel.add(catField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Product p = new Product(0, nameField.getText().trim(), descField.getText().trim(),
                        Double.parseDouble(priceField.getText().trim()),
                        Integer.parseInt(stockField.getText().trim()),
                        Integer.parseInt(catField.getText().trim()));

                if (adminService.addProduct(p)) {
                    JOptionPane.showMessageDialog(this, "✅ Product added!");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to add product.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    private void editProductAction() {
        try {
            String pidStr = JOptionPane.showInputDialog(this, "Enter Product ID to update:");
            if (pidStr == null) return;
            int pid = Integer.parseInt(pidStr.trim());

            Product p = adminService.getProductById(pid);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "❌ Product not found!");
                return;
            }

            JTextField nameField = new JTextField(p.getName());
            JTextField descField = new JTextField(p.getDescription());
            JTextField priceField = new JTextField(String.valueOf(p.getPrice()));
            JTextField stockField = new JTextField(String.valueOf(p.getStock()));
            JTextField catField = new JTextField(String.valueOf(p.getCategoryId()));

            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.add(new JLabel("Name:")); panel.add(nameField);
            panel.add(new JLabel("Description:")); panel.add(descField);
            panel.add(new JLabel("Price:")); panel.add(priceField);
            panel.add(new JLabel("Stock:")); panel.add(stockField);
            panel.add(new JLabel("Category ID:")); panel.add(catField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                p.setName(nameField.getText().trim());
                p.setDescription(descField.getText().trim());
                p.setPrice(Double.parseDouble(priceField.getText().trim()));
                p.setStock(Integer.parseInt(stockField.getText().trim()));
                p.setCategoryId(Integer.parseInt(catField.getText().trim()));

                if (adminService.updateProduct(p)) {
                    JOptionPane.showMessageDialog(this, "✅ Product updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Update failed!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void deleteProductAction() {
        try {
            String pidStr = JOptionPane.showInputDialog(this, "Enter Product ID to delete:");
            if (pidStr == null) return;
            int pid = Integer.parseInt(pidStr.trim());

            if (adminService.deleteProduct(pid)) {
                JOptionPane.showMessageDialog(this, "✅ Product deleted!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Delete failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ---------------- Cart / Checkout -----------------
    private void addToCartAction(Map<Integer, Integer> cart) {
        try {
            String pidStr = JOptionPane.showInputDialog(this, "Enter Product ID to add:");
            if (pidStr == null) return;
            int pid = Integer.parseInt(pidStr.trim());

            String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity:");
            if (qtyStr == null) return;
            int qty = Integer.parseInt(qtyStr.trim());

            Product product = productData.getProductById(pid);
            if (product == null) {
                JOptionPane.showMessageDialog(this, "❌ Product not found!");
                return;
            }
            cart.put(pid, cart.getOrDefault(pid, 0) + qty);
            JOptionPane.showMessageDialog(this, "✅ Added to cart!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void viewCartAction(Map<Integer, Integer> cart) {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }
        StringBuilder sb = new StringBuilder("Your Cart:\n");
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product p = productData.getProductById(entry.getKey());
            int qty = entry.getValue();
            double subtotal = p.getPrice() * qty;
            total += subtotal;
            sb.append(p.getName()).append(" x ").append(qty)
                    .append(" = ₹").append(subtotal).append("\n");
        }
        sb.append("Total: ₹").append(total);
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void checkoutAction() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        String[] paymentOptions = {"UPI", "Cash on Delivery", "Card"};
        int payChoice = JOptionPane.showOptionDialog(this, "Select Payment Mode", "Payment",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, paymentOptions, paymentOptions[0]);
        if (payChoice == -1) return;
        String paymentMode = paymentOptions[payChoice];

        String orderId = orderService.placeOrder(loggedInUser.getUserId(), cart);
        if (orderId != null) {
            double total = 0;
            Map<Product, Integer> itemDetails = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                Product p = productData.getProductById(entry.getKey());
                int qty = entry.getValue();
                total += p.getPrice() * qty;
                itemDetails.put(p, qty);
            }
            InvoiceGenerator.generateInvoice(orderId, loggedInUser, itemDetails, total, paymentMode);
            JOptionPane.showMessageDialog(this, "✅ Payment successful via " + paymentMode + "\nOrder ID: " + orderId);
            cart.clear();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Order failed.");
        }
    }

    // ---------------- Main -----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EcommerceApp::new);
    }
}
