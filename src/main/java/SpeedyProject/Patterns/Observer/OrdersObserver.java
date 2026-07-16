package SpeedyProject.Patterns.Observer;

/**
 * Observer concreto usado por la vista de Pedidos: se suscribe al
 * OrderSubject de pedidos de OrderFacade y refresca el historial cuando
 * se confirma un pedido nuevo o cambia de estado.
 */
public class OrdersObserver extends SimpleObserver {
    public OrdersObserver(Runnable onOrdersChanged) {
        super(onOrdersChanged);
    }
}
