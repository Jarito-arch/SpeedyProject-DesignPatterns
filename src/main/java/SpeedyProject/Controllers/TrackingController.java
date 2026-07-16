package SpeedyProject.Controllers;

import SpeedyProject.Models.Order;
import SpeedyProject.Patterns.Facade.OrderFacade;
import SpeedyProject.Patterns.Observer.Observer;
import SpeedyProject.Patterns.Observer.TrackingObserver;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TrackingController {

    @FXML
    private Label lblUserName;
    @FXML
    private VBox activeOrdersContainer;
    @FXML
    private Label lblEmptyTracking;
    @FXML
    private VBox progressSection;
    @FXML
    private Label lblSelectedOrder;
    @FXML
    private Label lblSelectedStatus;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btnAdvance;

    private final OrderFacade facade = OrderFacade.getInstance();
    private Observer trackingObserver;
    private Order selectedOrder;

    @FXML
    public void initialize() {
        if (Session.getInstance().getCurrentUser() != null) {
            lblUserName.setText(Session.getInstance().getCurrentUser().getName());
        }
        progressSection.setVisible(false);
        progressSection.setManaged(false);
        trackingObserver = new TrackingObserver(this::refresh);
        facade.addOrderObserver(trackingObserver);
        refresh();
    }

    private void refresh() {
        int userId = Session.getInstance().getCurrentUser().getId();
        List<Order> activeOrders = facade.getOrderHistory(userId).stream()
                .filter(Order::isActive)
                .toList();

        activeOrdersContainer.getChildren().clear();
        boolean isEmpty = activeOrders.isEmpty();
        lblEmptyTracking.setVisible(isEmpty);
        lblEmptyTracking.setManaged(isEmpty);

        for (Order order : activeOrders) {
            activeOrdersContainer.getChildren().add(buildActiveOrderCard(order));
        }

        if (selectedOrder != null) {
            Order refreshedSelected = activeOrders.stream()
                    .filter(o -> o.getId() == selectedOrder.getId())
                    .findFirst()
                    .orElse(null);
            if (refreshedSelected != null) {
                showProgressFor(refreshedSelected);
            } else {
                progressSection.setVisible(false);
                progressSection.setManaged(false);
                selectedOrder = null;
            }
        }
    }

    private Node buildActiveOrderCard(Order order) {
        VBox card = new VBox(4);
        card.setPrefWidth(333);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #e8e8e8; " +
                "-fx-background-color: #fcfcfc; -fx-cursor: hand;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label lblTitle = new Label("Pedido #" + order.getId());
        lblTitle.setFont(Font.font("Arial Bold", 15));
        javafx.scene.layout.Pane spacer = new javafx.scene.layout.Pane();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        Label lblStatus = new Label(order.getStatusLabel());
        lblStatus.setFont(Font.font("Arial Bold", 12));
        lblStatus.setStyle("-fx-text-fill: #F26513;");
        header.getChildren().addAll(lblTitle, spacer, lblStatus);

        Label lblCategory = new Label(order.getCommerceCategory() != null ? order.getCommerceCategory() : "Variado");
        lblCategory.setStyle("-fx-text-fill: #777777;");
        lblCategory.setFont(Font.font("Arial", 12));

        ProgressBar miniBar = new ProgressBar(order.getProgressPercentage() / 100.0);
        miniBar.setPrefWidth(300);
        miniBar.setStyle("-fx-accent: #F26513;");

        card.getChildren().addAll(header, lblCategory, miniBar);
        card.setOnMouseClicked(e -> {
            selectedOrder = order;
            showProgressFor(order);
        });
        return card;
    }

    private void showProgressFor(Order order) {
        progressSection.setVisible(true);
        progressSection.setManaged(true);
        lblSelectedOrder.setText("Pedido #" + order.getId());
        lblSelectedStatus.setText(order.getStatusLabel());
        progressBar.setProgress(order.getProgressPercentage() / 100.0);
        btnAdvance.setDisable(!order.isActive());
    }

    @FXML
    private void onAdvanceOrder() {
        if (selectedOrder != null && selectedOrder.isActive()) {
            facade.advanceOrder(selectedOrder);
        }
    }

    @FXML
    private void goToPrincipalMenu(MouseEvent event) throws IOException {
        facade.removeOrderObserver(trackingObserver);
        Parent root = FXMLLoader.load(getClass().getResource("/Views/PrincipalMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
