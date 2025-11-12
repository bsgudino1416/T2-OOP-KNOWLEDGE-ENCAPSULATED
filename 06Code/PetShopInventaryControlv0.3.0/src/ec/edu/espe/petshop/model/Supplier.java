package ec.edu.espe.petshop.model;

/**
 * Representa un proveedor de productos del PetShop.
 */
public class Supplier {
    private String name;
    private String contactInfo;

    public Supplier(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    @Override
    public String toString() {
        return name + " (" + contactInfo + ")";
    }
}
