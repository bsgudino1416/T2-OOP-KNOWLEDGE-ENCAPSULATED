package ec.edu.espe.petshop.model;

/**
 * Clase que representa al usuario con rol de Gerente.
 */
public class Gerente extends Profile {

    public Gerente(String username, String password) {
        super(username, password, "Gerente");
    }

    // Permite al gerente establecer el precio del producto por unidad o por libra
    public void setPrice(Product product, double pricePerUnit) {
        product.setPricePerUnit(pricePerUnit);
    }
}

