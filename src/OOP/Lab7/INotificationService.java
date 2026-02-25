package OOP.Lab7;

public interface INotificationService {
    void send(String recipient, String message);
    String getType();
}