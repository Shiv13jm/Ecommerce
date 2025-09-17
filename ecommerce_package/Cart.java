package ecommerce_package;
import java.util.HashMap;
import java.util.Map;
public class Cart {
	    private Map<Integer, Integer> items = new HashMap<>(); 

	    public void addItem(int productId, int quantity) {
	        items.put(productId, items.getOrDefault(productId, 0) + quantity);
	    }

	    public void removeItem(int productId) {
	        items.remove(productId);
	    }

	    public Map<Integer, Integer> viewCart() {
	        return items;
	    }

	    public void clearCart() {
	        items.clear();
	    }
	}


