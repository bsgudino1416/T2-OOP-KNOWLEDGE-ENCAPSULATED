package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.Movement;
import ec.edu.espe.petshop.model.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportManager {
    private final ProductManager pm;

    public ReportManager(ProductManager pm) { this.pm = pm; }

    public void showTopSelling(int topN) {
        List<Product> list = new ArrayList<>(pm.getProducts());
        list.sort(Comparator.comparingInt(Product::getTotalSold).reversed());
        System.out.println("\n--- TOP " + topN + " PRODUCTOS MÁS VENDIDOS ---");
        list.stream().limit(topN).forEach(System.out::println);
    }

    public void showLowStock() {
        System.out.println("\n--- PRODUCTOS BAJO STOCK ---");
        List<Product> low = pm.getLowStockProducts();
        if (low.isEmpty()) System.out.println("No hay productos con bajo stock.");
        else low.forEach(System.out::println);
    }

    public void showMovementsLastDays(int days) {
        System.out.println("\n--- MOVIMIENTOS ÚLTIMOS " + days + " DÍAS ---");
        LocalDate cutoff = LocalDate.now().minusDays(days);
        List<Movement> movs = pm.getMovements().stream()
                .filter(m -> m.getDate().isAfter(cutoff) || m.getDate().isEqual(cutoff))
                .collect(Collectors.toList());
        if (movs.isEmpty()) System.out.println("No hay movimientos recientes.");
        else movs.forEach(System.out::println);
    }

    public void showInventorySummary() {
        List<Product> prod = pm.getProducts();
        int totalProducts = prod.size();
        int totalStock = prod.stream().mapToInt(Product::getStock).sum();
        System.out.println("\n--- PANEL DE CONTROL ---");
        System.out.println("Total productos: " + totalProducts);
        System.out.println("Stock total unidades: " + totalStock);
        showLowStock();
        showTopSelling(5);
    }
}
