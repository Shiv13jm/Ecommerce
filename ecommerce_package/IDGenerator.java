package ecommerce_package;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IDGenerator {
    public static String generateOrderId() {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int rand = (int)(Math.random() * 9000) + 1000;
        return "ORD" + ts + rand;
    }
}

