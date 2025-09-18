package ecommerce_package;

import java.util.Map;

public class InvoiceGenerator {
    public static void generateInvoice(String orderId, UserData user, 
                                       Map<Product, Integer> items, 
                                       double total, String paymentMode) {
        System.out.println("\n===== INVOICE =====");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + user.getUsername() + " (ID: " + user.getUserId() + ")");
        System.out.println("Email: " + user.getEmail());
        System.out.println("---------------------------");

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double lineTotal = p.getPrice() * qty;
            System.out.printf("%s (x%d) - ₹%.2f%n", p.getName(), qty, lineTotal);
        }

        System.out.println("---------------------------");
        System.out.printf("Total: ₹%.2f%n", total);
        System.out.println("Payment Mode: " + paymentMode);
        System.out.println("===== THANK YOU! =====\n");
    }
}
