package ecommerce_package;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/DB"; 
    private static final String USER = "root";  // your Workbench username
    private static final String PASSWORD = "root123"; // your Workbench password ("" if blank)

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to connect to DB!");
            return null;
        }
    }
}
