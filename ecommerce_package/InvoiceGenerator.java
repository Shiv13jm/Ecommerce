package ecommerce_package;

import java.util.Map;

public class InvoiceGenerator {

    public static void generateInvoice(String orderId, UserData user, Map<Product, Integer> items, double total, String paymentMode) {
        System.out.println("\n========== INVOICE ==========");
        System.out.println("Order ID   : " + orderId);
        System.out.println("Customer   : " + user.getUsername());
        System.out.println("Email      : " + user.getEmail());
        System.out.println("Phone      : " + user.getPhone());
        System.out.println("-----------------------------");
        System.out.println("Items Purchased:");
        System.out.println("Product\t\tQty\tPrice\tSubtotal");

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            double subtotal = product.getPrice() * qty;
            System.out.printf("%s\t%d\t₹%.2f\t₹%.2f%n",
                    product.getName(), qty, product.getPrice(), subtotal);
        }

        System.out.println("-----------------------------");
        System.out.printf("TOTAL AMOUNT: ₹%.2f%n", total);
        System.out.println("Payment Mode: " + paymentMode);
        System.out.println("=============================");
        System.out.println("✅ Thank you for shopping with us!\n");
    }
}
