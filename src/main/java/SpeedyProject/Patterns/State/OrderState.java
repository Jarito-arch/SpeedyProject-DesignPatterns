package SpeedyProject.Patterns.State;

/**
 * Patrón State (GoF).
 * Cada estado sabe en qué estado se convierte el pedido (changingState) y cómo
 * se representa (getState / getProgressPercentage), evitando condicionales
 * if/else repartidos por los controladores para decidir "qué sigue".
 */
public interface OrderState {

    /** @return el siguiente estado del pedido según el flujo de negocio. */
    OrderState changingState();

    /** @return la etiqueta legible/persistible del estado (coincide con la BD). */
    String getState();

    /** @return progreso del pedido de 0 a 100, usado por la barra de seguimiento. */
    int getProgressPercentage();

    /**
     * Factory estático que reconstruye el objeto State a partir de la etiqueta
     * guardada en base de datos.
     */
    static OrderState fromLabel(String label) {
        if (label == null) {
            return new PreparingState();
        }
        switch (label.trim()) {
            case "Preparando":
                return new PreparingState();
            case "En camino":
                return new OnTheWayState();
            case "Entregado":
                return new DeliveredState();
            default:
                throw new IllegalArgumentException("Estado de pedido desconocido: " + label);
        }
    }
}
