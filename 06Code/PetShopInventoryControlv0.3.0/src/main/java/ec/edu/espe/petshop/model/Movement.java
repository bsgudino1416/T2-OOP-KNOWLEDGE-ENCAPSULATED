package ec.edu.espe.petshop.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movement {
    public enum Type { PURCHASE, SALE }
    private LocalDate date;
    private Type type;
    private String productId;
    private int quantity;

    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public Movement(LocalDate date, Type type, String productId, int quantity) {
        this.date = date;
        this.type = type;
        this.productId = productId;
        this.quantity = quantity;
    }

    public LocalDate getDate() { return date; }
    public Type getType() { return type; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }

    public String toCsvLine() {
        return date.format(F) + "," + type.name() + "," + productId + "," + quantity;
    }

    public static Movement fromCsv(String line) {
        String[] cols = line.split(",");
        java.time.LocalDate date = cols.length > 0 && !cols[0].isEmpty() ? LocalDate.parse(cols[0]) : LocalDate.now();
        Type type = cols.length > 1 && !cols[1].isEmpty() ? Type.valueOf(cols[1]) : Type.SALE;
        String pid = cols.length > 2 ? cols[2] : "";
        int qty = cols.length > 3 && !cols[3].isEmpty() ? Integer.parseInt(cols[3]) : 0;
        return new Movement(date, type, pid, qty);
    }

    @Override
    public String toString() {
        return date + " | " + type + " | " + productId + " | qty: " + quantity;
    }
}
