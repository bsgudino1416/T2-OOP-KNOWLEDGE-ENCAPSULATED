package ec.edu.espe.petshop.utils;

import ec.edu.espe.petshop.model.Product;
import java.util.List;
import java.util.stream.Collectors;

public class AlertSystem {
    private final int LOW_STOCK_THRESHOLD = 5;

    public java.util.List<Product> checkLowStock(java.util.List<Product> products) {
        return products.stream().filter(p -> p.getStock() <= LOW_STOCK_THRESHOLD).collect(Collectors.toList());
    }
}
