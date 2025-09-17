


package ecommerce_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductData {

    // Fetch all products
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id")
                );
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // Fetch a single product by ID
    public Product getProductById(int pid) {
        Product product = null;
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pid);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product; // returns null if not found
    }

    // Add a new product
    public boolean addProduct(Product p) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO products(name, description, price, stock, category_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getStock());
            stmt.setInt(5, p.getCategoryId());

            int rows = stmt.executeUpdate();
            return rows > 0; // success if at least 1 row inserted
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


//Inside ProductData class
public boolean updateStock(int productId, int newStock) {
 String sql = "UPDATE products SET stock = ? WHERE product_id = ?";
 
 try (Connection conn = DBConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {
     
     stmt.setInt(1, newStock);
     stmt.setInt(2, productId);
     
     int rows = stmt.executeUpdate();
     return rows > 0; // returns true if at least 1 row updated
     
 } catch (Exception e) {
     e.printStackTrace();
     return false;
 }
}


	
	
}



