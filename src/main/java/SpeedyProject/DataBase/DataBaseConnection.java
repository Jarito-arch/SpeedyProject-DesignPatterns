package SpeedyProject.DataBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DataBaseConnection {

    private static final String URL ="jdbc:sqlserver://DESKTOP-VA3B7GI:1433;databaseName=DeliveryDB;encrypt=true;trustServerCertificate=true";
    private static final String user = "sa";
    private static final String password = "Jarisalday1004";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                URL,
                user,
                password
        );
    }
}
