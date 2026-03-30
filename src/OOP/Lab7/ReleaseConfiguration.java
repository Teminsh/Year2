package OOP.Lab7;

public class ReleaseConfiguration {
    public static Injector create() {
        Injector injector = new Injector();
        injector.register(ILogger.class, FileLogger.class, LifeStyle.SINGLETON, "production.log");
        injector.register(INotificationService.class, SmsNotificationService.class, LifeStyle.SCOPED, "+7");
        injector.register(IUserService.class, AdminUserService.class, LifeStyle.SINGLETON);
        return injector;
    }
}