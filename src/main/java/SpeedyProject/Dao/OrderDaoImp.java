package SpeedyProject.Dao;

import SpeedyProject.DataBase.DataBaseConnection;
import SpeedyProject.Models.ItemShoppingCart;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class OrderDaoImp implements OrderDao{
    @Override
    public boolean registerOrder(int idUser, double total, ObservableList<ItemShoppingCart> items) {
        boolean isRegistered = false;

        try (
                Connection
                        connection = DataBaseConnection.getConnection();
                CallableStatement
                        callableOrderStatement = connection.prepareCall("{call sp_create_order(?,?,?)}");
                CallableStatement
                        callableDetailStatement = connection.prepareCall("{call sp_create_order_detail(?,?,?,?)}")
        ) {
            connection.setAutoCommit(false);
            try {
                callableOrderStatement.setInt(1, idUser);
                callableOrderStatement.setDouble(2, total);
                callableOrderStatement.registerOutParameter(3, Types.INTEGER);
                callableOrderStatement.execute();

                int generatedOrderId = callableOrderStatement.getInt(3);

                for (ItemShoppingCart item : items) {
                    callableDetailStatement.setInt(1, generatedOrderId);
                    callableDetailStatement.setInt(2, item.getProduct().getId());
                    callableDetailStatement.setInt(3, item.getQuantity());
                    callableDetailStatement.setDouble(4, item.getSubtotal());
                    callableDetailStatement.addBatch();
                }
                callableDetailStatement.executeBatch();
                connection.commit();
                isRegistered = true;
            } catch (Exception ex) {
                connection.rollback();
                throw ex;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRegistered;
    }
}
