package ec.edu.espe.petshop.model;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Report {

    public void generateInventoryReport(Inventory inv) {
        System.out.println("\n=== REPORTE DE INVENTARIO ===");
        for (Product p : inv.getProducts()) {
            System.out.println(p);
        }
    }

    public void generateSalesReport(Invoice invoice) {
        System.out.println("\n=== REPORTE DE VENTAS ===");
        invoice.showInvoice();
    }
}
