package SpeedyProject.Dao;

import SpeedyProject.Models.ItemShoppingCart;
import javafx.beans.Observable;
import javafx.collections.ObservableList;

public interface OrderDao {
    boolean registerOrder(int idUser, double total, ObservableList<ItemShoppingCart> items);
}
