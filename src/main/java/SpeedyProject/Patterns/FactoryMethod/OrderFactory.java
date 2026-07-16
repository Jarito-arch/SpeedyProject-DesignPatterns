package SpeedyProject.Patterns.FactoryMethod;

import SpeedyProject.Models.ItemShoppingCart;
import SpeedyProject.Models.Product;

/**
 * Factory Method (GoF) - centraliza la creación de ítems de carrito para que
 * ShoppingCart no tenga que conocer cómo se calcula un subtotal inicial.
 */
public class OrderFactory {
    public static ItemShoppingCart createCartItem(Product product, int quantity) {
        ItemShoppingCart item = new ItemShoppingCart();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setSubtotal(product.getPrice() * quantity);
        return item;
    }
}
