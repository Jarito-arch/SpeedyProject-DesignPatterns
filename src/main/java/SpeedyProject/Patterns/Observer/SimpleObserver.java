package SpeedyProject.Patterns.Observer;

/**
 * Adaptador genérico que permite registrar un método/lambda del controlador
 * (por ejemplo this::updateCartBadge) como Observer sin crear una clase anónima
 * en cada controlador. Evita duplicar la misma implementación de Observer en
 * cada clase concreta.
 */
public class SimpleObserver implements Observer {
    private final Runnable callback;

    public SimpleObserver(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void update() {
        callback.run();
    }
}
