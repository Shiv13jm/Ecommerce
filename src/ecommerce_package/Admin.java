//package ecommerce_package;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Admin {
//
//    // ✅ Admin login
//    public boolean login(String username, String password) {
//        boolean isValid = false;
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, username);
//            ps.setString(2, password);
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                isValid = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isValid;
//    }
//
//    // ✅ Add Product
//    public boolean addProduct(Product p) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "INSERT INTO products (name, description, price, stock, category_id) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, p.getName());
//            ps.setString(2, p.getDescription());
//            ps.setDouble(3, p.getPrice());
//            ps.setInt(4, p.getStock());
//            ps.setInt(5, p.getCategoryId());
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Update Product
//    public boolean updateProduct(Product p) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "UPDATE products SET name=?, description=?, price=?, stock=?, category_id=? WHERE product_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, p.getName());
//            ps.setString(2, p.getDescription());
//            ps.setDouble(3, p.getPrice());
//            ps.setInt(4, p.getStock());
//            ps.setInt(5, p.getCategoryId());
//            ps.setInt(6, p.getProductId());
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Delete Product
//    public boolean deleteProduct(int productId) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "DELETE FROM products WHERE product_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, productId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Add Category
//    public boolean addCategory(Category c) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "INSERT INTO categories (name) VALUES (?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, c.getName());
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Update Category
//    public boolean updateCategory(Category c) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "UPDATE categories SET name=? WHERE category_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, c.getName());
//            ps.setInt(2, c.getCategoryId());
//            return ps.executeUpdate() > 0;
//       } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ Delete Category
//    public boolean deleteCategory(int categoryId) {
//      try (Connection conn = DBConnection.getConnection()) {
//            String sql = "DELETE FROM categories WHERE category_id=?";
//           PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, categoryId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ✅ View all Orders
//    public List<Order> viewAllOrders() {
//        List<Order> orders = new ArrayList<>();
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT * FROM orders";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Order o = new Order();
//               o.setOrderId(rs.getInt("order_id"));
//                o.setUserId(rs.getInt("user_id")); // ✅ corrected (was customerId)
//               o.setOrderDate(rs.getTimestamp("order_date"));
//                o.setStatus(rs.getString("status"));
//                o.setTotalAmount(rs.getDouble("total_amount"));
//                orders.add(o);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return orders;
//    }
//
//    // ✅ Generate simple sales report
//    public void generateReport() {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT status, COUNT(*) AS total_orders, SUM(total_amount) AS total_sales FROM orders GROUP BY status";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            System.out.println("\n📊 Sales Report:");
//            while (rs.next()) {
//                System.out.println("Status: " + rs.getString("status") +
//                        " | Orders: " + rs.getInt("total_orders") +
//                        " | Sales: ₹" + rs.getDouble("total_sales"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//   }
//}
//
//
//


//package ecommerce_package;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Admin {
//
//    // ---------- Admin login ----------
//    public boolean login(String username, String password) {
//        boolean isValid = false;
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT * FROM admins WHERE username=? AND password=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) isValid = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isValid;
//    }
//
//    // ---------- Product CRUD ----------
//    public boolean addProduct(Product p) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "INSERT INTO products (name, description, price, stock, category_id) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, p.getName());
//            ps.setString(2, p.getDescription());
//            ps.setDouble(3, p.getPrice());
//            ps.setInt(4, p.getStock());
//            ps.setInt(5, p.getCategoryId());
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean updateProduct(Product p) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "UPDATE products SET name=?, description=?, price=?, stock=?, category_id=? WHERE product_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, p.getName());
//            ps.setString(2, p.getDescription());
//            ps.setDouble(3, p.getPrice());
//            ps.setInt(4, p.getStock());
//            ps.setInt(5, p.getCategoryId());
//            ps.setInt(6, p.getProductId());
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean deleteProduct(int productId) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "DELETE FROM products WHERE product_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, productId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ---------- Category CRUD ----------
//    public boolean addCategory(Category c) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "INSERT INTO categories (name) VALUES (?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, c.getName());
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean updateCategory(Category c) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "UPDATE categories SET name=? WHERE category_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, c.getName());
//            ps.setInt(2, c.getCategoryId());
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean deleteCategory(int categoryId) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "DELETE FROM categories WHERE category_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, categoryId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ---------- Orders ----------
//    public List<Order> viewAllOrders() {
//        List<Order> orders = new ArrayList<>();
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT * FROM orders";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Order o = new Order();
//                o.setOrderId(rs.getInt("order_id"));
//                o.setUserId(rs.getInt("user_id"));
//                o.setOrderDate(rs.getTimestamp("order_date"));
//                o.setStatus(rs.getString("status"));
//                o.setTotalAmount(rs.getDouble("total_amount"));
//                orders.add(o);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return orders;
//    }
//
//    public boolean updateOrderStatus(int orderId, String status) {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "UPDATE orders SET status=? WHERE order_id=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, status);
//            ps.setInt(2, orderId);
//            return ps.executeUpdate() > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // ---------- Optional: generate report ----------
//    public void generateReport() {
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT status, COUNT(*) AS total_orders, SUM(total_amount) AS total_sales FROM orders GROUP BY status";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            System.out.println("\n📊 Sales Report:");
//            while (rs.next()) {
//                System.out.println("Status: " + rs.getString("status") +
//                        " | Orders: " + rs.getInt("total_orders") +
//                        " | Sales: ₹" + rs.getDouble("total_sales"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//

package ecommerce_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Admin {

    // Admin login
    public boolean login(String username, String password) {
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM admins WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add Product
    public boolean addProduct(Product p) {
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO products(name, description, price, stock, category_id) VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    // Update Product
    public boolean updateProduct(Product p) {
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE products SET name=?, description=?, price=?, stock=?, category_id=? WHERE product_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getCategoryId());
            ps.setInt(6, p.getProductId());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    // Delete Product
    public boolean deleteProduct(int productId) {
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM products WHERE product_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    // Add Category
    public boolean addCategory(Category c) {
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO categories(name) VALUES(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    // Update Category
    public boolean updateCategory(Category c) {
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE categories SET name=? WHERE category_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setInt(2, c.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    // Delete Category
    public boolean deleteCategory(int categoryId) {
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM categories WHERE category_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    // View All Orders
    public List<Order> viewAllOrders() {
        List<Order> orders = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM orders";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setUserId(rs.getInt("user_id"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setStatus(rs.getString("status"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                orders.add(o);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return orders;
    }
}
