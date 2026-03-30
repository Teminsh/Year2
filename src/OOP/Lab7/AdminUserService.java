package OOP.Lab7;

public class AdminUserService implements IUserService {
    private final INotificationService notificationService;
    private final ILogger logger;

    public AdminUserService(INotificationService notificationService, ILogger logger) {
        if (notificationService == null || logger == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.notificationService = notificationService;
        this.logger = logger;
    }

    @Override
    public void executeAction(String username) {
        logger.log("ADMIN ACTION TRIGGERED");
        notificationService.send("Admin executed system override: " + username);
    }
}
