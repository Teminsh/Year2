package OOP.Lab6.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class DualOutput implements Closeable {
    //region fields
    private final BufferedWriter fileWriter;
    private final BufferedWriter consoleWriter;
    //endregion

    public DualOutput(Path file) throws Exception {
        this.fileWriter = Files.newBufferedWriter(file);
        this.consoleWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void log(String consoleLine, String fileLine) {
        try {
            String formattedConsole = String.format("%-64s", consoleLine);
            consoleWriter.write(formattedConsole);
            consoleWriter.newLine();
            consoleWriter.flush();

            fileWriter.write(fileLine);
            fileWriter.newLine();
            fileWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try { fileWriter.close(); } catch (Exception ignored) { }
        try { consoleWriter.flush(); } catch (Exception ignored) { }
    }
}
