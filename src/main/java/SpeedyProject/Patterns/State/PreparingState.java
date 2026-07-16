package SpeedyProject.Patterns.State;

/** Estado inicial de todo pedido: el comercio está preparando los productos. */
public class PreparingState implements OrderState {
    @Override
    public OrderState changingState() {
        return new OnTheWayState();
    }

    @Override
    public String getState() {
        return "Preparando";
    }

    @Override
    public int getProgressPercentage() {
        return 33;
    }
}
