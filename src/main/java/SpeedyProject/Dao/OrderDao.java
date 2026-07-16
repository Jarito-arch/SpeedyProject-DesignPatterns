package SpeedyProject.Dao;

import SpeedyProject.Models.ItemShoppingCart;
import SpeedyProject.Models.Order;
import SpeedyProject.Models.OrderDetail;
import javafx.collections.ObservableList;

import java.util.List;

public interface OrderDao {
    boolean registerOrder(int idUser, double total, ObservableList<ItemShoppingCart> items);

    List<Order> getOrdersByUser(int userId);

    List<OrderDetail> getOrderDetails(int orderId);

    void updateOrderStatus(int orderId, String status);
}
