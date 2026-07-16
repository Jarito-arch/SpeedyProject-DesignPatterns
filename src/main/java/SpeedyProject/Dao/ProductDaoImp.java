package SpeedyProject.Dao;

import SpeedyProject.Models.Product;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static SpeedyProject.DataBase.DataBaseConnection.getConnection;

public class ProductDaoImp implements ProductDao {

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        try (
                Connection connection = getConnection();
                CallableStatement callableStatement = connection.prepareCall("{call sp_get_products_by_category(?)}")
        ) {
            callableStatement.setString(1, category);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setCommerceId(resultSet.getInt("commerce_id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImagePath(resultSet.getString("image_path"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
