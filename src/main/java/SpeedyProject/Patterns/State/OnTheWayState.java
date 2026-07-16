package SpeedyProject.Patterns.State;

/** El pedido salió del comercio y va camino al cliente. */
public class OnTheWayState implements OrderState {
    @Override
    public OrderState changingState() {
        return new DeliveredState();
    }

    @Override
    public String getState() {
        return "En camino";
    }

    @Override
    public int getProgressPercentage() {
        return 66;
    }
}
