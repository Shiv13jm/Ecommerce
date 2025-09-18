//
//
//
//package ecommerce_package;
//
//import java.util.Map;
//
//public class Order {
//
//    private ProductData productData = new ProductData(); // access product info
//
//    public String placeOrder(int userId, Map<Integer, Integer> cartItems) {
//
//        double total = 0.0;
//
//        // Iterate through cart and calculate total
//        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
//            int productId = entry.getKey();
//            int quantity = entry.getValue();
//
//            // Fetch product from DB
//            Product product = productData.getProductById(productId);
//            if (product == null) {
//                System.out.println("❌ Product ID " + productId + " not found. Skipping...");
//                continue;
//            }
//
//            // Check if enough stock exists
//            if (quantity > product.getStock()) {
//                System.out.println("❌ Not enough stock for " + product.getName() + ". Available: " + product.getStock());
//                continue;
//            }
//
//            // Update total
//            total += product.getPrice() * quantity;
//
//            // Update stock in DB
//            int newStock = product.getStock() - quantity;
//            productData.updateStock(productId, newStock);
//        }
//
//        if (total == 0.0) {
//            System.out.println("❌ No valid products in cart. Order not placed.");
//            return null;
//        }
//
//        // Generate order ID
//        String orderId = IDGenerator.generateOrderId();
//
//        // Generate invoice with total
//        InvoiceGenerator.generateInvoice(orderId, userId, cartItems, total);
//
//        System.out.printf("✅ Order placed successfully! Total amount: ₹%.2f%n", total);
//
//        return orderId;
//    }
//
//    public boolean updateStatus(String orderId, String status) {
//        // Implement DB update for order status if needed
//        return false;
//    }
//}

package ecommerce_package;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private String status;
    private double totalAmount;

    // Constructors
    public Order() {}

    public Order(int orderId, int userId, Timestamp orderDate, String status, double totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getters & Setters
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId +
               ", userId=" + userId +
               ", orderDate=" + orderDate +
               ", status=" + status +
               ", totalAmount=" + totalAmount + "]";
    }
}

