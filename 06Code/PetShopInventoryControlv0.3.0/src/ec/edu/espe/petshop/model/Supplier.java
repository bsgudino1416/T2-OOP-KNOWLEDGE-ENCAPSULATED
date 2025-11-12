
package ec.edu.espe.petshop.model;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Supplier {
    private String name;
    private String contact;

    public Supplier(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "Proveedor: " + name + " | Contacto: " + contact;
    }
}
