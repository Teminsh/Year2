package OOP.Lab7;

public class BasicUserService implements IUserService {
    private final ILogger logger;
    private final INotificationService notificationService;

    public BasicUserService(ILogger logger, INotificationService notificationService) {
        this.logger = logger;
        this.notificationService = notificationService;
    }

    @Override
    public void createUser(String username) {
        logger.log("Creating user: " + username);
        notificationService.send(username, "Welcome, " + username + "!");
    }

    @Override
    public void deleteUser(String username) {
        logger.log("Deleting user: " + username);
    }

    @Override
    public String getInfo() {
        return "BasicUserService@" + Integer.toHexString(hashCode());
    }
}