package ec.edu.espe.petshop.model;

public class OrderDetail {
    private Product product;
    private int quantity;

    public OrderDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double calculateSubtotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity + " = $" + calculateSubtotal();
    }
}
