
package ecommerce_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerData {

    // Register new customer
    public boolean register(String username, String password, String email, String phone) {
        String sql = "INSERT INTO users(username, password, email, phone, role) VALUES (?, ?, ?, ?, 'CUSTOMER')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, phone);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Customer login
    public UserData login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=? AND role='CUSTOMER'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	UserData u = new UserData();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Browse all products
    public List<Product> browseProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setDescription(rs.getString("description"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // Search products by name
    public List<Product> searchByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setDescription(rs.getString("description"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // Search products by category
    public List<Product> searchByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setDescription(rs.getString("description"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // Search products by price range
    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, min);
            stmt.setDouble(2, max);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setDescription(rs.getString("description"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // Checkout → calls Order module
    public boolean checkout(int userId, Map<Integer, Integer> cartItems) {
        Order orderService = new Order();
        String orderId = orderService.placeOrder(userId, cartItems);
        return orderId != null;
    }

    // Track order
    public void trackOrder(String orderId) {
        String sql = "SELECT status FROM orders WHERE order_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Order Status: " + rs.getString("status"));
            } else {
                System.out.println("Order not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View order history
    public void viewOrderHistory(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("OrderID: " + rs.getInt("order_id") + 
                                   " | Date: " + rs.getTimestamp("order_date") + 
                                   " | Total: $" + rs.getDouble("total_amount") + 
                                   " | Status: " + rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

