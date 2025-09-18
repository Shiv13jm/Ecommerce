package ecommerce_package;
import java.util.List;
public class Customer_module {

    public boolean register(String username, String password, String email) {
        // Insert into users table with role='CUSTOMER'
        return false;
    }

    public User login(String username, String password) {
        // Validate username & password
        return null;
    }

    public List<Product> browseProducts() {
       
        return null;
    }

    public List<Product> searchByName(String name) {
        return null;
    }

    public List<Product> searchByCategory(int categoryId) {
        return null;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        return null;
    }

    public boolean checkout(int userId) {
       
        return false;
    }

    public void trackOrder(String orderId) {
      
    }

    public void viewOrderHistory(int userId) {
    
    }
}


	