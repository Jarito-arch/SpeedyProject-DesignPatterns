package SpeedyProject.Controllers;

import SpeedyProject.Patterns.Singlenton.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalMenuController {

    @FXML
    private Label lblWelcome;

    @FXML
    public void initialize() {
        String userName = Session.getInstance().getCurrentUser().getName();
        lblWelcome.setText("Hi, " + userName + "!");
    }

    private void navigateTo(String fxmlPath, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void goToComerces(ActionEvent event) throws IOException {
        navigateTo("/Views/Comerces.fxml", event);
    }

    @FXML
    private void goToShoppingCart(ActionEvent event) throws IOException {
        navigateTo("/Views/ShoppingCart.fxml", event);
    }

    @FXML
    private void goToOrders(ActionEvent event) throws IOException {
        navigateTo("/Views/Orders.fxml", event);
    }

    @FXML
    private void goToTracking(ActionEvent event) throws IOException {
        navigateTo("/Views/Tracking.fxml", event);
    }
}
