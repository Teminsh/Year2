package OOP.Lab5.repository;

import OOP.Lab5.model.User;

public interface IUserRepository extends IDataRepository<User> {
    User getByLogin(String login);
}