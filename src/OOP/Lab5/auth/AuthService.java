package OOP.Lab5.auth;

import OOP.Lab5.model.User;

import java.io.*;

public class AuthService implements IAuthService {

    //region Fields
    private final String sessionFilePath;
    private User currentUser;
    //endregion

    //region Constructor
    public AuthService(String sessionFilePath) {
        this.sessionFilePath = sessionFilePath;
        this.currentUser = loadSession();
    }
    //endregion

    //region Session Persistence
    private User loadSession() {
        File file = new File(sessionFilePath);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (User) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    private void saveSession() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sessionFilePath))) {
            oos.writeObject(currentUser);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save session", e);
        }
    }

    private void clearSession() {
        new File(sessionFilePath).delete();
        currentUser = null;
    }
    //endregion

    //region IAuthService
    @Override
    public void signIn(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Попытка авторизации несуществующего пользователя (null)");
        }
        currentUser = user;
        saveSession();
    }

    @Override
    public void signOut(User user) {
        clearSession();
    }

    @Override
    public boolean isAuthorized() {
        return currentUser != null;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
    //endregion
}
