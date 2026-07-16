package SpeedyProject.Controllers;

import SpeedyProject.Models.Order;
import SpeedyProject.Models.OrderDetail;
import SpeedyProject.Patterns.Facade.OrderFacade;
import SpeedyProject.Patterns.Observer.Observer;
import SpeedyProject.Patterns.Observer.OrdersObserver;
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
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderController {

    @FXML
    private Label lblUserName;
    @FXML
    private VBox ordersContainer;
    @FXML
    private Label lblEmptyOrders;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final OrderFacade facade = OrderFacade.getInstance();
    private Observer ordersObserver;

    @FXML
    public void initialize() {
        if (Session.getInstance().getCurrentUser() != null) {
            lblUserName.setText(Session.getInstance().getCurrentUser().getName());
        }
        ordersObserver = new OrdersObserver(this::loadOrders);
        facade.addOrderObserver(ordersObserver);
        loadOrders();
    }

    private void loadOrders() {
        int userId = Session.getInstance().getCurrentUser().getId();
        List<Order> orders = facade.getOrderHistory(userId);

        ordersContainer.getChildren().clear();
        boolean isEmpty = orders.isEmpty();
        lblEmptyOrders.setVisible(isEmpty);
        lblEmptyOrders.setManaged(isEmpty);

        for (Order order : orders) {
            ordersContainer.getChildren().add(buildOrderCard(order));
        }
    }

    private Node buildOrderCard(Order order) {
        VBox card = new VBox(2);
        card.setPrefWidth(333);
        card.setStyle("-fx-background-color: white; -fx-border-color: #e8e8e8; -fx-border-radius: 8; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        card.setPadding(new Insets(10));

        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        VBox info = new VBox(2);
        Label lblNumber = new Label("#" + order.getId());
        lblNumber.setFont(Font.font("Arial Bold", 15));
        lblNumber.setStyle("-fx-text-fill: #555555;");

        Label lblCategory = new Label(order.getCommerceCategory() != null ? order.getCommerceCategory() : "Variado");
        lblCategory.setFont(Font.font("Arial", 12));
        lblCategory.setStyle("-fx-text-fill: #777777;");

        String dateText = order.getDate() != null ? order.getDate().format(FORMATTER) : "";
        Label lblDate = new Label(dateText);
        lblDate.setFont(Font.font("Arial", 11));
        lblDate.setStyle("-fx-text-fill: #b5b5b5;");

        info.getChildren().addAll(lblNumber, lblCategory, lblDate);
        HBox.setHgrow(info, javafx.scene.layout.Priority.ALWAYS);

        VBox statusBox = new VBox(4);
        statusBox.setAlignment(Pos.CENTER_RIGHT);
        Label lblStatus = new Label(order.getStatusLabel());
        lblStatus.setFont(Font.font("Arial Bold", 12));
        lblStatus.setStyle("-fx-text-fill: " + statusColor(order.getStatusLabel()) + ";");
        Label lblTotal = new Label(String.format("S/. %.2f", order.getTotal()));
        lblTotal.setFont(Font.font("Arial Bold", 13));
        lblTotal.setStyle("-fx-text-fill: #333333;");
        statusBox.getChildren().addAll(lblStatus, lblTotal);

        row.getChildren().addAll(info, statusBox);
        card.getChildren().add(row);

        card.setOnMouseClicked(e -> onOrderSelected(order));
        return card;
    }

    private String statusColor(String status) {
        if ("Entregado".equals(status)) {
            return "#318243";
        }
        return "#F26513";
    }

    private void onOrderSelected(Order order) {
        if (order.isActive()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Views/Tracking.fxml"));
                Stage stage = (Stage) ordersContainer.getScene().getWindow();
                facade.removeOrderObserver(ordersObserver);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showOrderSummary(order);
        }
    }

    /** Ventana modal con el resumen moderno de un pedido ya entregado. */
    private void showOrderSummary(Order order) {
        List<OrderDetail> details = facade.getOrderDetails(order.getId());

        VBox root = new VBox(12);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");

        Label title = new Label("Pedido #" + order.getId());
        title.setFont(Font.font("Arial Bold", 20));

        Label subtitle = new Label("Entregado " + (order.getDate() != null ? order.getDate().format(FORMATTER) : ""));
        subtitle.setStyle("-fx-text-fill: #888888;");

        VBox itemsBox = new VBox(8);
        for (OrderDetail detail : details) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);

            ImageView imageView = new ImageView();
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            imageView.setPreserveRatio(true);
            try {
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/" + detail.getProduct().getImagePath())));
            } catch (Exception ignored) {
            }

            VBox info = new VBox(1);
            Label lblName = new Label(detail.getProduct().getName() + " x" + detail.getQuantity());
            lblName.setFont(Font.font("Arial Bold", 13));
            Label lblUnit = new Label(String.format("S/. %.2f c/u", detail.getProduct().getPrice()));
            lblUnit.setStyle("-fx-text-fill: #999999;");
            lblUnit.setFont(Font.font("Arial", 11));
            info.getChildren().addAll(lblName, lblUnit);
            HBox.setHgrow(info, javafx.scene.layout.Priority.ALWAYS);
            info.setPadding(new Insets(0, 0, 0, 10));

            Label lblSubtotal = new Label(String.format("S/. %.2f", detail.getSubtotal()));
            lblSubtotal.setFont(Font.font("Arial Bold", 13));

            row.getChildren().addAll(imageView, info, lblSubtotal);
            itemsBox.getChildren().add(row);
        }

        HBox totalRow = new HBox();
        totalRow.setAlignment(Pos.CENTER_LEFT);
        Label lblTotalCaption = new Label("Total");
        lblTotalCaption.setFont(Font.font("Arial Bold", 15));
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        Label lblTotalValue = new Label(String.format("S/. %.2f", order.getTotal()));
        lblTotalValue.setFont(Font.font("Arial Bold", 15));
        lblTotalValue.setStyle("-fx-text-fill: #F26513;");
        totalRow.getChildren().addAll(lblTotalCaption, spacer, lblTotalValue);

        Button btnClose = new Button("Cerrar");
        btnClose.setStyle("-fx-background-color: #F26513; -fx-text-fill: white; -fx-background-radius: 20; -fx-cursor: hand;");
        btnClose.setPrefWidth(120);

        root.getChildren().addAll(title, subtitle, new Separator(), itemsBox, new Separator(), totalRow, btnClose);

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Resumen del pedido");
        Scene scene = new Scene(root, 340, 480);
        dialog.setScene(scene);
        btnClose.setOnAction(e -> dialog.close());
        dialog.showAndWait();
    }

    @FXML
    private void goToPrincipalMenu(MouseEvent event) throws IOException {
        facade.removeOrderObserver(ordersObserver);
        Parent root = FXMLLoader.load(getClass().getResource("/Views/PrincipalMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
