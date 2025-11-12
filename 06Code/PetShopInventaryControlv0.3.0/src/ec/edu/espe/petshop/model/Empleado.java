package ec.edu.espe.petshop.model;

/**
 * Clase que representa al usuario con rol de Empleado.
 */
public class Empleado extends Profile {

    public Empleado(String username, String password) {
        super(username, password, "Empleado");
    }
}

