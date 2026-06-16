package codingcourse.speedyprojectdemo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ShoppingCartController {
    @FXML
    private void goToPrincipalMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("/codingcourse/speedyprojectdemo/PrincipalMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
