package ecommerce_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderData {
	// ✅ Fetch all orders for a given user
    public List<Order> getOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id"));
                o.setUserId(rs.getInt("user_id"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setStatus(rs.getString("status"));
                o.setTotalAmount(rs.getDouble("total_amount"));

                orders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Place a new order
    public String placeOrder(int userId, Map<Integer, Integer> cartItems) {
        String insertOrder = "INSERT INTO orders(user_id, order_date, status, total_amount) VALUES (?, NOW(), 'PENDING', ?)";
        String insertOrderItem = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        String getProduct = "SELECT price FROM products WHERE product_id=?";

        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        PreparedStatement productStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // transaction start

            double totalAmount = 0.0;

            // Calculate total
            productStmt = conn.prepareStatement(getProduct);
            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                int productId = entry.getKey();
                int qty = entry.getValue();

                productStmt.setInt(1, productId);
                rs = productStmt.executeQuery();
                if (rs.next()) {
                    double price = rs.getDouble("price");
                    totalAmount += price * qty;
                }
            }

            // Insert order
            orderStmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, totalAmount);
            orderStmt.executeUpdate();

            // Get generated order ID
            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            // Insert order items
            itemStmt = conn.prepareStatement(insertOrderItem);
            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                int productId = entry.getKey();
                int qty = entry.getValue();

                productStmt.setInt(1, productId);
                rs = productStmt.executeQuery();
                if (rs.next()) {
                    double price = rs.getDouble("price");

                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, productId);
                    itemStmt.setInt(3, qty);
                    itemStmt.setDouble(4, price);
                    itemStmt.addBatch();
                }
            }
            itemStmt.executeBatch();

            conn.commit();
            return String.valueOf(orderId);

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (orderStmt != null) orderStmt.close(); } catch (Exception ignored) {}
            try { if (itemStmt != null) itemStmt.close(); } catch (Exception ignored) {}
            try { if (productStmt != null) productStmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
        return null;
    }
}
