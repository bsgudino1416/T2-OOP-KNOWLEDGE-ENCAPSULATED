package ec.edu.espe.petshop.model;

import java.util.*;
/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Order {
    private List<OrderDetail> details = new ArrayList<>();
    private Date date; // Fecha del pedido

    public Order() {
        this.date = new Date(); // Asigna fecha automática al crear pedido
    }

    public void addProduct(Product product, int quantity) {
        details.add(new OrderDetail(product, quantity));
    }

    public void showOrderDetails() {
        System.out.println("\n--- ORDER DETAILS ---");
        for (OrderDetail d : details) {
            System.out.println(d);
        }
    }

    // 🔹 Este método es necesario para Invoice
    public List<OrderDetail> getDetails() {
        return details;
    }

    // 🔹 Por si la factura quiere mostrar la fecha del pedido
    public Date getDate() {
        return date;
    }
}
