package SpeedyProject.Models;
public class OrderDetail {
    private int id;
    private Product product;
    private int quantity;
    private double subtotal;

    public OrderDetail() {
    }

    public OrderDetail(Product product, int quantity, double subtotal) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /** GRASP - Information Expert: el propio detalle sabe calcular su subtotal. */
    public void calculateSubTotal() {
        if (product != null) {
            this.subtotal = product.getPrice() * quantity;
        }
    }
}
