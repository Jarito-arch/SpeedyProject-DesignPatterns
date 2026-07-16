package SpeedyProject.Models;

import SpeedyProject.Patterns.FactoryMethod.OrderFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShoppingCart {
    private final ObservableList<ItemShoppingCart> items = FXCollections.observableArrayList();
    private double total;

    public void addProduct(Product product) {
        for (ItemShoppingCart item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                item.setSubtotal(item.getProduct().getPrice() * item.getQuantity());
                calculateTotal();
                return;
            }
        }
        items.add(OrderFactory.createCartItem(product, 1));
        calculateTotal();
    }

    public void increaseQuantity(ItemShoppingCart item) {
        item.setQuantity(item.getQuantity() + 1);
        item.setSubtotal(item.getProduct().getPrice() * item.getQuantity());
        calculateTotal();
    }

    public void decreaseQuantity(ItemShoppingCart item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            item.setSubtotal(item.getProduct().getPrice() * item.getQuantity());
            calculateTotal();
        } else {
            removeProduct(item);
        }
    }

    public void removeProduct(ItemShoppingCart item) {
        items.remove(item);
        calculateTotal();
    }

    public void calculateTotal() {
        this.total = items.stream().mapToDouble(ItemShoppingCart::getSubtotal).sum();
    }

    public void clean() {
        items.clear();
        total = 0.0;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalItemsCount() {
        return items.stream().mapToInt(ItemShoppingCart::getQuantity).sum();
    }

    public ObservableList<ItemShoppingCart> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}
