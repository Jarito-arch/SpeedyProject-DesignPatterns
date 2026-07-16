package SpeedyProject.Patterns.Facade;

import SpeedyProject.Dao.OrderDao;
import SpeedyProject.Dao.OrderDaoImp;
import SpeedyProject.Models.*;
import SpeedyProject.Patterns.Observer.Observer;
import SpeedyProject.Patterns.Observer.OrderSubject;
import SpeedyProject.Patterns.State.OrderState;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Facade (GoF).
 * Unifica de cara a los controladores tres subsistemas que, de otro modo,
 * tendrían que orquestarse manualmente en cada pantalla:
 *   1) ShoppingCart (estado del carrito en memoria).
 *   2) OrderDao (persistencia de pedidos en SQL Server).
 *   3) OrderSubject x2 (notificación a las vistas cuando cambia el carrito
 *      o cuando cambia el estado de un pedido).
 *
 * Es Singleton porque debe existir un único carrito de compras vivo por
 * sesión de aplicación (un solo usuario logueado a la vez, ver Session).
 */
public class OrderFacade {
    private static OrderFacade instance;

    private final ShoppingCart shoppingCart;
    private final OrderDao orderDao;
    private final OrderSubject cartSubject;
    private final OrderSubject orderSubject;

    private OrderFacade() {
        this.shoppingCart = new ShoppingCart();
        this.orderDao = new OrderDaoImp();
        this.cartSubject = new OrderSubject();
        this.orderSubject = new OrderSubject();
    }

    public static synchronized OrderFacade getInstance() {
        if (instance == null) {
            instance = new OrderFacade();
        }
        return instance;
    }

    // ---- Carrito ----

    public void addProductToCart(Product product) {
        shoppingCart.addProduct(product);
        cartSubject.notificationObservers();
    }

    public void increaseQuantity(ItemShoppingCart item) {
        shoppingCart.increaseQuantity(item);
        cartSubject.notificationObservers();
    }

    public void decreaseQuantity(ItemShoppingCart item) {
        shoppingCart.decreaseQuantity(item);
        cartSubject.notificationObservers();
    }

    public void removeFromCart(ItemShoppingCart item) {
        shoppingCart.removeProduct(item);
        cartSubject.notificationObservers();
    }

    public boolean isProductInCart(int productId) {
        return shoppingCart.getItems().stream()
                .anyMatch(i -> i.getProduct().getId() == productId);
    }

    public ObservableList<ItemShoppingCart> getCartItems() {
        return shoppingCart.getItems();
    }

    public double getTotalOrderAmount() {
        return shoppingCart.getTotal();
    }

    public int getCartItemsCount() {
        return shoppingCart.getTotalItemsCount();
    }

    // ---- Pedidos ----

    public boolean submitOrder(int currentUserId) {
        if (shoppingCart.isEmpty()) {
            return false;
        }
        boolean isSuccess = orderDao.registerOrder(currentUserId, shoppingCart.getTotal(), shoppingCart.getItems());
        if (isSuccess) {
            shoppingCart.clean();
            cartSubject.notificationObservers();
            orderSubject.notificationObservers();
        }
        return isSuccess;
    }

    public List<Order> getOrderHistory(int userId) {
        return orderDao.getOrdersByUser(userId);
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderDao.getOrderDetails(orderId);
    }

    /** Avanza el estado de un pedido usando el patrón State y persiste el cambio. */
    public void advanceOrder(Order order) {
        OrderState next = order.getState().changingState();
        orderDao.updateOrderStatus(order.getId(), next.getState());
        order.setState(next);
        orderSubject.notificationObservers();
    }

    // ---- Observadores ----

    public void addCartObserver(Observer observer) {
        cartSubject.addObserver(observer);
    }

    public void removeCartObserver(Observer observer) {
        cartSubject.removeObserver(observer);
    }

    public void addOrderObserver(Observer observer) {
        orderSubject.addObserver(observer);
    }

    public void removeOrderObserver(Observer observer) {
        orderSubject.removeObserver(observer);
    }
}
