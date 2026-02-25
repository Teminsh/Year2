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
        System.out.println("========== Debug Configuration ==========\n");
        demoDebugConfig();

        System.out.println("\n========== Release Configuration ==========\n");
        demoReleaseConfig();

        System.out.println("\n========== Factory Method ==========\n");
        demoFactory();
    }
    //endregion

    //region Debug Demo
    private static void demoDebugConfig() {
        Injector injector = DebugConfiguration.create();

        System.out.println("[ PER_REQUEST: ILogger -> ConsoleLogger ]");
        ILogger l1 = injector.getInstance(ILogger.class);
        ILogger l2 = injector.getInstance(ILogger.class);
        l1.log("First call");
        l2.log("Second call");
        System.out.println("Same instance? " + (l1 == l2));
        System.out.println("l1: " + l1.getName());
        System.out.println("l2: " + l2.getName());

        System.out.println("\n[ SCOPED: IUserService -> BasicUserService ]");
        System.out.println("--- Scope 1 ---");
        try (Scope scope1 = injector.beginScope()) {
            IUserService us1 = injector.getInstance(IUserService.class);
            IUserService us2 = injector.getInstance(IUserService.class);
            System.out.println("Same in scope? " + (us1 == us2));
            us1.createUser("Alice");
            us2.createUser("Bob");
        }

        System.out.println("--- Scope 2 (новый экземпляр) ---");
        try (Scope scope2 = injector.beginScope()) {
            IUserService us3 = injector.getInstance(IUserService.class);
            System.out.println("New scope instance: " + us3.getInfo());
            us3.deleteUser("Alice");
        }

        System.out.println("\n[ PER_REQUEST: INotificationService -> EmailNotificationService ]");
        INotificationService ns1 = injector.getInstance(INotificationService.class);
        INotificationService ns2 = injector.getInstance(INotificationService.class);
        ns1.send("user@test.com", "Msg 1");
        ns2.send("user@test.com", "Msg 2");
        System.out.println("Same instance? " + (ns1 == ns2));
        System.out.println("ns1: " + ns1.getType());
        System.out.println("ns2: " + ns2.getType());
    }
    //endregion

    //region Release Demo
    private static void demoReleaseConfig() {
        Injector injector = ReleaseConfiguration.create();

        System.out.println("[ SINGLETON: ILogger -> FileLogger ]");
        ILogger l1 = injector.getInstance(ILogger.class);
        ILogger l2 = injector.getInstance(ILogger.class);
        l1.log("Entry 1");
        l2.log("Entry 2");
        System.out.println("Same instance? " + (l1 == l2));
        System.out.println("Logger: " + l1.getName());

        System.out.println("\n[ SINGLETON: IUserService -> AdminUserService ]");
        System.out.println("Примечание: AdminUserService (Singleton) зависит от SmsNotificationService");
        System.out.println("(Scoped) — это 'captive dependency'. Первый вызов требует открытого scope.");
        try (Scope warmUp = injector.beginScope()) {
            IUserService admin = injector.getInstance(IUserService.class);
            admin.createUser("Dave");
        }
        IUserService admin1 = injector.getInstance(IUserService.class);
        IUserService admin2 = injector.getInstance(IUserService.class);
        System.out.println("Same singleton? " + (admin1 == admin2));
        admin1.deleteUser("Dave");

        System.out.println("\n[ SCOPED: INotificationService -> SmsNotificationService ]");
        try (Scope scope = injector.beginScope()) {
            INotificationService ns1 = injector.getInstance(INotificationService.class);
            INotificationService ns2 = injector.getInstance(INotificationService.class);
            System.out.println("Same in scope? " + (ns1 == ns2));
            ns1.send("+79001234567", "Release alert");
            System.out.println("Service: " + ns1.getType());
        }
    }
    //endregion

    //region Factory Demo
    private static void demoFactory() {
        Injector injector = new Injector();
        int[] callCount = {0};

        injector.register(ILogger.class, () -> {
            callCount[0]++;
            System.out.println("Factory: creating logger #" + callCount[0]);
            return new ConsoleLogger();
        });

        ILogger fl1 = injector.getInstance(ILogger.class);
        ILogger fl2 = injector.getInstance(ILogger.class);
        fl1.log("Factory logger call 1");
        fl2.log("Factory logger call 2");
        System.out.println("Factory вызван: " + callCount[0] + " раза");
        System.out.println("Same instance? " + (fl1 == fl2));
        System.out.println("fl1: " + fl1.getName());
        System.out.println("fl2: " + fl2.getName());
    }
    //endregion
}

