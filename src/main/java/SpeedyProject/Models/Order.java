package SpeedyProject.Models;

import SpeedyProject.Patterns.State.OrderState;

import java.time.LocalDate;

public class Order {
    private int id;
    private LocalDate date;
    private double total;
    private OrderState state;
    public void calculateTotal(){

    }
    public void changeState(){

    }
}
