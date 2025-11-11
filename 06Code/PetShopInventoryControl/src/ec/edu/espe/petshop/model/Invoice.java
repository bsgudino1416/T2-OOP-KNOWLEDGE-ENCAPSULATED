package ec.edu.espe.petshop.model;

import java.util.Date;
/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Invoice {
    private Order order;
    private double total;
    private Date date;

    public Invoice(Order order) {
        this.order = order;
        this.total = calculateTotal();
        this.date = new Date();
    }

    private double calculateTotal() {
        double total = 0;
        for (OrderDetail d : order.getDetails()) {
            total += d.getProduct().getPrice() * d.getQuantity();
        }
        return total;
    }

    public void showInvoice() {
        System.out.println("\n=== FACTURA ===");
        order.showOrderDetails();
        System.out.println("Total: $" + total);
        System.out.println("Fecha: " + date);
    }
    
        @Override
    public String toString() {
        return "Invoice [date=" + date + ", total=" + total + "]";
    }
    
}
