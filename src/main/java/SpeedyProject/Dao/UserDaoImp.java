package SpeedyProject.Dao;

import SpeedyProject.DataBase.DataBaseConnection;
import SpeedyProject.Models.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class UserDaoImp implements UserDao{
    @Override
    public User login(String email, String password) {
        User user = null;
        try (
                Connection
                        connection = DataBaseConnection.getConnection();
                CallableStatement
                        callableStatement = connection.prepareCall("{call sp_login(?,?)}")
        ) {
            callableStatement.setString(1, email);
            callableStatement.setString(2, password);

            ResultSet resultSet = callableStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("passwd"));

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean register(User user) {
        return false;
    }
}
