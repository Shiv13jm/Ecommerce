

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

