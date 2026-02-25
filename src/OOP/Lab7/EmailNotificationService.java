package OOP.Lab7;

public class EmailNotificationService implements INotificationService {
    private final ILogger logger;
    private final String senderEmail;

    public EmailNotificationService(ILogger logger, String senderEmail) {
        this.logger = logger;
        this.senderEmail = senderEmail;
    }

    @Override
    public void send(String recipient, String message) {
        logger.log("Email [" + senderEmail + " → " + recipient + "]: " + message);
    }

    @Override
    public String getType() {
        return "EmailService@" + Integer.toHexString(hashCode());
    }
}