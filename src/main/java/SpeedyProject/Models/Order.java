package SpeedyProject.Models;

import SpeedyProject.Patterns.State.OrderState;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;
    private LocalDate date;
    private double total;
    private OrderState state;
    //
    private List<OrderDetail> details;
    public void calculateTotal(){

    }
    public void changeState(){

    }
}
