package OOP.Lab7;

public class AdminUserService implements IUserService {
    private final ILogger logger;
    private final INotificationService notificationService;

    public AdminUserService(ILogger logger, INotificationService notificationService) {
        this.logger = logger;
        this.notificationService = notificationService;
    }

    @Override
    public void createUser(String username) {
        logger.log("[ADMIN] Creating user: " + username);
        notificationService.send(username, "[ADMIN] Account created for " + username);
    }

    @Override
    public void deleteUser(String username) {
        logger.log("[ADMIN] Archiving user: " + username);
        notificationService.send(username, "Your account has been deactivated.");
    }

    @Override
    public String getInfo() {
        return "AdminUserService@" + Integer.toHexString(hashCode());
    }
}
