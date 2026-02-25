package OOP.Lab7;

public class FileLogger implements ILogger {
    private final String filePath;

    public FileLogger(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        this.filePath = filePath;
    }

    @Override
    public void log(String message) {
        if (message == null) return;
        System.out.println("[FILE: " + filePath + "] " + message);
    }
}