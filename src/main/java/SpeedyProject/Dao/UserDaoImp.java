package SpeedyProject.Dao;

import SpeedyProject.DataBase.DataBaseConnection;
import SpeedyProject.Models.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static SpeedyProject.DataBase.DataBaseConnection.getConnection;

public class UserDaoImp implements UserDao{
    @Override
    public User login(String email, String password) {
        User user = null;
        try (
                Connection
                        connection = getConnection();
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
    public User findByEmail(String email) {
        try(Connection connection = getConnection();
            CallableStatement
                    callableStatement = connection.prepareCall("{CALL sp_findByEmail(?)}")) {

            callableStatement.setString(1,email);

            ResultSet resultSet = callableStatement.executeQuery();

            if(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("passwd"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public boolean register(User user) {
        try(
                Connection
                    connection = getConnection();
                CallableStatement
                    callableStatement = connection.prepareCall("{call sp_register(?,?,?)}")
        ){
                callableStatement.setString(1,user.getName());
                callableStatement.setString(2,user.getEmail());
                callableStatement.setString(3,user.getPassword());
                callableStatement.execute();

                return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
