package OOP.Lab7;

//region Task
/*
Лабораторная работа 7 ("внедреж" зависимостей)

Написать сервис, который управляет ассоциациями между интерфейсами и классами их реализующими. См. Dependency Injection (Развитие концециии фабрики классов)

1. Создать класс инжектор, который должен поддерживать
- 3 различных режима жизненного цикла соаздаваемы классов LifeStyle: PerRequest, Scoped, Singleton
- регистрацию зависимости между интерфейсов и классом
 напр: register(self, interface_type, class_type, life_circle)
- возможность передачи дополнительных параметров в конструктор регистрируемого класса
 напр: register(self, interface_type, class_type, life_circle, params)
- использование в конструкторе регистрируемого интерфейса другие уже зарегистрированные интерфейсы
- метод для возвращаения экземпляра класса по интерфейсу.
напр: get_instance(self, interface_type) -> class_instance
- В зависимости от ассоциированного LifeStyle get_instance должен работать по-разному:
    PerRequest => возвращает каждый раз новый экзепляр класса
    Scoped => возвращает один и тот же экземпляр внутри Scope (внутри открытой области). Можно реализовать, например, через with в python
    Singleton => всегда возвращает один и тот же экземпляр объекта
- добавить также возможность ассоциации интерфейса с фабричным методом, возвращающим класс
  напр: register(self, interface_type, fabric_method)

2. Создать  минимум три интерфейса
напр: interface1, interface2, interface3
Под каждый интерфейс создать минимум два класса его поддерживающего с разными LifeCircle
напр: class1_debug(interface1), class1_release(interface1), class2_debug(interface2), class2_release(interface2), class3_debug(interface3), class3_release(interface3)

3. Создать две конфигурации c различными регистрациями реализаций interface1, interface2, interface3

4. Продемонстрировать получение экземпляров классов при помощи инжектора и их дальнейшее использование
 */
//endregion Task

public class Main {

    //region Entry Point
    public static void main(String[] args) {
        System.out.println("=========== DEMO 1: LIFE STYLES (PER_REQUEST, SINGLETON, SCOPED) ===========");
        demoLifeStyles();

        System.out.println("\n=========== DEMO 2: FACTORY METHOD ===========");
        demoFactoryMethod();

        System.out.println("\n=========== DEMO 3: FOOL-PROOFING ===========");
        demoFoolProofing();

        System.out.println("\n=========== DEMO 4: DEPENDENCY TREE ===========");
        demoDependencyTree();
    }
    //endregion

    //region Demos
    private static void demoLifeStyles() {
        Injector injector = DebugConfiguration.create();

        System.out.println("--- PER_REQUEST ---");
        ILogger logger1 = injector.getInstance(ILogger.class);
        ILogger logger2 = injector.getInstance(ILogger.class);
        System.out.println("Logger 1 hash: " + Integer.toHexString(logger1.hashCode()));
        System.out.println("Logger 2 hash: " + Integer.toHexString(logger2.hashCode()));
        System.out.println("Are they the same instance? " + (logger1 == logger2));

        System.out.println("\n--- SINGLETON ---");
        Injector releaseInjector = ReleaseConfiguration.create();
        ILogger fileLogger1 = releaseInjector.getInstance(ILogger.class);
        ILogger fileLogger2 = releaseInjector.getInstance(ILogger.class);
        System.out.println("FileLogger 1 hash: " + Integer.toHexString(fileLogger1.hashCode()));
        System.out.println("FileLogger 2 hash: " + Integer.toHexString(fileLogger2.hashCode()));
        System.out.println("Are they the same instance? " + (fileLogger1 == fileLogger2));

        System.out.println("\n--- SCOPED ---");
        try (Scope scope1 = releaseInjector.beginScope()) {
            INotificationService sms1 = releaseInjector.getInstance(INotificationService.class);
            INotificationService sms2 = releaseInjector.getInstance(INotificationService.class);
            System.out.println("Inside Scope 1 - Are they the same instance? " + (sms1 == sms2));

            sms1.send("Test SMS from Scope 1");
        }

        try (Scope scope2 = releaseInjector.beginScope()) {
            INotificationService sms3 = releaseInjector.getInstance(INotificationService.class);
            System.out.println("Inside Scope 2 - Created a new Scoped instance!");
            sms3.send("Test SMS from Scope 2");
        }
    }

    private static void demoFactoryMethod() {
        Injector injector = new Injector();

        injector.register(ILogger.class, ConsoleLogger::new);

        ILogger logger1 = injector.getInstance(ILogger.class);
        logger1.log("This logger was created via a Factory Method (Supplier)!");
    }

    private static void demoFoolProofing() {
        Injector injector = new Injector();

        executeWithErrorHandling("1. Null registration attempt", () ->
                injector.register(null, ConsoleLogger.class, LifeStyle.SINGLETON)
        );

        executeWithErrorHandling("2. Abstract class/Interface registration attempt", () ->
                injector.register((Class) ILogger.class, (Class) ILogger.class, LifeStyle.SINGLETON)
        );

        injector.register(ILogger.class, ConsoleLogger.class, LifeStyle.PER_REQUEST);

        executeWithErrorHandling("3. Duplicate registration attempt", () ->
                injector.register(ILogger.class, FileLogger.class, LifeStyle.SINGLETON)
        );

        executeWithErrorHandling("4. Requesting unregistered dependency", () ->
                injector.getInstance(IUserService.class)
        );

        injector.register(INotificationService.class, SmsNotificationService.class, LifeStyle.SCOPED, "+1");

        executeWithErrorHandling("5. Requesting SCOPED dependency without active scope", () ->
                injector.getInstance(INotificationService.class)
        );

        executeWithErrorHandling("6. Nested scopes attempt", () -> {
            try (Scope scope1 = injector.beginScope()) {
                try (Scope scope2 = injector.beginScope()) {
                    System.out.println("This won't be reached");
                }
            }
        });

        executeWithErrorHandling("7. Circular Dependency attempt", () -> {
            Injector badInjector = new Injector();
            badInjector.register(CircularClass.class, CircularClass.class, LifeStyle.PER_REQUEST);
            badInjector.getInstance(CircularClass.class);
        });
    }

    private static void demoDependencyTree() {
        Injector releaseInjector = ReleaseConfiguration.create();
        releaseInjector.printDependencyTree(IUserService.class);
    }
    //endregion

    //region Utilities
    private static void executeWithErrorHandling(String actionDescription, Runnable action) {
        System.out.println(actionDescription);
        try {
            action.run();
            System.out.println("  -> SUCCESS (Unexpected!)");
        } catch (Exception e) {
            System.out.println("  -> BLOCKED: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        System.out.println();
    }

    public static class CircularClass {
        public CircularClass(CircularClass selfDependency) { }
    }
    //endregion
}