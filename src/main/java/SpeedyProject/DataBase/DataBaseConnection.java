package SpeedyProject.DataBase;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {

    private static final String CONFIG_FILE = "/db.properties";
    private static Properties properties;

    private static synchronized Properties loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream input = DataBaseConnection.class.getResourceAsStream(CONFIG_FILE)) {
                if (input == null) {
                    throw new IllegalStateException(
                            "No se encontro db.properties en resources. " +
                                    "Copia db.properties.example y agrega tus credenciales."
                    );
                }
                properties.load(input);
            } catch (IOException e) {
                throw new IllegalStateException("Error reading file:  db.properties", e);
            }
        }
        return properties;
    }

    public static Connection getConnection() throws SQLException {
        Properties props = loadProperties();
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}