package ec.edu.espe.petshop.utils;

import ec.edu.espe.petshop.model.Product;
import ec.edu.espe.petshop.model.Movement;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CsvManager {
    private Path productsPath;
    private Path movementsPath;
    private static final String PRODUCTS_HEADER = "id,name,category,animal,size,brand,pricePerUnit,stock,dateAdded,totalSold";
    private static final String MOVEMENTS_HEADER = "date,type,productId,quantity";

    public CsvManager(String productsFile, String movementsFile) {
        productsPath = Paths.get(productsFile);
        movementsPath = Paths.get(movementsFile);
        ensureFiles();
    }

    private void ensureFiles() {
        try {
            if (!Files.exists(productsPath)) {
                Files.write(productsPath, Arrays.asList(PRODUCTS_HEADER), StandardOpenOption.CREATE);
            }
            if (!Files.exists(movementsPath)) {
                Files.write(movementsPath, Arrays.asList(MOVEMENTS_HEADER), StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            System.out.println("Error creando archivos CSV: " + e.getMessage());
        }
    }

    public List<Product> readProducts() {
        try {
            List<String> lines = Files.readAllLines(productsPath);
            if (lines.size() <= 1) return new ArrayList<>();
            return lines.stream().skip(1).map(Product::fromCsv).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error al leer products CSV: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveProducts(List<Product> products) {
        List<String> out = new ArrayList<>();
        out.add(PRODUCTS_HEADER);
        for (Product p : products) out.add(p.toCsvLine());
        try {
            Files.write(productsPath, out, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    public List<Movement> readMovements() {
        try {
            List<String> lines = Files.readAllLines(movementsPath);
            if (lines.size() <= 1) return new ArrayList<>();
            return lines.stream().skip(1).map(Movement::fromCsv).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error al leer movimientos CSV: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void appendMovement(Movement m) {
        try {
            Files.write(movementsPath, Arrays.asList(m.toCsvLine()), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error al guardar movimiento: " + e.getMessage());
        }
    }
}
