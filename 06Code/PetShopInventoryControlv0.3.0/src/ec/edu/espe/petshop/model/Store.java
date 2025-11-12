package ec.edu.espe.petshop.model;

import java.util.Scanner;


/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Store {
    private Inventory inventory;

    public Store(String name) {
        this.inventory = new Inventory();
        // opcional: cargar productos iniciales
    }

    // Otros métodos: addProduct, showInventory, etc.

    /**
     * Crea un pedido a partir de la entrada del usuario.
     * Devuelve la Order creada o null si no se pudo crear.
     */
    public Order createOrder(Scanner sc) {
        Order order = new Order();

        if (inventory.getProducts().isEmpty()) {
            System.out.println("El inventario está vacío. No se pueden crear pedidos.");
            return null;
        }

        inventory.showProducts();
        System.out.print("Ingrese el nombre del producto a ordenar: ");
        String name = sc.nextLine();
        Product product = inventory.findProductByName(name);

        if (product != null) {
            System.out.print("Ingrese la cantidad: ");
            int qty = sc.nextInt();
            sc.nextLine();
            order.addProduct(product, qty);
            System.out.println("Pedido creado correctamente.");
            order.showOrderDetails();
            return order;
        } else {
            System.out.println("❌ Producto no encontrado!");
            return null;
        }
    }
}
