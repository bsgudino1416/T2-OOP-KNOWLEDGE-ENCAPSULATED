package ec.edu.espe.petshop.model;

import java.util.*;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Inventory {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void showProducts() {
        System.out.println("\n--- LISTA DE INVENTARIO ---");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    public Product findProductByName(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }
}
