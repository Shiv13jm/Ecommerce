


package ecommerce_package;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcommerceApp extends JFrame {

    private static final long serialVersionUID = 1L;

    // Backend instances
    private final CustomerData customerData = new CustomerData();
    private final AdminLogic adminService = new AdminLogic();
    private final ProductData productData = new ProductData();
    private final OrderData orderService = new OrderData();

    public EcommerceApp() {
        setTitle("CARTIFY BY BTSS");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titleLabel = new JLabel("=====  CARTIFY BY BTSS  =====", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        // Buttons
        JButton registerBtn = new JButton("1. Register");
        JButton loginBtn = new JButton("2. Login");
        JButton viewProductsBtn = new JButton("3. View Products");
        JButton adminLoginBtn = new JButton("4. Admin Login");
        JButton exitBtn = new JButton("5. Exit");

        add(registerBtn);
        add(loginBtn);
        add(viewProductsBtn);
        add(adminLoginBtn);
        add(exitBtn);

        // ---------- Button Actions ----------

        // Register customer
        registerBtn.addActionListener(e -> {
            JTextField unameField = new JTextField();
            JPasswordField passField = new JPasswordField();
            JTextField emailField = new JTextField();
            JTextField phoneField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
            panel.add(new JLabel("Username:")); panel.add(unameField);
            panel.add(new JLabel("Password:")); panel.add(passField);
            panel.add(new JLabel("Email:")); panel.add(emailField);
            panel.add(new JLabel("Phone:")); panel.add(phoneField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Register", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean registered = customerData.register(
                        unameField.getText().trim(),
                        new String(passField.getPassword()).trim(),
                        emailField.getText().trim(),
                        phoneField.getText().trim()
                );
                JOptionPane.showMessageDialog(this, registered ? "✅ Registered!" : "❌ Registration failed!");
            }
        });

        // Customer login
        loginBtn.addActionListener(e -> {
            JTextField unameField = new JTextField();
            JPasswordField passField = new JPasswordField();
            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.add(new JLabel("Username:")); panel.add(unameField);
            panel.add(new JLabel("Password:")); panel.add(passField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                UserData user = customerData.login(unameField.getText().trim(), new String(passField.getPassword()).trim());
                if (user != null) {
                    JOptionPane.showMessageDialog(this, "✅ Welcome, " + user.getUsername());
                    customerMenu(user);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Invalid credentials!");
                }
            }
        });

        // View products
        viewProductsBtn.addActionListener(e -> viewProductsAction());

        // Admin login
        adminLoginBtn.addActionListener(e -> {
            JTextField unameField = new JTextField();
            JPasswordField passField = new JPasswordField();
            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.add(new JLabel("Admin Username:")); panel.add(unameField);
            panel.add(new JLabel("Admin Password:")); panel.add(passField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean isAdmin = adminService.login(unameField.getText().trim(), new String(passField.getPassword()).trim());
                if (isAdmin) {
                    JOptionPane.showMessageDialog(this, "✅ Admin logged in");
                    adminMenu();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Wrong admin credentials!");
                }
            }
        });

        // Exit button
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // ---------------- Customer Menu -----------------
    private void customerMenu(UserData user) {
        Map<Integer, Integer> cart = new HashMap<>();
        String[] options = {"View Products", "Add to Cart", "View Cart", "Remove from Cart", "Checkout & Pay", "Logout"};

        while (true) {
            int choice = JOptionPane.showOptionDialog(this,
                    "Customer Menu - " + user.getUsername(),
                    "Customer Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (choice == 0) viewProductsAction();
            else if (choice == 1) addToCartAction(cart);
            else if (choice == 2) viewCartAction(cart);
            else if (choice == 3) removeFromCartAction(cart); // ✅ Remove items
            else if (choice == 4) checkoutAndPayAction(user, cart);
            else break; // Logout
        }
    }

    // ---------------- Admin Menu -----------------
    private void adminMenu() {
        String[] options = {"View Products", "Add Product", "Logout"};

        while (true) {
            int choice = JOptionPane.showOptionDialog(this,
                    "Admin Menu",
                    "Admin Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (choice == 0) viewProductsAction();
            else if (choice == 1) addProductAction();
            else break; // Logout
        }
    }

    // ---------------- View Products -----------------
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

    // ---------------- Add to Cart -----------------
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

    // ---------------- View Cart -----------------
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

    // ---------------- Remove from Cart -----------------
    private void removeFromCartAction(Map<Integer, Integer> cart) {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        StringBuilder sb = new StringBuilder("Your Cart:\n");
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product p = productData.getProductById(entry.getKey());
            sb.append("ID: ").append(p.getProductId())
              .append(", Name: ").append(p.getName())
              .append(", Qty: ").append(entry.getValue())
              .append("\n");
        }
        sb.append("Enter Product ID to remove:");

        String pidStr = JOptionPane.showInputDialog(this, sb.toString());
        if (pidStr == null) return; // user canceled

        try {
            int pid = Integer.parseInt(pidStr.trim());
            if (cart.containsKey(pid)) {
                cart.remove(pid);
                JOptionPane.showMessageDialog(this, "✅ Product removed from cart!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Product ID not in cart!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ---------------- Checkout -----------------
    private void checkoutAndPayAction(UserData user, Map<Integer, Integer> cart) {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        // -------- Payment Mode Selection --------
        String[] paymentOptions = {"UPI", "Cash on Delivery", "Card"};
        int payChoice = JOptionPane.showOptionDialog(this,
                "Select Payment Mode",
                "Payment",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, paymentOptions, paymentOptions[0]);

        if (payChoice == -1) return; // user canceled
        String paymentMode = paymentOptions[payChoice];

        // -------- Place Order --------
        String orderId = orderService.placeOrder(user.getUserId(), cart);

        if (orderId != null) {
            // Calculate total
            double total = 0;
            Map<Product, Integer> itemDetails = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                Product p = productData.getProductById(entry.getKey());
                int qty = entry.getValue();
                total += p.getPrice() * qty;
                itemDetails.put(p, qty);
            }

            // Generate invoice
            InvoiceGenerator.generateInvoice(orderId, user, itemDetails, total, paymentMode);

            JOptionPane.showMessageDialog(this, "✅ Payment successful via " + paymentMode + "\nOrder ID: " + orderId);

            cart.clear(); // clear cart after successful order
        } else {
            JOptionPane.showMessageDialog(this, "❌ Order failed. Check stock or DB.");
        }
    }


    // ---------------- Add Product (Admin) -----------------
    private void addProductAction() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Price:")); panel.add(priceField);
        panel.add(new JLabel("Stock:")); panel.add(stockField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String desc = descField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());

                Product p = new Product(0, name, desc, price, stock, 1); // categoryId=1 for simplicity
                if (productData.addProduct(p)) {
                    JOptionPane.showMessageDialog(this, "✅ Product added!");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to add product.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    // ---------------- Main -----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EcommerceApp::new);
    }
}


