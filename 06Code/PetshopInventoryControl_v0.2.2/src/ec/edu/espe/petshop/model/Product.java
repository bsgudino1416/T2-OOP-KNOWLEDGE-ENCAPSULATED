//package ec.edu.espe.petshop.model;
//
//public class Product {
//    private int id;
//    private String name;
//    private double price;
//    private int stock;
//    int getPrice;
//
//    public Product(int id, String name, double price, int stock) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.stock = stock;
//    }
//
//    @Override
//    public String toString() {
//        return "ID: " + id + " | " + name + " | $" + price + " | Stock: " + stock;
//    }
//}
package ec.edu.espe.petshop.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters añadidos (necesarios para otras clases)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setters opcionales
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " | $" + price + " | Stock: " + stock;
    }
}
