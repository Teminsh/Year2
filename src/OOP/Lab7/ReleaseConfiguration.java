package OOP.Lab7;

public class ReleaseConfiguration {
    public static Injector create() {
        Injector injector = new Injector();
        injector.register(ILogger.class, FileLogger.class, LifeStyle.SINGLETON, "app.log");
        injector.register(INotificationService.class, SmsNotificationService.class,
                LifeStyle.SCOPED, "PROD_API_KEY");
        injector.register(IUserService.class, AdminUserService.class, LifeStyle.SINGLETON);
        return injector;
    }
}