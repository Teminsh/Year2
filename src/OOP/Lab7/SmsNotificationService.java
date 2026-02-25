package OOP.Lab7;

public class SmsNotificationService implements INotificationService {
    private final ILogger logger;
    private final String apiKey;

    public SmsNotificationService(ILogger logger, String apiKey) {
        this.logger = logger;
        this.apiKey = apiKey;
    }

    @Override
    public void send(String recipient, String message) {
        logger.log("SMS [key=" + apiKey + ", to=" + recipient + "]: " + message);
    }

    @Override
    public String getType() {
        return "SmsService@" + Integer.toHexString(hashCode());
    }
}