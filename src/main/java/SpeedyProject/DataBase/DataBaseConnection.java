package SpeedyProject.DataBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DataBaseConnection {

    private static final String URL ="jdbc:sqlserver://LAPTOP-N14SBG7V:1433;databaseName=DeliveryDB;encrypt=true;trustServerCertificate=true";
    private static final String user = "sa";
    private static final String password = "12345";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                URL,
                user,
                password
        );
    }
}
