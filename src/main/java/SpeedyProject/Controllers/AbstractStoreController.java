package SpeedyProject.Controllers;

import SpeedyProject.Dao.ProductDao;
import SpeedyProject.Dao.ProductDaoImp;
import SpeedyProject.Models.Product;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public abstract class AbstractStoreController {

    @FXML
    protected FlowPane productsContainer;
    @FXML
    protected Label lblCartCount;
    @FXML
    protected Label lblUserName;
    @FXML
    protected ImageView cartIcon;

    protected final ProductDao productDao = new ProductDaoImp();
    protected final OrderFacade facade = OrderFacade.getInstance();
    private Observer cartObserver;

    /** @return la categoría de comercio que este controlador debe mostrar (coincide con la BD). */
    protected abstract String getCommerceCategory();

    @FXML
    public void initialize() {
        if (lblUserName != null && Session.getInstance().getCurrentUser() != null) {
            lblUserName.setText(Session.getInstance().getCurrentUser().getName());
        }
        cartObserver = new SimpleObserver(this::updateCartBadge);
        facade.addCartObserver(cartObserver);
        loadProducts();
        updateCartBadge();
    }

    protected void loadProducts() {
        productsContainer.getChildren().clear();
        List<Product> products = productDao.getProductsByCategory(getCommerceCategory());
        for (Product product : products) {
            productsContainer.getChildren().add(buildProductCard(product));
        }
    }

    protected Node buildProductCard(Product product) {
        VBox card = new VBox(4);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(110, 165);
        card.setStyle("-fx-border-color: #E0E0E0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: white;");

        ImageView imageView = new ImageView();
        imageView.setFitHeight(70);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        try {
            String path = "/images/" + product.getImagePath();
            imageView.setImage(new Image(getClass().getResourceAsStream(path)));
        } catch (Exception ignored) {
            // si la imagen no existe, la tarjeta igual se muestra sin foto
        }
        VBox.setMargin(imageView, new Insets(5, 0, 0, 0));

        Label lblName = new Label(product.getName());
        lblName.setWrapText(true);
        lblName.setStyle("-fx-text-fill: #555555;");
        lblName.setFont(Font.font("Arial", 10));
        lblName.setAlignment(Pos.CENTER);
        lblName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label lblPrice = new Label(String.format("S/. %.2f", product.getPrice()));
        lblPrice.setStyle("-fx-text-fill: #888888;");
        lblPrice.setFont(Font.font("Arial", 11));

        Button btnAdd = new Button();
        btnAdd.setPrefWidth(95);
        btnAdd.setPrefHeight(24);
        btnAdd.setFont(Font.font("Arial", 9));
        btnAdd.setStyle("-fx-cursor: hand; -fx-background-radius: 12; -fx-text-fill: white;");
        VBox.setMargin(btnAdd, new Insets(0, 0, 5, 0));
        refreshButtonState(btnAdd, product);

        btnAdd.setOnAction(event -> {
            facade.addProductToCart(product);
            refreshButtonState(btnAdd, product);
        });

        card.getChildren().addAll(imageView, lblName, lblPrice, btnAdd);
        return card;
    }

    private void refreshButtonState(Button button, Product product) {
        if (facade.isProductInCart(product.getId())) {
            button.setText("Añadido ✓");
            button.setStyle(button.getStyle() + "-fx-background-color: #d65810;");
        } else {
            button.setText("Añadir al carrito");
            button.setStyle(button.getStyle() + "-fx-background-color: #F26513;");
        }
    }

    protected void updateCartBadge() {
        if (lblCartCount == null) {
            return;
        }
        int count = facade.getCartItemsCount();
        lblCartCount.setText(String.valueOf(count));
        lblCartCount.setVisible(count > 0);
        lblCartCount.setManaged(count > 0);
    }

    @FXML
    protected void goToPrincipalMenu(MouseEvent event) throws IOException {
        facade.removeCartObserver(cartObserver);
        navigateTo("/Views/PrincipalMenu.fxml", (Node) event.getSource());
    }

    @FXML
    protected void goToCart(MouseEvent event) throws IOException {
        facade.removeCartObserver(cartObserver);
        navigateTo("/Views/ShoppingCart.fxml", (Node) event.getSource());
    }

    protected void navigateTo(String fxmlPath, Node sourceNode) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
