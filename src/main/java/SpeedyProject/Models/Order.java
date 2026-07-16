package SpeedyProject.Models;

import SpeedyProject.Patterns.State.OrderState;
import SpeedyProject.Patterns.State.PreparingState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class Order {
    private int id;
    private LocalDateTime date;
    private double total;
    private OrderState state;
    private String commerceCategory;
    private List<OrderDetail> details = new ArrayList<>();

    public Order() {
        this.state = new PreparingState();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getCommerceCategory() {
        return commerceCategory;
    }

    public void setCommerceCategory(String commerceCategory) {
        this.commerceCategory = commerceCategory;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    /** GRASP - Information Expert: el pedido conoce sus detalles, por lo tanto calcula su propio total. */
    public void calculateTotal() {
        this.total = details.stream().mapToDouble(OrderDetail::getSubtotal).sum();
    }

    /** Avanza al siguiente estado delegando en el patrón State. */
    public void changeState() {
        this.state = state.changingState();
    }

    public String getStatusLabel() {
        return state.getState();
    }

    public int getProgressPercentage() {
        return state.getProgressPercentage();
    }

    public boolean isActive() {
        return !(state instanceof SpeedyProject.Patterns.State.DeliveredState);
    }
}
