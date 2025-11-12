package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.*;
import ec.edu.espe.petshop.utils.CsvManager;
import ec.edu.espe.petshop.utils.AlertSystem;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProductManager {
    private final List<Product> products;
    private final List<Movement> movements;
    private final CsvManager csv;
    private final AlertSystem alert;

    private static final Set<String> VALID_CATEGORIES = Set.of("FOOD","MEDICINE","SNACK","ACCESORIES");
    private static final Set<String> VALID_ANIMALS = Set.of("perro","gato","caballo","conejo","cerdo","gallina","vaca");
    private static final Set<String> VALID_SIZES = Set.of("pequeño","grande");

    public ProductManager(CsvManager csv) {
        this.csv = csv;
        this.products = csv.readProducts();
        this.movements = csv.readMovements();
        this.alert = new AlertSystem();
    }

    public List<Product> getProducts() { return products; }
    public List<Movement> getMovements() { return movements; }

    public void saveAll() { csv.saveProducts(products); }

    public void addProductInteractive(Scanner sc, Role role) throws InvalidProductDataException {
        if (role != Role.MANAGER) throw new IllegalArgumentException("Operación no permitida para su rol.");
        System.out.println("\n--- AGREGAR PRODUCTO ---");
        System.out.print("Nombre: ");
        String name = sc.nextLine().trim();
        System.out.print("Categoría (FOOD, MEDICINE, SNACK, ACCESORIES): ");
        String category = sc.nextLine().trim().toUpperCase();
        System.out.print("Animal (perro,gato,caballo,conejo,cerdo,gallina,vaca): ");
        String animal = sc.nextLine().trim().toLowerCase();
        System.out.print("Tamaño (pequeño/grande): ");
        String size = sc.nextLine().trim().toLowerCase();
        System.out.print("Marca/Proveedor: ");
        String brand = sc.nextLine().trim();

        if (name.isEmpty() || !VALID_CATEGORIES.contains(category) || !VALID_ANIMALS.contains(animal) || !VALID_SIZES.contains(size) || brand.isEmpty()) {
            throw new InvalidProductDataException("Datos inválidos o incompletos.");
        }

        double price;
        if ("FOOD".equalsIgnoreCase(category)) {
            System.out.print("Precio por libra (gerente define): ");
            price = readDouble(sc);
        } else {
            System.out.print("Precio por unidad: ");
            price = readDouble(sc);
        }

        System.out.print("Stock inicial (unidades): ");
        int stock = readInt(sc);

        String prefix = IDGenerator.generatePrefix(category, animal, size);
        String id = IDGenerator.nextId(products, prefix);
        Product p = new Product(id, name, category, animal, size, brand, price, stock, LocalDate.now(), 0);
        products.add(p);
        csv.saveProducts(products);

        System.out.println("Producto agregado con ID: " + id);
        List<Product> low = alert.checkLowStock(products);
        if (!low.isEmpty()) System.out.println("Alerta: hay " + low.size() + " productos con bajo stock.");
    }

    private double readDouble(Scanner sc) {
        while (true) {
            String l = sc.nextLine();
            try { return Double.parseDouble(l.trim()); }
            catch (NumberFormatException e) { System.out.print("Valor inválido. Intente de nuevo: "); }
        }
    }

    private int readInt(Scanner sc) {
        while (true) {
            String l = sc.nextLine();
            try { return Integer.parseInt(l.trim()); }
            catch (NumberFormatException e) { System.out.print("Valor inválido. Intente de nuevo: "); }
        }
    }

    public void showInventory() {
        System.out.println("\n--- INVENTARIO ---");
        if (products.isEmpty()) { System.out.println("No hay productos."); return; }
        products.forEach(System.out::println);
    }

    public Product findById(String id) {
        for (Product p : products) if (p.getId().equalsIgnoreCase(id)) return p;
        return null;
    }

    public List<Product> searchByText(String q) {
        String s = q.toLowerCase();
        return products.stream().filter(p ->
                p.getId().toLowerCase().contains(s) ||
                p.getName().toLowerCase().contains(s) ||
                p.getCategory().toLowerCase().contains(s) ||
                p.getBrand().toLowerCase().contains(s)
        ).collect(Collectors.toList());
    }

    public List<Product> searchByCategory(String cat) {
        String s = cat.toLowerCase();
        return products.stream().filter(p -> p.getCategory().toLowerCase().equals(s)).collect(Collectors.toList());
    }

    public List<Product> searchBySupplier(String brand) {
        String s = brand.toLowerCase();
        return products.stream().filter(p -> p.getBrand().toLowerCase().contains(s)).collect(Collectors.toList());
    }

    public boolean registerSale(String productId, int qty) {
        Product p = findById(productId);
        if (p == null) return false;
        if (!p.decreaseStock(qty)) return false;
        Movement m = new Movement(java.time.LocalDate.now(), Movement.Type.SALE, productId, qty);
        movements.add(m);
        csv.appendMovement(m);
        csv.saveProducts(products);
        if (!alert.checkLowStock(products).isEmpty()) System.out.println("Alerta: stock bajo detectado tras venta.");
        return true;
    }

    public boolean registerPurchase(String productId, int qty) {
        Product p = findById(productId);
        if (p == null) return false;
        p.increaseStock(qty);
        Movement m = new Movement(java.time.LocalDate.now(), Movement.Type.PURCHASE, productId, qty);
        movements.add(m);
        csv.appendMovement(m);
        csv.saveProducts(products);
        return true;
    }

    public List<Product> getLowStockProducts() { return alert.checkLowStock(products); }
}
