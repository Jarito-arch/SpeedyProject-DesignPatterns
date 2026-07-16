package SpeedyProject.Patterns.State;

/** Estado final: el pedido ya fue entregado. No hay transición posterior. */
public class DeliveredState implements OrderState {
    @Override
    public OrderState changingState() {
        return this;
    }

    @Override
    public String getState() {
        return "Entregado";
    }

    @Override
    public int getProgressPercentage() {
        return 100;
    }
}
