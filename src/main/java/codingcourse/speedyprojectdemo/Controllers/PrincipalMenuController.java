package codingcourse.speedyprojectdemo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class PrincipalMenuController {
    @FXML
    private void goToComerces(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(
                getClass().getResource("/codingcourse/speedyprojectdemo/Comerces.fxml"));
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void goToShoppingCart(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(
                getClass().getResource("/codingcourse/speedyprojectdemo/ShoppingCart.fxml"));
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void goToOrders(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(
                getClass().getResource("/codingcourse/speedyprojectdemo/Orders.fxml"));
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void goToTracking(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(
                getClass().getResource("/codingcourse/speedyprojectdemo/Tracking.fxml"));
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
