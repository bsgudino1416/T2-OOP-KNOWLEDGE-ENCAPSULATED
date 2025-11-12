package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.*;
import ec.edu.espe.petshop.utils.JsonManager;

import java.util.*;
import java.util.stream.Collectors;

public class ProductController {
    private final List<Product> products;
    private final List<Movement> movements;
    private final JsonManager json;

    private final int LOW_STOCK_THRESHOLD = 5;

    private static final Set<String> VALID_CATEGORIES = Set.of("FOOD","MEDICINE","SNACK","ACCESORIES");
    private static final Set<String> VALID_ANIMALS = Set.of("perro","gato","caballo","conejo","cerdo","gallina","vaca");
    private static final Set<String> VALID_SIZES = Set.of("pequeño","grande");

    public ProductController(JsonManager json) {
        this.json = json;
        this.products = json.readProducts();
        this.movements = json.readMovements();
    }

    public List<Product> allProducts() { return products; }
    public List<Movement> allMovements() { return movements; }

    public Product findById(String id) {
        for (Product p : products) if (p.getId().equalsIgnoreCase(id)) return p;
        return null;
    }

    public void addProductInteractive(Scanner sc) throws InvalidProductDataException {
        System.out.println("\n=== AGREGAR PRODUCTO ===");
        System.out.print("Nombre: ");
        String name = sc.nextLine().trim();
        System.out.print("Categoría (FOOD, MEDICINE, SNACK, ACCESORIES): ");
        String category = sc.nextLine().trim().toUpperCase();
        System.out.print("Animal (perro,gato,caballo,conejo,cerdo,gallina,vaca): ");
        String animal = sc.nextLine().trim().toLowerCase();
        System.out.print("Tamaño (pequeño/grande): ");
        String size = sc.nextLine().trim().toLowerCase();
        System.out.print("Marca/Proveedor: ");
        String supplier = sc.nextLine().trim();

        if (name.isEmpty() || !VALID_CATEGORIES.contains(category) || !VALID_ANIMALS.contains(animal) ||
            !VALID_SIZES.contains(size) || supplier.isEmpty()) {
            throw new InvalidProductDataException("Datos incompletos o inválidos.");
        }

        double price;
        if ("FOOD".equalsIgnoreCase(category)) {
            System.out.print("Precio por libra (gerente define): ");
            price = readDouble(sc);
        } else {
            System.out.print("Precio por unidad: ");
            price = readDouble(sc);
        }

        System.out.print("Stock inicial: ");
        int stock = readInt(sc);

        String prefix = IDGenerator.generatePrefix(category, animal, size);
        String id = IDGenerator.nextId(products, prefix);
        String animalType = animal + " " + size;
        Product p = new Product(id, name, category, animalType, supplier, price, stock);
        products.add(p);
        json.saveProducts(products);
        System.out.println("Producto agregado: " + p.getId());
        checkAlerts();
    }

    private double readDouble(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            try { return Double.parseDouble(line.trim()); }
            catch (NumberFormatException e) { System.out.print("Valor inválido. Inténtelo de nuevo: "); }
        }
    }

    private int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            try { return Integer.parseInt(line.trim()); }
            catch (NumberFormatException e) { System.out.print("Valor inválido. Inténtelo de nuevo: "); }
        }
    }

    public boolean registerSale(String productId, int qty) {
        Product p = findById(productId);
        if (p == null) return false;
        if (!p.decreaseStock(qty)) return false;
        Movement m = new Movement(Movement.Type.SALE, productId, qty);
        movements.add(m);
        json.appendMovement(m);
        json.saveProducts(products);
        checkAlerts();
        return true;
    }

    public boolean registerPurchase(String productId, int qty) {
        Product p = findById(productId);
        if (p == null) return false;
        p.increaseStock(qty);
        Movement m = new Movement(Movement.Type.PURCHASE, productId, qty);
        movements.add(m);
        json.appendMovement(m);
        json.saveProducts(products);
        return true;
    }

    public List<Product> search(String q) {
        String s = q.toLowerCase();
        return products.stream().filter(p ->
            p.getId().toLowerCase().contains(s) ||
            p.getName().toLowerCase().contains(s) ||
            p.getCategory().toLowerCase().contains(s) ||
            p.getSupplier().toLowerCase().contains(s)
        ).collect(Collectors.toList());
    }

    public List<Product> productsLowStock() {
        return products.stream().filter(p -> p.getStock() <= LOW_STOCK_THRESHOLD).collect(Collectors.toList());
    }

    public void checkAlerts() {
        List<Product> low = productsLowStock();
        if (!low.isEmpty()) {
            System.out.println("ALERTA: " + low.size() + " productos con bajo stock.");
            for (Product p : low) System.out.println(" - " + p.getId() + " : " + p.getStock());
        }
    }

    // Reportes básicos
    public void reportTopSold(int topN) {
        List<Product> copy = new ArrayList<>(products);
        copy.sort(Comparator.comparingInt(Product::getTotalSold).reversed());
        System.out.println("\n--- TOP " + topN + " VENDIDOS ---");
        copy.stream().limit(topN).forEach(System.out::println);
    }

    public void reportMovementsLastDays(int days) {
        System.out.println("\n--- MOVIMIENTOS últimos " + days + " días ---");
        // movimientos con fecha igual/ posterior a cutoff
        // movimientos guardados con fecha ISO yyyy-MM-dd
        java.time.LocalDate cutoff = java.time.LocalDate.now().minusDays(days);
        boolean any = false;
        for (Movement m : movements) {
            java.time.LocalDate d = java.time.LocalDate.parse(m.getDate());
            if (!d.isBefore(cutoff)) {
                System.out.println(m);
                any = true;
            }
        }
        if (!any) System.out.println("No hay movimientos recientes.");
    }

    public void showInventorySummary() {
        System.out.println("\n--- PANEL DE CONTROL ---");
        System.out.println("Total productos: " + products.size());
        int totalUnits = products.stream().mapToInt(Product::getStock).sum();
        System.out.println("Unidades totales en stock: " + totalUnits);
        System.out.println("Productos con bajo stock:");
        productsLowStock().forEach(System.out::println);
        reportTopSold(5);
    }
}

