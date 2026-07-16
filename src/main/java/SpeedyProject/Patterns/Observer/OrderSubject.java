package SpeedyProject.Patterns.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto observable genérico (GoF - Observer).
 * OrderFacade mantiene dos instancias de esta clase: una para cambios del
 * carrito (badge del ícono) y otra para cambios del estado de los pedidos
 * (refresco de Pedidos/Seguimiento), evitando que las vistas se acoplen entre sí.
 */
public class OrderSubject {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notificationObservers() {
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update();
        }
    }
}
