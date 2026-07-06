package SpeedyProject.Dao;

import SpeedyProject.Models.User;
//Use of DIP - El controlador depende de la abstracción UserDAO y no de la implementación concreta UserDAOImpl, permitiendo
// cambiar la fuente de datos sin modificar el controlador.
public interface UserDao {
    User login(String email, String password);
    boolean register(User user);
    User findByEmail(String email);
}
