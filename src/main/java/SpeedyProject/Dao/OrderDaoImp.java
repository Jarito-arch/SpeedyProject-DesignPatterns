package SpeedyProject.Dao;

import SpeedyProject.Models.ItemShoppingCart;
import SpeedyProject.Models.Order;
import SpeedyProject.Models.OrderDetail;
import SpeedyProject.Models.Product;
import SpeedyProject.Patterns.State.OrderState;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static SpeedyProject.DataBase.DataBaseConnection.getConnection;

public class OrderDaoImp implements OrderDao {

    @Override
    public boolean registerOrder(int idUser, double total, ObservableList<ItemShoppingCart> items) {
        boolean isRegistered = false;

        try (
                Connection connection = getConnection();
                CallableStatement callableOrderStatement = connection.prepareCall("{call sp_create_order(?,?,?)}");
                CallableStatement callableDetailStatement = connection.prepareCall("{call sp_create_order_detail(?,?,?,?)}")
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

    @Override
    public List<Order> getOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();
        try (
                Connection connection = getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call sp_get_orders_by_user(?)}")
        ) {
            callableStatement.setInt(1, userId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                Timestamp ts = resultSet.getTimestamp("order_date");
                if (ts != null) {
                    order.setDate(ts.toLocalDateTime());
                }
                order.setTotal(resultSet.getDouble("total"));
                order.setState(OrderState.fromLabel(resultSet.getString("status")));
                order.setCommerceCategory(resultSet.getString("category"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        try (
                Connection connection = getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call sp_get_order_details(?)}")
        ) {
            callableStatement.setInt(1, orderId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImagePath(resultSet.getString("image_path"));

                OrderDetail detail = new OrderDetail();
                detail.setId(resultSet.getInt("id"));
                detail.setProduct(product);
                detail.setQuantity(resultSet.getInt("quantity"));
                detail.setSubtotal(resultSet.getDouble("subtotal"));
                details.add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        try (
                Connection connection = getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call sp_update_order_status(?,?)}")
        ) {
            callableStatement.setInt(1, orderId);
            callableStatement.setString(2, status);
            callableStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
