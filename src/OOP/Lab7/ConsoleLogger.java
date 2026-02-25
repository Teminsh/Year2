package OOP.Lab7;

public class ConsoleLogger implements ILogger {
    @Override
    public void log(String message) {
        System.out.println("[CONSOLE] " + message);
    }

    @Override
    public String getName() {
        return "ConsoleLogger@" + Integer.toHexString(hashCode());
    }
}