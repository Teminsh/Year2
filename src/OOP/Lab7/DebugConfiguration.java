package OOP.Lab7;

public class DebugConfiguration {
    public static Injector create() {
        Injector injector = new Injector();
        injector.register(ILogger.class, ConsoleLogger.class, LifeStyle.PER_REQUEST);
        injector.register(INotificationService.class, EmailNotificationService.class, LifeStyle.PER_REQUEST);
        injector.register(IUserService.class, BasicUserService.class, LifeStyle.PER_REQUEST);
        return injector;
    }
}