package SpeedyProject.Patterns.Facade;

import SpeedyProject.Dao.OrderDao;
import SpeedyProject.Dao.OrderDaoImp;
import SpeedyProject.Models.ItemShoppingCart;
import SpeedyProject.Models.Product;
import SpeedyProject.Patterns.FactoryMethod.OrderFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderFacade {
    private static OrderFacade instance;

    private ObservableList<ItemShoppingCart> cartItems;
    private double totalOrderAmount;
    private OrderDao orderDao;

    private OrderFacade() {
        this.cartItems = FXCollections.observableArrayList();
        this.totalOrderAmount = 0.0;
        this.orderDao = new OrderDaoImp();
    }

    public static OrderFacade getInstance() {
        if (instance == null) {
            instance = new OrderFacade();
        }
        return instance;
    }

    public void addProductToCart(Product product) {
        for (ItemShoppingCart item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                item.setSubtotal(item.getProduct().getPrice() * item.getQuantity());
                calculateTotal();
                return;
            }
        }
        ItemShoppingCart newItem = OrderFactory.createCartItem(product, 1);
        cartItems.add(newItem);
        calculateTotal();
    }
    private void calculateTotal() {
        double sum = 0;
        for (ItemShoppingCart item : cartItems) {
            sum += item.getSubtotal();
        }
        this.totalOrderAmount = sum;
    }
    public boolean submitOrder(int currentUserId) {
        if (cartItems.isEmpty()) {
            return false;
        }

        boolean isSuccess = orderDao.registerOrder(currentUserId, totalOrderAmount, cartItems);

        if (isSuccess) {
            cartItems.clear();
            totalOrderAmount = 0.0;
        }

        return isSuccess;
    }
    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public ObservableList<ItemShoppingCart> getCartItems() {
        return cartItems;
    }
}