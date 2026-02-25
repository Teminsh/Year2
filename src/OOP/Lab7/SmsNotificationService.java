package OOP.Lab7;

public class SmsNotificationService implements INotificationService {
    private final ILogger logger;
    private final String phoneCode;

    public SmsNotificationService(ILogger logger, String phoneCode) {
        if (logger == null || phoneCode == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.logger = logger;
        this.phoneCode = phoneCode;
    }

    @Override
    public void send(String message) {
        logger.log("Sending SMS [Code: " + phoneCode + "]: " + message);
    }
}