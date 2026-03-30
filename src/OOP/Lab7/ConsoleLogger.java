package OOP.Lab7;

public class ConsoleLogger implements ILogger {
    @Override
    public void log(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Log message cannot be null or empty");
        }
        System.out.println("[CONSOLE] " + message);
    }
}