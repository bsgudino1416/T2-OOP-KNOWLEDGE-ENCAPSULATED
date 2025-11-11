package ec.edu.espe.petshop.model;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Product {
    private String name;
    private double price;
    private Animal animal;

    public Product(String name, double price, Animal animal) {
        this.name = name;
        this.price = price;
        this.animal = animal;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " - $" + price + " (" + animal.getType() + ")";
    }
}
