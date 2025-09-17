package ecommerce_package;


	
	

	import java.util.Map;

	public class InvoiceGenerator {
	    public static void generateInvoice(String orderId, int userId, Map<Integer,Integer> items, double total) {
	        System.out.println("Invoice for Order ID: " + orderId);
	        System.out.println("Customer: " + userId);
	        System.out.println("Items: " + items.toString());
	        System.out.println("Total: " + total);
	    }
	}



