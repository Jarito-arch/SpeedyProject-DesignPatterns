package SpeedyProject.Controllers;

import SpeedyProject.Models.Product;
import SpeedyProject.Patterns.Facade.OrderFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class TechStoreControler {
    private OrderFacade facade = OrderFacade.getInstance();

    @FXML
    private void goToPrincipalMenu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("/Views/PrincipalMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onAddPCGamer(){
        Product pcGamer = new Product();
        pcGamer.setId(1);
        pcGamer.setName("Computadora Gamer");
        pcGamer.setPrice(11500.00);
        facade.addProductToCart(pcGamer);
        System.out.println("PC Gamer añadida");

    }



}
