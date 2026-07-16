package SpeedyProject.Dao;

import SpeedyProject.Models.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProductsByCategory(String category);
}
