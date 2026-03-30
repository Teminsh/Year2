package OOP.Lab7;

public class Scope implements AutoCloseable {
    private final Injector injector;

    Scope(Injector injector) {
        this.injector = injector;
        injector.enterScope();
    }

    @Override
    public void close() {
        injector.exitScope();
    }
}