package OOP.Lab3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class StandardFormatter implements ILogFormatter {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    @Override
    public String format(LogLevel level, String message) {
        String dateStr = LocalDateTime.now().format(dateFormatter);
        return String.format("[%s] [%s] %s", level, dateStr, message);
    }
}
