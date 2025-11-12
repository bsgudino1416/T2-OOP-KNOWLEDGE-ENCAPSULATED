package ec.edu.espe.petshop.model;
//
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Product {
    private String id;             // Ejemplo: CPP01
    private String name;
    private String category;       // FOOD, MEDICINE, SNACK, ACCESORIES
    private String animalType;     // perro pequeño, gato grande, etc.
    private String supplier;       // DogChown, Perro, etc.
    private double pricePerUnit;   // por libra o por unidad según category
    private int stock;
    private String dateAdded;      // ISO: yyyy-MM-dd
    private int totalSold;

    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    // Constructor completo (usado al cargar desde JSON)
    public Product(String id, String name, String category, String animalType, String supplier,
                   double pricePerUnit, int stock, String dateAdded, int totalSold) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.animalType = animalType;
        this.supplier = supplier;
        this.pricePerUnit = pricePerUnit;
        this.stock = stock;
        this.dateAdded = dateAdded;
        this.totalSold = totalSold;
    }

    // Constructor breve (nuevo producto) -> dateAdded = hoy, totalSold = 0
    public Product(String id, String name, String category, String animalType, String supplier,
                   double pricePerUnit, int stock) {
        this(id, name, category, animalType, supplier, pricePerUnit, stock, LocalDate.now().format(F), 0);
    }

    // getters y setters relevantes
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getAnimalType() { return animalType; }
    public String getSupplier() { return supplier; }
    public double getPricePerUnit() { return pricePerUnit; }
    public int getStock() { return stock; }
    public String getDateAdded() { return dateAdded; }
    public int getTotalSold() { return totalSold; }

    public void setPricePerUnit(double pricePerUnit) { this.pricePerUnit = pricePerUnit; }
    public void increaseStock(int qty) { this.stock += qty; }
    public boolean decreaseStock(int qty) {
        if (qty <= stock) {
            stock -= qty;
            totalSold += qty;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Product ID: " + id +
               " | Nombre: " + name +
               " | Category: " + category +
               " | Type: " + animalType +
               " | Supplier: " + supplier +
               " | $" + pricePerUnit +
               " | Stock: " + stock +
               " | Date: " + dateAdded +
               " | Sold: " + totalSold;
    }
}

