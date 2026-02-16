package OOP.Lab3;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    //region Fields
    private final List<ILogFilter> filters;
    private final List<ILogFormatter> formatters;
    private final List<ILogHandler> handlers;
    //endregion

    //region Constructor
    public Logger(List<ILogFilter> filters, List<ILogFormatter> formatters, List<ILogHandler> handlers) {
        this.filters = filters != null ? filters : new ArrayList<>();
        this.formatters = formatters != null ? formatters : new ArrayList<>();
        this.handlers = handlers != null ? handlers : new ArrayList<>();
    }
    //endregion

    //region Core Logic
    public void log(LogLevel level, String text) {
        for (ILogFilter filter : filters) {
            if (!filter.match(level, text)) {
                return;
            }
        }

        String processedText = text;
        for (ILogFormatter formatter : formatters) {
            processedText = formatter.format(level, processedText);
        }

        for (ILogHandler handler : handlers) {
            handler.handle(level, processedText);
        }
    }
    //endregion

    //region Helpers
    public void logInfo(String text) {
        log(LogLevel.INFO, text);
    }

    public void logWarn(String text) {
        log(LogLevel.WARN, text);
    }

    public void logError(String text) {
        log(LogLevel.ERROR, text);
    }
    //endregion
}