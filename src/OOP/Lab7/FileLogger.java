package OOP.Lab7;

public class FileLogger implements ILogger {
    private final String filePath;

    public FileLogger(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void log(String message) {
        System.out.println("[FILE:" + filePath + "] " + message);
    }

    @Override
    public String getName() {
        return "FileLogger@" + Integer.toHexString(hashCode());
    }
}