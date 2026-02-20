package OOP.Lab3;

//region Task
/*
Лабораторная работа 3 (Система логирования)

Создать систему логирования, применяя композицию (агрегацию),
с возможностью фильтрации и различных способов вывода информации.
Использовать либо протоколы, либо интерфейсы, либо чисто абстрактные классы в зависимости от используемого языка программирования.

1. Создать перечислитель LogLevel со значениями INFO, WARN, ERROR
 - LevelFilter - для фильтрации на основе перечислителя (его также создать )

2. Создать протокол/интерфейс фильтров ILogFilter / LogFilterProtocol:
  - match(self, log_level: LogLevel, text: str) -> bool

3. Создать несколько классов реализующих данный протокол/интерфейс
 - SimpleLogFilter - для фильтрации по вхождению паттерна, задаваемого текстом, в текст сообщения
 - ReLogFilter - для фильтрации по вхождению паттерна, задаваемого регулярным выражением, в текст сообщения
 - LevelFilter - Для фильтрации по LogLevel

4. Создать протокол/интерфейс обработчиков ILogHandler / LogHandlerProtocol:
 - handle(self, log_level: LogLevel, text: str) -> None

5. Создать неcколько классов реализующих данный протокол/интерфейс
 - FileHandler - для записи логов в файл
 - SocketHandler - для отправки логов через сокет
 - ConsoleHandler - для вывода логово в консоль
 - SyslogHandler - для записи логов в системные логи
 - FtpHandler - для записи логов на ftp сервер

6. Создать протокол/интерфейс обработчиков ILogFormatter / LogFormatterProtocol:
 - format(self, log_level: LogLevel, text: str) -> str

7. Реализовать форматтер, который к каждому сообщению в логах добавляет данные по следующему формату:
[<log_level>] [<data:yyyy.MM.dd hh:mm:ss>] <text>
где <>  - плейсхолдеры, которые должны быть заменены на значения переменных

8. Реализовать класс Logger, который принимает
  - список ILogFilter / LogFilterProtocol
  - список  ILogFormatter / LogFormatterProtocol
  - список ILogHandler / LogHandlerProtocol

 и реализует:
 - log(self, log_level: LogLevel, text: str) -> None - которая прогоняет логи через фильтры, потом последовательно через все форматтеры и отдает обработчикам
 - log_info(text: str) -> None - записывает логи с LogLevel = LogLevel.INFO
 - log_warn(text: str) -> None - записывает логи с LogLevel = LogLevel.WARN
 - log_error(text: str) -> None - записывает логи с LogLevel = LogLevel.ERROR


9. Продемонстрировать работу спроектированной системы классов
 */
//endregion

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        testBasics();
        testRegexFilter();
        testSyslogAndFtpHandlers();

        System.out.println("\n=== Done ===");
    }

    private static void testBasics()
    {
        List<ILogFilter> filters = new ArrayList<>();
        filters.add(new LevelFilter(LogLevel.INFO));
        filters.add(new SimpleLogFilter("System"));

        List<ILogFormatter> formatters = new ArrayList<>();
        formatters.add(new StandardFormatter());

        List<ILogHandler> handlers = new ArrayList<>();
        handlers.add(new ConsoleHandler());
        handlers.add(new FileHandler("app_logs.txt"));
        handlers.add(new SocketHandler("192.168.1.50", 8080));

        Logger logger = new Logger(filters, formatters, handlers);

        System.out.println("=== Testing Logger (basic) ===\n");

        logger.logInfo("System initialization started.");
        logger.logWarn("User uploaded a file.");
        logger.logError("System critical failure!");
    }

    private static void testRegexFilter()
    {
        System.out.println("\n--- Testing Regex Filter (Numbers only) ---");

        List<ILogFilter> filters = new ArrayList<>();
        filters.add(new ReLogFilter(".*\\d+.*"));

        List<ILogFormatter> formatters = Arrays.asList(new StandardFormatter());
        List<ILogHandler> handlers = Arrays.asList(new ConsoleHandler());

        Logger digitLogger = new Logger(filters, formatters, handlers);

        digitLogger.logInfo("No digits here");
        digitLogger.logInfo("Error code 404");
    }

    private static void testSyslogAndFtpHandlers()
    {
        System.out.println("\n--- Testing Syslog & FTP Handlers ---");

        List<ILogFilter> filters = new ArrayList<>();
        filters.add(new LevelFilter(LogLevel.WARN));

        List<ILogFormatter> formatters = Arrays.asList(new StandardFormatter());

        List<ILogHandler> handlers = new ArrayList<>();
        handlers.add(new SyslogHandler("MyApp"));
        handlers.add(new FtpHandler("logs.example.com", "logger"));

        Logger networkLogger = new Logger(filters, formatters, handlers);

        networkLogger.logInfo("System info message");
        networkLogger.logWarn("System warning: disk space low");
        networkLogger.logError("System error: database down");
    }
}