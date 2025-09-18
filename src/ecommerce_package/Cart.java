package ecommerce_package;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, Integer> items = new HashMap<>(); // productId -> quantity

    // Add product with quantity
    public void addItem(int productId, int quantity) {
        if (quantity <= 0) return;
        items.put(productId, items.getOrDefault(productId, 0) + quantity);
    }

    // Remove product completely
    public void removeItem(int productId) {
        items.remove(productId);
    }

    // Decrease quantity (remove if goes to 0 or below)
    public void decreaseQuantity(int productId, int quantity) {
        if (items.containsKey(productId)) {
            int current = items.get(productId);
            int updated = current - quantity;
            if (updated > 0) {
                items.put(productId, updated);
            } else {
                items.remove(productId);
            }
        }
    }

    // Get all items
    public Map<Integer, Integer> viewCart() {
        return items;
    }

    // Clear cart
    public void clearCart() {
        items.clear();
    }

    // Check if cart empty
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Get total unique items
    public int getTotalUniqueItems() {
        return items.size();
    }

    // Get total quantity across all products
    public int getTotalQuantity() {
        int total = 0;
        for (int qty : items.values()) {
            total += qty;
        }
        return total;
    }
}
