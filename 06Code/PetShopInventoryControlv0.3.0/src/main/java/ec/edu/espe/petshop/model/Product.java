package ec.edu.espe.petshop.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Product {
    private String id;
    private String name;
    private String category;   // FOOD, MEDICINE, SNACK, ACCESORIES
    private String animal;     // perro, gato, caballo, conejo, cerdo, gallina, vaca
    private String size;       // pequeño, grande
    private String brand;      // proveedor/marca
    private double pricePerUnit; // price per libra for FOOD, per unit for others
    private int stock;
    private LocalDate dateAdded;
    private int totalSold;

    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public Product(String id, String name, String category, String animal, String size, String brand,
                   double pricePerUnit, int stock, LocalDate dateAdded, int totalSold) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.animal = animal;
        this.size = size;
        this.brand = brand;
        this.pricePerUnit = pricePerUnit;
        this.stock = stock;
        this.dateAdded = dateAdded;
        this.totalSold = totalSold;
    }

    public Product(String id, String name, String category, String animal, String size, String brand,
                   double pricePerUnit, int stock) {
        this(id, name, category, animal, size, brand, pricePerUnit, stock, LocalDate.now(), 0);
    }

    // getters / setters mínimos
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getAnimal() { return animal; }
    public String getSize() { return size; }
    public String getBrand() { return brand; }
    public double getPricePerUnit() { return pricePerUnit; }
    public int getStock() { return stock; }
    public LocalDate getDateAdded() { return dateAdded; }
    public int getTotalSold() { return totalSold; }

    public void setPricePerUnit(double pricePerUnit) { this.pricePerUnit = pricePerUnit; }
    public void increaseStock(int q) { stock += q; }
    public boolean decreaseStock(int q) {
        if (q <= stock) {
            stock -= q;
            totalSold += q;
            return true;
        }
        return false;
    }

    public String toCsvLine() {
        // id,name,category,animal,size,brand,pricePerUnit,stock,dateAdded,totalSold
        return String.join(",",
                safe(id),
                safe(name),
                safe(category),
                safe(animal),
                safe(size),
                safe(brand),
                String.valueOf(pricePerUnit),
                String.valueOf(stock),
                dateAdded.format(F),
                String.valueOf(totalSold)
        );
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace(",", " "); // simple escaping
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + category + " | " + animal + " " + size +
                " | " + brand + " | $" + pricePerUnit + " | Stock: " + stock +
                " | Fecha: " + dateAdded + " | Vendidos: " + totalSold;
    }

    public static Product fromCsv(String line) {
        String[] cols = line.split(",");
        String id = cols.length > 0 ? cols[0].trim() : "";
        String name = cols.length > 1 ? cols[1].trim() : "";
        String category = cols.length > 2 ? cols[2].trim() : "";
        String animal = cols.length > 3 ? cols[3].trim() : "";
        String size = cols.length > 4 ? cols[4].trim() : "";
        String brand = cols.length > 5 ? cols[5].trim() : "";
        double price = cols.length > 6 && !cols[6].isEmpty() ? Double.parseDouble(cols[6].trim()) : 0.0;
        int stock = cols.length > 7 && !cols[7].isEmpty() ? Integer.parseInt(cols[7].trim()) : 0;
        java.time.LocalDate date = cols.length > 8 && !cols[8].isEmpty() ? LocalDate.parse(cols[8].trim()) : LocalDate.now();
        int sold = cols.length > 9 && !cols[9].isEmpty() ? Integer.parseInt(cols[9].trim()) : 0;
        return new Product(id, name, category, animal, size, brand, price, stock, date, sold);
    }
}
