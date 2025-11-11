package ec.edu.espe.petshop.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private Supplier supplier;
    private AnimalCategory category;

    public Product(int id, String name, double price, int stock, Supplier supplier, AnimalCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.supplier = supplier;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void updateStock(int quantity) {
        this.stock += quantity;
    }

    @Override
    public String toString() {
        return id + " - " + name + " | $" + price + " | Stock: " + stock + " | " + category.getType();
    }
}
