package ecommerce_package;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class EcommerceApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Instances (these classes must exist in your project)
        CustomerData customerData = new CustomerData(); // handles register/login
        Admin adminService = new Admin();               // admin logic (login, etc.)
        ProductData productData = new ProductData();    // product DB operations
        Order orderService = new Order();               // order placement

        while (true) {
            System.out.println("\n===== E-COMMERCE PORTAL =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Products");
            System.out.println("4. Admin Login");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: {
                    System.out.print("Enter username: ");
                    String uname = sc.nextLine().trim();
                    System.out.print("Enter password: ");
                    String pass = sc.nextLine().trim();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine().trim();
                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine().trim();

                    boolean registered = customerData.register(uname, pass, email, phone);
                    System.out.println(registered ? "✅ Registered!" : "❌ Registration failed!");
                    break;
                }

                case 2: {
                    System.out.print("Enter username: ");
                    String loginUser = sc.nextLine().trim();
                    System.out.print("Enter password: ");
                    String loginPass = sc.nextLine().trim();

                    UserData user = customerData.login(loginUser, loginPass);
                    if (user != null) {
                        System.out.println("✅ Welcome, " + user.getUsername());
                        customerMenu(sc, productData, orderService, user);
                    } else {
                        System.out.println("❌ Invalid credentials!");
                    }
                    break;
                }

                case 3: {
                    List<Product> products = productData.getAllProducts();
                    displayProducts(products);
                    break;
                }

                case 4: {
                    System.out.print("Enter admin username: ");
                    String adminUser = sc.nextLine().trim();
                    System.out.print("Enter admin password: ");
                    String adminPass = sc.nextLine().trim();

                    boolean isAdmin = adminService.login(adminUser, adminPass);
                    if (isAdmin) {
                        System.out.println("✅ Admin logged in");
                        adminMenu(sc, productData, adminService);
                    } else {
                        System.out.println("❌ Wrong admin credentials!");
                    }
                    break;
                }

                case 5:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice, try again!");
            }
        }
    }

    // ---------- Customer menu ----------
    private static void customerMenu(Scanner sc, ProductData productData, Order orderService, UserData user) {
        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. View Products");
            System.out.println("2. Buy Product (single item quick-buy)");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1: {
                    displayProducts( productData.getAllProducts());
                    break;
                }
                case 2: {
                    System.out.print("Enter Product ID to buy: ");
                    int pid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter quantity: ");
                    int qty = Integer.parseInt(sc.nextLine().trim());

                    Product product = productData.getProductById(pid);
                    if (product == null) {
                        System.out.println("❌ Product not found!");
                        break;
                    }

                    double totalPrice = product.getPrice() * qty; // ✅ calculate total price

                    System.out.printf("Total price: ₹%.2f%n", totalPrice);

                    // Create a simple cart map with single item
                    Map<Integer, Integer> cart = new HashMap<>();
                    cart.put(pid, qty);

                    String orderId = orderService.placeOrder(user.getUserId(), cart);
                    if (orderId != null) {
                        System.out.println("✅ Order placed. Order ID: " + orderId);
                    } else {
                        System.out.println("❌ Order failed. Check stock or DB.");
                    }
                    break;
                }

                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ---------- Admin menu ----------
    private static void adminMenu(Scanner sc, ProductData productData, Admin adminService) {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1: {
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Enter description: ");
                    String desc = sc.nextLine().trim();
                    System.out.print("Enter price: ");
                    double price = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Enter stock: ");
                    int stock = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter categoryId (or 0): ");
                    int categoryId = Integer.parseInt(sc.nextLine().trim());

                    Product p = new Product(0, name, desc, price, stock, categoryId);
                    boolean added = productData.addProduct(p);
                    System.out.println(added ? "✅ Product added" : "❌ Failed to add product");
                    break;
                }
                case 2: {
                    displayProducts(productData.getAllProducts());
                    break;
                }
                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ---------- Utility ----------
    private static void displayProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        System.out.println("\nID | Name | Price | Stock | CategoryId");
        for (Product p : products) {
            System.out.printf("%d | %s | %.2f | %d | %d%n",
                    p.getProductId(), p.getName(), p.getPrice(), p.getStock(), p.getCategoryId());
        }
    }
}
