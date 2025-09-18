package ecommerce_package;

public class Main {
    public static void main(String[] args) {
        CustomerData customerService = new CustomerData();

        // Test Register
        boolean registered = customerService.register("shivani", "12345", "shivani@gmail.com", "9876543210");
        System.out.println("Register success: " + registered);

        // Test Login
        UserData u = customerService.login("shivani", "12345");
        if (u != null) {
            System.out.println("Login success! Welcome " + u.getUsername() + ", role: " + u.getRole());
        } else {
            System.out.println("Login failed!");
        }
    }
}
