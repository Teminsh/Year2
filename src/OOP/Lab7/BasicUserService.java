package OOP.Lab7;

public class BasicUserService implements IUserService {
    private final INotificationService notificationService;

    public BasicUserService(INotificationService notificationService) {
        if (notificationService == null) {
            throw new IllegalArgumentException("Notification service cannot be null");
        }
        this.notificationService = notificationService;
    }

    @Override
    public void executeAction(String username) {
        notificationService.send("Action executed by basic user: " + username);
    }
}