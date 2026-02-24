package OOP.Lab5.auth;

import OOP.Lab5.model.User;

public interface IAuthService {
    void signIn(User user);
    void signOut(User user);
    boolean isAuthorized();
    User getCurrentUser();
}