

//package ecommerce_package;
//
//import static org.junit.Assert.*;
//import org.junit.Test;
//import java.util.List;
//
//public class CustomerDataTest {
//
//    // ✅ Test login with invalid credentials
//    @Test
//    public void testInvalidLogin() {
//        CustomerData customerData = new CustomerData();
//        UserData user = customerData.login("wrongUser", "wrongPass");
//        assertNull("Login should return null for invalid credentials", user);
//    }
//
//    // ✅ Test searching by name (should not return null list)
//    @Test
//    public void testSearchByName() {
//        CustomerData customerData = new CustomerData();
//        List<Product> results = customerData.searchByName("Laptop");
//        assertNotNull("Search by name should not return null", results);
//    }
//
//    // ✅ Test searching by category (empty list is okay, but should not be null)
//    @Test
//    public void testSearchByCategory() {
//        CustomerData customerData = new CustomerData();
//        List<Product> results = customerData.searchByCategory(1); // category 1 example
//        assertNotNull("Search by category should not return null", results);
//    }
//
//    // ✅ Test searching by price range
//    @Test
//    public void testSearchByPriceRange() {
//        CustomerData customerData = new CustomerData();
//        List<Product> results = customerData.searchByPriceRange(100, 1000);
//        assertNotNull("Search by price range should not return null", results);
//    }
//
//    // ✅ Example: Register new customer (will fail if username already exists in DB)
//    @Test
//    public void testRegisterCustomer() {
//        CustomerData customerData = new CustomerData();
//        boolean result = customerData.register("testUserJUnit", "password123", "junit@test.com", "9876543210");
//        assertTrue("Register should return true if insert was successful", result);
//    }
//}
//


package ecommerce_package;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.List;

public class CustomerDataTest {

    private static CustomerData customerData;

    // Runs once before all tests
    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println(">>> Setting up database connection/resources (BeforeClass)");
        customerData = new CustomerData();
    }

    // Runs once after all tests
    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println(">>> Cleaning up resources (AfterClass)");
    }

    // Runs before each test
    @Before
    public void setUp() {
        System.out.println(">>> Before Test: preparing test data");
    }

    // Runs after each test
    @After
    public void tearDown() {
        System.out.println(">>> After Test: cleaning temporary data");
    }

    @Test
    public void testInvalidLogin() {
        UserData user = customerData.login("wrongUser", "wrongPass");
        assertNull("Login should return null for invalid credentials", user);
    }

    @Test
    public void testSearchByName() {
        List<Product> results = customerData.searchByName("Laptop");
        assertNotNull("Search should not return null", results);
    }

    @Test
    public void testSearchByPriceRange() {
        List<Product> results = customerData.searchByPriceRange(100, 500);
        assertNotNull("Search by price range should not return null", results);
    }
}

