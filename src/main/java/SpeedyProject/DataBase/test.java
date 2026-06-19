package SpeedyProject.DataBase;

import java.sql.Connection;

public class test {
    public static void main(String[] args) {
        try(Connection conn = DataBaseConnection.getConnection()){
            System.out.println("Succesful conexion");
        } catch (Exception e) {
            System.out.println("error :(");
            e.printStackTrace();
        }
    }
}
