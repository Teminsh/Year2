package OOP.Lab5.repository;

import OOP.Lab5.model.User;

public class UserRepository extends DataRepository<User> implements IUserRepository {

    public UserRepository(String filePath) {
        super(filePath);
    }

    @Override
    public void add(User item) {
        if (item != null && getByLogin(item.login()) != null) {
            throw new IllegalArgumentException("Пользователь с логином '" + item.login() + "' уже существует");
        }
        super.add(item);
    }

    @Override
    public User getByLogin(String login) {
        if (login == null || login.isBlank()) return null;

        return getAll().stream()
                .filter(user -> user.login().equals(login))
                .findFirst()
                .orElse(null);
    }
}
