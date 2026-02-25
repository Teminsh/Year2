package OOP.Lab5;

//region Task
/*
Лабораторная работа 5 (система авторизации)

Создать систему авторизации и хранения информации о пользователях приложении, не зависящую от
источника данных, поддерживающую автоматическую авторизацию пользователей, реализующую взаимодействие с источником данных
обобщенным образом.

Реализуем через паттерн репозиторий

1. Создать класс User с атрибутами:
    id: int
    name: str
    login: str
    password: str (поле не должно показываться при строковом представлении класс)
    email: str (сделать поле необязательным)
    address: str (сделать поле необязательным)

- Сделать, чтобы коллекция классов User умела сортироваться по полю name.
- Реализовать через dataclass или через аналоги в других языках (C# и Java: record)

2. Создать интерфейс / протокол IDataRepository[T] / DataRepsitoryProtocol[T] для системы CRUD = Create, Read, Update, Delete для произвольного типа данных:
  - get_all(self) -> Sequence[T]
  - get_by_id(self, id: int) -> T | None
  - add(self, item: T) -> None
  - update(self, item: T) -> None
  - delete(self, item: T) -> None

2. Создать интерфейс / протокол IUserRepository ( IDataRepository[User]) / UserRepositoryProtocol (DataRepsitoryProtocol[User]) для взаимодействия с типом данных User
 - get_by_login(self, login: str) -> User | None

3. Создать реализацию  DataRepository[T](IDataRepository[T) / DataRepitoryProtocol[T] supports IDataRepsitoryProtocol[T]
  - Осуществляет хранение данных в файле
  - Можно использовать сторонние сериализаторы (Напр., pickle, json, xml)

4. Создать реализацию UserRepository(IUserRepository) / supports UserRepositoryProtocol на основе DataRepository[T](IDataRepository[T) / DataRepitoryProtocol[T]

5. Создать интерфейс / протокол IAuthService / AuthServiceProtocol
  - sign_in(self, user: User) -> None
  - sign_out(selg, user: User) -> None
  - is_authorized -> bool
  - current_user  -> User

6. Создать реализацию IAuthService / AuthServiceProtocol, которая хранит информацию о текущем пользователе в файле и автоматически авторизует пользователя при повторном заходе в программу в случае наличия соответствующей записи в файле

7. Продемонстрировать работу реализованной системы
 - добавление пользователя
 - редактирование свойств пользователя
 - авторизация пользователя
 - смена текущего пользователя
 - авторматическая авторизация при повторном заходе в программу
 */
//endregion Task

import OOP.Lab5.auth.AuthService;
import OOP.Lab5.auth.IAuthService;
import OOP.Lab5.model.User;
import OOP.Lab5.repository.IUserRepository;
import OOP.Lab5.repository.UserRepository;

import java.io.File;
import java.util.List;

public class Main {
    private static final String USERS_FILE = "users.dat";
    private static final String SESSION_FILE = "session.dat";

    //region Utility
    private static void printSection(String title) {
        System.out.println("\n========== " + title + " ==========");
    }

    private static void cleanUp() {
        new File(USERS_FILE).delete();
        new File(SESSION_FILE).delete();
    }
    //endregion

    public static void main(String[] args) {
        IAuthService authService = new AuthService(SESSION_FILE);

        //region Automatic Authorisation on restart
        if (authService.isAuthorized()) {
            printSection("ПОВТОРНЫЙ ЗАПУСК — Автоматическая авторизация");
            System.out.println("isAuthorized = " + authService.isAuthorized());
            System.out.println("С возвращением, " + authService.getCurrentUser().name() + "!");
            System.out.println("Текущий пользователь: " + authService.getCurrentUser());

            cleanUp();
            System.out.println("\n[Файлы очищены. Следующий запуск — снова с нуля]");
            return;
        }
        //endregion

        cleanUp();
        IUserRepository repo = new UserRepository(USERS_FILE);

        //region Adding users
        printSection("1. Добавление пользователей");

        repo.add(User.of(1, "Михаил Захаров", "mzakharov", "pass1"));
        repo.add(User.withAll(2, "Анна Соколова", "asokolova", "pass2", "anna@mail.ru", "ул. Ленина, 5"));
        repo.add(User.withEmail(3, "Владимир Иванов", "vivanov", "pass3", "v.ivanov@gmail.com"));
        repo.add(User.withAddress(4, "Екатерина Попова", "epopova", "pass4", "ул. Бабиджона 24"));

        List<User> sorted = repo.getAll().stream().sorted().toList();
        System.out.println("Пользователи (отсортированы по имени):");
        sorted.forEach(System.out::println);
        //endregion

        //region editing user properties
        printSection("2. Редактирование свойств пользователя");

        User mikhail = repo.getById(1);
        System.out.println("До редактирования:    " + mikhail);

        repo.update(new User(
                mikhail.id(), mikhail.name(), mikhail.login(),
                mikhail.password(), "m.zakharov@bfu.ru", "пр. Мира, 14"
        ));

        System.out.println("После редактирования: " + repo.getById(1));
        //endregion

        //region user authorisation
        printSection("3. Авторизация пользователя");

        System.out.println("До входа: isAuthorized = " + authService.isAuthorized());
        authService.signIn(repo.getByLogin("asokolova"));
        System.out.println("После входа: isAuthorized = " + authService.isAuthorized());
        System.out.println("Текущий пользователь: " + authService.getCurrentUser());
        //endregion

        //region 4. change of current user
        printSection("4. Смена текущего пользователя");

        authService.signOut(authService.getCurrentUser());
        System.out.println("После выхода: isAuthorized = " + authService.isAuthorized());

        authService.signIn(repo.getByLogin("vivanov"));
        System.out.println("Вошёл новый пользователь: " + authService.getCurrentUser());
        //endregion

        //region data validation demonstration
        printSection("5. Демонстрация защиты входных данных");

        System.out.println("Попытка 1: Создание пользователя с пустым именем");
        try {
            User.of(5, "   ", "newlogin", "1234");
        } catch (IllegalArgumentException e) {
            System.out.println("Успешно заблокировано: " + e.getMessage());
        }

        System.out.println("\nПопытка 2: Добавление пользователя с уже занятым логином");
        try {
            repo.add(User.of(6, "Новый Человек", "mzakharov", "qwerty"));
        } catch (IllegalArgumentException e) {
            System.out.println("Успешно заблокировано: " + e.getMessage());
        }

        System.out.println("\nПопытка 3: Обновление несуществующего пользователя");
        try {
            repo.update(User.of(999, "Призрак", "ghost", "000"));
        } catch (IllegalArgumentException e) {
            System.out.println("Успешно заблокировано: " + e.getMessage());
        }
        //endregion
    }
}