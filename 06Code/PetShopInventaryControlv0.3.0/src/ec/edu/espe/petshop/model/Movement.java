package ec.edu.espe.petshop.model;
//
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movement {
    public enum Type { PURCHASE, SALE }
    private String date; // yyyy-MM-dd
    private Type type;
    private String productId;
    private int quantity;

    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public Movement(String date, Type type, String productId, int quantity) {
        this.date = date;
        this.type = type;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Movement(Type type, String productId, int quantity) {
        this(LocalDate.now().format(F), type, productId, quantity);
    }

    public String getDate() { return date; }
    public Type getType() { return type; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return date + " | " + type + " | " + productId + " | qty: " + quantity;
    }
}
