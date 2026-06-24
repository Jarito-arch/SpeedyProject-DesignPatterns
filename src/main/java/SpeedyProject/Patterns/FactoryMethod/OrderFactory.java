package SpeedyProject.Patterns.FactoryMethod;

import SpeedyProject.Models.ItemShoppingCart;
import SpeedyProject.Models.Product;

public class OrderFactory {
    public static ItemShoppingCart createCartItem(Product product, int quantity) {

        ItemShoppingCart item = new ItemShoppingCart();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setSubtotal(product.getPrice() * quantity);
        return item;
    }
}
