package ecommerce_package;



import java.util.List;
import java.util.ArrayList;

public class AdminLogic {

    // ---------- Admin login ----------
    public boolean login(String username, String password) {
        // Replace with your DB login logic
        // Example: hardcoded admin credentials
        return "admin".equals(username) && "admin123".equals(password);
    }

    // ---------- Product CRUD ----------
    public boolean addProduct(Product p) {
        // Add product to database
        System.out.println("Adding product: " + p.getName());
        return true;  // return true if successful
    }

    public boolean updateProduct(Product p) {
        // Update product in database
        System.out.println("Updating product ID: " + p.getProductId());
        return true;
    }

    public boolean deleteProduct(int id) {
        // Delete product by ID
        System.out.println("Deleting product ID: " + id);
        return true;
    }

    // ---------- Category CRUD ----------
    public boolean addCategory(Category c) {
        System.out.println("Adding category: " + c.getName());
        return true;
    }

    public boolean updateCategory(Category c) {
        System.out.println("Updating category ID: " + c.getCategoryId());
        return true;
    }

    // ---------- View Orders ----------
    public List<Order> viewAllOrders() {
        // Return empty list for now
        return new ArrayList<Order>();
    }
}
