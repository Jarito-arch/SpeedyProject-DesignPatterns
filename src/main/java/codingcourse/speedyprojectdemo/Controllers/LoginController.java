package codingcourse.speedyprojectdemo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private void goToPrincipalMenu(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(
                getClass().getResource("/codingcourse/speedyprojectdemo/PrincipalMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void goToRegister(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(
                getClass().getResource("/codingcourse/speedyprojectdemo/Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
