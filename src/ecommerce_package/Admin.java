package ecommerce_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Admin {

    // ✅ Admin login
    public boolean login(String username, String password) {
        boolean isValid = false;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    // ✅ Add Product
    public boolean addProduct(Product p) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO products (name, description, price, stock, category_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Update Product
    public boolean updateProduct(Product p) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE products SET name=?, description=?, price=?, stock=?, category_id=? WHERE product_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getCategoryId());
            ps.setInt(6, p.getProductId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Delete Product
    public boolean deleteProduct(int productId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM products WHERE product_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Add Category
    public boolean addCategory(Category c) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO categories (name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Update Category
    public boolean updateCategory(Category c) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE categories SET name=? WHERE category_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setInt(2, c.getCategoryId());
            return ps.executeUpdate() > 0;
       } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Delete Category
    public boolean deleteCategory(int categoryId) {
      try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM categories WHERE category_id=?";
           PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ View all Orders
    public List<Order> viewAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM orders";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
               o.setOrderId(rs.getInt("order_id"));
                o.setUserId(rs.getInt("user_id")); // ✅ corrected (was customerId)
               o.setOrderDate(rs.getTimestamp("order_date"));
                o.setStatus(rs.getString("status"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                orders.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // ✅ Generate simple sales report
    public void generateReport() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT status, COUNT(*) AS total_orders, SUM(total_amount) AS total_sales FROM orders GROUP BY status";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n📊 Sales Report:");
            while (rs.next()) {
                System.out.println("Status: " + rs.getString("status") +
                        " | Orders: " + rs.getInt("total_orders") +
                        " | Sales: ₹" + rs.getDouble("total_sales"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
}








//package ecommerce_package;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//
//
//	public class Admin extends JFrame {
//
//	    private static final long serialVersionUID = 1L;
//
//	    // Your fields, buttons, listeners here
//	
//
//
//    // Logic object (if Admin logic is separate, otherwise this can be removed)
//    // private AdminLogic adminLogic;
//
//    // Product fields
//    private JTextField productIdField, nameField, descField, priceField, stockField, categoryField;
//
//    // Category fields
//    private JTextField categoryIdField, categoryNameField;
//
//    // Orders display
//    private JTextArea ordersArea;
//
//    public Admin() {
//        // If logic is separate
//        // adminLogic = new AdminLogic();
//
//        setTitle("Admin Dashboard");
//        setSize(700, 700);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new FlowLayout());
//
//        // --------- Product Section ----------
//        add(new JLabel("Product ID:"));
//        productIdField = new JTextField(10);
//        add(productIdField);
//
//        add(new JLabel("Name:"));
//        nameField = new JTextField(10);
//        add(nameField);
//
//        add(new JLabel("Description:"));
//        descField = new JTextField(10);
//        add(descField);
//
//        add(new JLabel("Price:"));
//        priceField = new JTextField(10);
//        add(priceField);
//
//        add(new JLabel("Stock:"));
//        stockField = new JTextField(10);
//        add(stockField);
//
//        add(new JLabel("Category ID:"));
//        categoryField = new JTextField(10);
//        add(categoryField);
//
//        JButton addProductBtn = new JButton("Add Product");
//        add(addProductBtn);
//
//        JButton updateProductBtn = new JButton("Update Product");
//        add(updateProductBtn);
//
//        JButton deleteProductBtn = new JButton("Delete Product");
//        add(deleteProductBtn);
//
//        // --------- Category Section ----------
//        add(new JLabel("Category ID:"));
//        categoryIdField = new JTextField(10);
//        add(categoryIdField);
//
//        add(new JLabel("Category Name:"));
//        categoryNameField = new JTextField(10);
//        add(categoryNameField);
//
//        JButton addCategoryBtn = new JButton("Add Category");
//        add(addCategoryBtn);
//
//        JButton updateCategoryBtn = new JButton("Update Category");
//        add(updateCategoryBtn);
//
//        // --------- Orders Section ----------
//        JButton viewOrdersBtn = new JButton("View Orders");
//        add(viewOrdersBtn);
//
//        ordersArea = new JTextArea(10, 50);
//        ordersArea.setEditable(false);
//        add(new JScrollPane(ordersArea));
//
//        // ------------------ Action Listeners ------------------
//
//        addProductBtn.addActionListener(e -> {
//            try {
//                Product p = new Product(
//                        0,
//                        nameField.getText(),
//                        descField.getText(),
//                        Double.parseDouble(priceField.getText()),
//                        Integer.parseInt(stockField.getText()),
//                        Integer.parseInt(categoryField.getText())
//                );
//
//                if (this.addProduct(p)) {
//                    JOptionPane.showMessageDialog(Admin.this, "Product added!");
//                } else {
//                    JOptionPane.showMessageDialog(Admin.this, "Failed to add product!");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(Admin.this, "Invalid input! " + ex.getMessage());
//            }
//        });
//
//        updateProductBtn.addActionListener(e -> {
//            try {
//                Product p = new Product(
//                        Integer.parseInt(productIdField.getText()),
//                        nameField.getText(),
//                        descField.getText(),
//                        Double.parseDouble(priceField.getText()),
//                        Integer.parseInt(stockField.getText()),
//                        Integer.parseInt(categoryField.getText())
//                );
//
//                if (this.updateProduct(p)) {
//                    JOptionPane.showMessageDialog(Admin.this, "Product updated!");
//                } else {
//                    JOptionPane.showMessageDialog(Admin.this, "Failed to update product!");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(Admin.this, "Invalid input! " + ex.getMessage());
//            }
//        });
//
//        deleteProductBtn.addActionListener(e -> {
//            try {
//                int id = Integer.parseInt(productIdField.getText());
//                if (this.deleteProduct(id)) {
//                    JOptionPane.showMessageDialog(Admin.this, "Product deleted!");
//                } else {
//                    JOptionPane.showMessageDialog(Admin.this, "Failed to delete product!");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(Admin.this, "Invalid Product ID!");
//            }
//        });
//
//        addCategoryBtn.addActionListener(e -> {
//            try {
//                Category c = new Category(
//                        0,
//                        categoryNameField.getText()
//                );
//
//                if (this.addCategory(c)) {
//                    JOptionPane.showMessageDialog(Admin.this, "Category added!");
//                } else {
//                    JOptionPane.showMessageDialog(Admin.this, "Failed to add category!");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(Admin.this, "Invalid input!");
//            }
//        });
//
//        updateCategoryBtn.addActionListener(e -> {
//            try {
//                Category c = new Category(
//                        Integer.parseInt(categoryIdField.getText()),
//                        categoryNameField.getText()
//                );
//
//                if (this.updateCategory(c)) {
//                    JOptionPane.showMessageDialog(Admin.this, "Category updated!");
//                } else {
//                    JOptionPane.showMessageDialog(Admin.this, "Failed to update category!");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(Admin.this, "Invalid input!");
//            }
//        });
//
//        viewOrdersBtn.addActionListener(e -> {
//            List<Order> orders = this.viewAllOrders();
//            ordersArea.setText("");
//            for (Order o : orders) {
//                ordersArea.append(o.toString() + "\n");
//            }
//        });
//
//        setVisible(true);
//    }
//
//    // ------------------ Business Logic Stubs ------------------
//    // Replace these with your actual Admin methods that interact with DB
//
//    public boolean addProduct(Product p) {
//        // Your existing addProduct logic
//        return true; // placeholder
//    }
//
//    public boolean updateProduct(Product p) {
//        // Your existing updateProduct logic
//        return true; // placeholder
//    }
//
//    public boolean deleteProduct(int id) {
//        // Your existing deleteProduct logic
//        return true; // placeholder
//    }
//
//    public boolean addCategory(Category c) {
//        return true; // placeholder
//    }
//
//    public boolean updateCategory(Category c) {
//        return true; // placeholder
//    }
//
//    public List<Order> viewAllOrders() {
//        return List.of(); // placeholder
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Admin::new);
//    }
//}
