package SpeedyProject.Controllers;

import SpeedyProject.Patterns.Singlenton.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ComercesController {

    @FXML
    private Label lblUserName;

    @FXML
    public void initialize() {
        if (Session.getInstance().getCurrentUser() != null) {
            lblUserName.setText(Session.getInstance().getCurrentUser().getName());
        }
    }

    private void loadView(String fxmlPath, Node sourceNode) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void goToTechStore(ActionEvent event) throws IOException {
        loadView("/Views/TechStore.fxml", (Node) event.getSource());
    }

    @FXML
    private void goToDrugStore(ActionEvent event) throws IOException {
        loadView("/Views/DrugStore.fxml", (Node) event.getSource());
    }

    @FXML
    private void goToRestaurant(ActionEvent event) throws IOException {
        loadView("/Views/Restaurant.fxml", (Node) event.getSource());
    }

    @FXML
    private void goToPrincipalMenu(MouseEvent event) throws IOException {
        loadView("/Views/PrincipalMenu.fxml", (Node) event.getSource());
    }
}
