package SpeedyProject.Controllers;

import SpeedyProject.Models.ItemShoppingCart;
import SpeedyProject.Patterns.Facade.OrderFacade;
import SpeedyProject.Patterns.Observer.Observer;
import SpeedyProject.Patterns.Observer.SimpleObserver;
import SpeedyProject.Patterns.Singlenton.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class ShoppingCartController {

    @FXML
    private Label lblUserName;
    @FXML
    private VBox cartItemsContainer;
    @FXML
    private Label lblEmptyCart;
    @FXML
    private Label lblSubtotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnConfirm;

    private static final double SHIPPING_COST = 5.00;

    private final OrderFacade facade = OrderFacade.getInstance();
    private Observer cartObserver;

    @FXML
    public void initialize() {
        if (Session.getInstance().getCurrentUser() != null) {
            lblUserName.setText(Session.getInstance().getCurrentUser().getName());
        }
        cartObserver = new SimpleObserver(this::renderCart);
        facade.addCartObserver(cartObserver);
        renderCart();
    }

    private void renderCart() {
        cartItemsContainer.getChildren().clear();

        boolean isEmpty = facade.getCartItems().isEmpty();
        lblEmptyCart.setVisible(isEmpty);
        lblEmptyCart.setManaged(isEmpty);
        btnConfirm.setDisable(isEmpty);

        for (ItemShoppingCart item : facade.getCartItems()) {
            cartItemsContainer.getChildren().add(buildCartRow(item));
        }

        double subtotal = facade.getTotalOrderAmount();
        double total = isEmpty ? 0 : subtotal + SHIPPING_COST;
        lblSubtotal.setText(String.format("S/. %.2f", subtotal));
        lblTotal.setText(String.format("S/. %.2f", total));
    }

    private Node buildCartRow(ItemShoppingCart item) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(55);
        row.setSpacing(10);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(45);
        imageView.setFitWidth(45);
        imageView.setPreserveRatio(true);
        try {
            String path = "/images/" + item.getProduct().getImagePath();
            imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        } catch (Exception ignored) {
        }

        VBox info = new VBox(2);
        Label lblName = new Label(item.getProduct().getName());
        lblName.setFont(Font.font("Arial Bold", 15));
        lblName.setStyle("-fx-text-fill: #555555;");
        Label lblUnitPrice = new Label(String.format("S/. %.2f", item.getProduct().getPrice()));
        lblUnitPrice.setFont(Font.font("Arial", 12));
        lblUnitPrice.setStyle("-fx-text-fill: #999999;");
        info.getChildren().addAll(lblName, lblUnitPrice);
        HBox.setHgrow(info, javafx.scene.layout.Priority.ALWAYS);

        HBox quantityBox = new HBox();
        quantityBox.setAlignment(Pos.CENTER);
        quantityBox.setSpacing(4);
        quantityBox.setPrefHeight(32);
        quantityBox.setStyle("-fx-border-color: #e2e2e2; -fx-border-radius: 15; -fx-background-radius: 15; -fx-background-color: #ffffff;");

        Button btnMinus = new Button("-");
        btnMinus.setStyle("-fx-background-color: transparent; -fx-text-fill: #f26513; -fx-font-weight: bold;");
        btnMinus.setPadding(new Insets(2, 6, 2, 6));
        btnMinus.setOnAction(e -> facade.decreaseQuantity(item));

        Label lblQuantity = new Label(String.valueOf(item.getQuantity()));
        lblQuantity.setStyle("-fx-text-fill: #777777;");
        lblQuantity.setFont(Font.font("Arial", 13));

        Button btnPlus = new Button("+");
        btnPlus.setStyle("-fx-background-color: transparent; -fx-text-fill: #f26513; -fx-font-weight: bold;");
        btnPlus.setPadding(new Insets(2, 6, 2, 6));
        btnPlus.setOnAction(e -> facade.increaseQuantity(item));

        quantityBox.getChildren().addAll(btnMinus, lblQuantity, btnPlus);

        Label lblSubtotal = new Label(String.format("S/. %.2f", item.getSubtotal()));
        lblSubtotal.setStyle("-fx-text-fill: #333333;");
        lblSubtotal.setFont(Font.font("Arial Bold", 13));
        lblSubtotal.setPrefWidth(70);
        lblSubtotal.setAlignment(Pos.CENTER_RIGHT);

        Button btnRemove = new Button("✕");
        btnRemove.setStyle("-fx-background-color: transparent; -fx-text-fill: #bbbbbb; -fx-cursor: hand;");
        btnRemove.setOnAction(e -> facade.removeFromCart(item));

        row.getChildren().addAll(imageView, info, quantityBox, lblSubtotal, btnRemove);
        return row;
    }

    @FXML
    private void confirmOrder(javafx.event.ActionEvent event) {
        int userId = Session.getInstance().getCurrentUser().getId();
        boolean success = facade.submitOrder(userId);

        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (success) {
            alert.setContentText("¡Tu pedido fue confirmado con éxito!");
            alert.showAndWait();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Views/Orders.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.setContentText("No se pudo confirmar el pedido. Intenta nuevamente.");
            alert.showAndWait();
        }
    }

    @FXML
    private void goToPrincipalMenu(MouseEvent event) throws IOException {
        facade.removeCartObserver(cartObserver);
        Parent root = FXMLLoader.load(getClass().getResource("/Views/PrincipalMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
