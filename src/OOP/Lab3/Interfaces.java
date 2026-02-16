package OOP.Lab3;

interface ILogFilter {
    boolean match(LogLevel level, String message);
}

interface ILogFormatter {
    String format(LogLevel level, String message);
}

interface ILogHandler {
    void handle(LogLevel level, String message);
}