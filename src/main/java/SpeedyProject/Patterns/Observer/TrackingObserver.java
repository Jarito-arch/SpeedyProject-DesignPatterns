package SpeedyProject.Patterns.Observer;

/**
 * Observer concreto usado por la vista de Seguimiento: se suscribe al
 * OrderSubject de pedidos de OrderFacade y refresca las tarjetas activas
 * y la barra de progreso cuando el estado de un pedido avanza.
 */
public class TrackingObserver extends SimpleObserver {
    public TrackingObserver(Runnable onTrackingChanged) {
        super(onTrackingChanged);
    }
}
