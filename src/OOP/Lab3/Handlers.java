package OOP.Lab3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class ConsoleHandler implements ILogHandler {
    @Override
    public void handle(LogLevel level, String message) {
        if (level == LogLevel.ERROR) {
            System.err.println("CONSOLE: " + message);
        } else {
            System.out.println("CONSOLE: " + message);
        }
    }
}

class FileHandler implements ILogHandler {
    private final String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void handle(LogLevel level, String message) {
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SocketHandler implements ILogHandler {
    private final String host;
    private final int port;

    public SocketHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void handle(LogLevel level, String message) {
        System.out.println("[SocketHandler Stub] Sending to " + host + ":" + port + " -> " + message);
    }
}

class SyslogHandler implements ILogHandler {
    private final String identity;

    public SyslogHandler(String identity) {
        this.identity = identity;
    }

    @Override
    public void handle(LogLevel level, String message) {
        System.out.println("[SyslogHandler Stub] " + identity + ": " + message);
    }
}

class FtpHandler implements ILogHandler {
    private final String server;
    private final String user;

    public FtpHandler(String server, String user) {
        this.server = server;
        this.user = user;
    }

    @Override
    public void handle(LogLevel level, String message) {
        System.out.println("[FtpHandler Stub] Uploading log to ftp://" + user + "@" + server + " -> " + message);
    }
}