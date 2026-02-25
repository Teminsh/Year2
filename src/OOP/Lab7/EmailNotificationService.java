package OOP.Lab7;

public class EmailNotificationService implements INotificationService {
    private final ILogger logger;

    public EmailNotificationService(ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger dependency cannot be null");
        }
        this.logger = logger;
    }

    @Override
    public void send(String message) {
        logger.log("Sending EMAIL: " + message);
    }
}
