package OOP.Lab5.model;

import java.io.Serializable;

public record User(
        int id,
        String name,
        String login,
        String password,
        String email,
        String address
) implements Comparable<User>, Serializable, Identifiable {

    //region Validation Constructor
    public User {
        if (id <= 0) {
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Логин не может быть пустым");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
    }
    //endregion

    //region Factory Methods
    public static User of(int id, String name, String login, String password) {
        return new User(id, name, login, password, null, null);
    }

    public static User of(int id, String name, String login, String password, String email) {
        return new User(id, name, login, password, email, null);
    }
    public static User of(int id, String name, String login, String password, String email, String address) {
        return new User(id, name, login, password, email, address);
    }
    //endregion

    //region Interface Implementations
    @Override
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.name);
    }
    //endregion

    @Override
    public String toString() {
        return "User{id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", email=" + (email == null ? "нет" : email) +
                ", address=" + (address == null ? "нет" : address) +
                '}';
    }
}
