package ec.edu.espe.petshop.model;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class Category {
    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Categoría: " + name + " (" + description + ")";
    }
}
