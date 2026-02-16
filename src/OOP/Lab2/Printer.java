package OOP.Lab2;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Printer implements AutoCloseable {

    //region Fields
    private final Color color;
    private final int[] position;
    private final String symbol;
    private static Map<Character, List<String>> currentFont = new HashMap<>();
    private static int fontHeight = 5;
    //endregion

    //region Constructor
    public Printer(Color color, int[] position, String symbol) {
        this.color = color;
        this.position = position.clone();
        this.symbol = symbol;
        System.out.print(AnsiCodes.SAVE_CURSOR);
    }
    //endregion

    //region File Operations
    public static void loadFont(String fontFile) throws IOException {
        Path path = Paths.get(fontFile);

        if (!Files.exists(path)) {
            createDefaultFontFile(path, fontFile);
        }

        currentFont.clear();
        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) {
            throw new IOException("Font file is empty");
        }

        if (fontFile.toLowerCase().endsWith(".json")) {
            loadJsonFont(lines);
        } else {
            loadTextFont(lines);
        }
    }

    private static void createDefaultFontFile(Path path, String filename) throws IOException {
        String content = "";
        if (filename.equals("font5.txt")) {
            content = getFont5Content();
        } else if (filename.equals("font7.txt")) {
            content = getFont7Content();
        } else {
            Files.createFile(path);
            return;
        }
        Files.write(path, content.getBytes());
    }
    //endregion

    //region Default files
    private static String getFont5Content() {
        return "CHAR:A\n  *  \n * * \n*****\n*   *\n*   *\n\n" +
                "CHAR:B\n**** \n*   *\n**** \n*   *\n**** \n\n" +
                "CHAR:C\n ****\n*    \n*    \n*    \n ****\n\n" +
                "CHAR:D\n**** \n*   *\n*   *\n*   *\n**** \n\n" +
                "CHAR:E\n*****\n*    \n**** \n*    \n*****\n\n" +
                "CHAR:F\n*****\n*    \n**** \n*    \n*    \n\n" +
                "CHAR:G\n ****\n*    \n*  **\n*   *\n ****\n\n" +
                "CHAR:H\n*   *\n*   *\n*****\n*   *\n*   *\n\n" +
                "CHAR:I\n*****\n  *  \n  *  \n  *  \n*****\n\n" +
                "CHAR:J\n  ***\n    *\n    *\n*   *\n *** \n\n" +
                "CHAR:K\n*   *\n*  * \n***  \n*  * \n*   *\n\n" +
                "CHAR:L\n*    \n*    \n*    \n*    \n*****\n\n" +
                "CHAR:M\n*   *\n** **\n* * *\n*   *\n*   *\n\n" +
                "CHAR:N\n*   *\n**  *\n* * *\n*  **\n*   *\n\n" +
                "CHAR:O\n *** \n*   *\n*   *\n*   *\n *** \n\n" +
                "CHAR:P\n**** \n*   *\n**** \n*    \n*    \n\n" +
                "CHAR:Q\n *** \n*   *\n*   *\n*  * \n ** *\n\n" +
                "CHAR:R\n**** \n*   *\n**** \n*  * \n*   *\n\n" +
                "CHAR:S\n ****\n*    \n *** \n    *\n**** \n\n" +
                "CHAR:T\n*****\n  *  \n  *  \n  *  \n  *  \n\n" +
                "CHAR:U\n*   *\n*   *\n*   *\n*   *\n *** \n\n" +
                "CHAR:V\n*   *\n*   *\n*   *\n * * \n  *  \n\n" +
                "CHAR:W\n*   *\n*   *\n* * *\n** **\n*   *\n\n" +
                "CHAR:X\n*   *\n * * \n  *  \n * * \n*   *\n\n" +
                "CHAR:Y\n*   *\n * * \n  *  \n  *  \n  *  \n\n" +
                "CHAR:Z\n*****\n   * \n  *  \n *   \n*****\n\n" +
                "CHAR: \n     \n     \n     \n     \n     \n";
    }

    private static String getFont7Content() {
        return "CHAR:A\n   *   \n  * *  \n *   * \n *   * \n ***** \n *   * \n *   * \n\n" +
                "CHAR:B\n ****  \n *   * \n *   * \n ****  \n *   * \n *   * \n ****  \n\n" +
                "CHAR:C\n  **** \n *     \n *     \n *     \n *     \n *     \n  **** \n\n" +
                "CHAR:D\n ****  \n *   * \n *   * \n *   * \n *   * \n *   * \n ****  \n\n" +
                "CHAR:E\n ***** \n *     \n *     \n ****  \n *     \n *     \n ***** \n\n" +
                "CHAR:H\n *   * \n *   * \n *   * \n ***** \n *   * \n *   * \n *   * \n\n" +
                "CHAR:I\n ***** \n   *   \n   *   \n   *   \n   *   \n   *   \n ***** \n\n" +
                "CHAR:L\n *     \n *     \n *     \n *     \n *     \n *     \n ***** \n\n" +
                "CHAR:O\n  ***  \n *   * \n *   * \n *   * \n *   * \n *   * \n  ***  \n\n" +
                "CHAR:W\n *   * \n *   * \n *   * \n * * * \n * * * \n ** ** \n *   * \n\n" +
                "CHAR: \n       \n       \n       \n       \n       \n       \n       \n";
    }
    //endregion

    //region Parsing Logic
    private static void loadTextFont(List<String> lines) {
        Character currentChar = null;
        List<String> charLines = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("CHAR:")) {
                if (currentChar != null && !charLines.isEmpty()) {
                    currentFont.put(currentChar, new ArrayList<>(charLines));
                    fontHeight = charLines.size();
                }

                String suffix = line.substring(5);
                if (suffix.trim().isEmpty()) {
                    currentChar = ' ';
                } else {
                    currentChar = suffix.trim().charAt(0);
                }

                charLines.clear();
            } else if (currentChar != null) {
                if (!line.isEmpty()) {
                    charLines.add(line);
                }
            }
        }
        if (currentChar != null && !charLines.isEmpty()) {
            currentFont.put(currentChar, new ArrayList<>(charLines));
        }
    }

    private static void loadJsonFont(List<String> lines) {
        String content = String.join("\n", lines);
        content = content.replaceAll("[{}\\[\\]\"]", "");

        String[] entries = content.split(",(?=[A-Za-z0-9А-Яа-я]:|\\s*[A-Za-z0-9А-Яа-я]:)");
        Character currentChar = null;
        List<String> charLines = new ArrayList<>();

        for (String entry : entries) {
            entry = entry.trim();
            if (entry.contains(":")) {
                String[] parts = entry.split(":", 2);
                if (parts.length == 2) {
                    if (currentChar != null && !charLines.isEmpty()) {
                        currentFont.put(currentChar, new ArrayList<>(charLines));
                        fontHeight = charLines.size();
                    }
                    currentChar = parts[0].trim().charAt(0);
                    charLines.clear();

                    String[] fontLines = parts[1].split("\\|");
                    for (String fontLine : fontLines) {
                        charLines.add(fontLine.trim());
                    }
                }
            }
        }
        if (currentChar != null && !charLines.isEmpty()) {
            currentFont.put(currentChar, new ArrayList<>(charLines));
        }
    }
    //endregion

    //region Printing Logic
    public static void print(String text, Color color, int[] position, String symbol) {
        if (currentFont.isEmpty()) {
            printSimple(text, color, position, symbol);
        } else {
            printWithFont(text, color, position, symbol);
        }
    }

    private static void printSimple(String text, Color color, int[] position, String symbol) {
        System.out.print(AnsiCodes.moveCursor(position[0], position[1]));
        System.out.print(color.getCode());
        System.out.print(text);
        System.out.print(Color.RESET.getCode());
    }

    private static void printWithFont(String text, Color color, int[] position, String symbol) {
        List<List<String>> charPatterns = new ArrayList<>();
        int maxWidth = 0;

        for (char c : text.toUpperCase().toCharArray()) {
            List<String> pattern = currentFont.get(c);
            if (pattern != null) {
                charPatterns.add(pattern);
                maxWidth = Math.max(maxWidth, getMaxLineWidth(pattern));
            } else {
                List<String> emptyPattern = new ArrayList<>();
                for (int i = 0; i < fontHeight; i++) {
                    emptyPattern.add("   ");
                }
                charPatterns.add(emptyPattern);
            }
        }

        for (int row = 0; row < fontHeight; row++) {
            System.out.print(AnsiCodes.moveCursor(position[0] + row, position[1]));
            System.out.print(color.getCode());

            for (List<String> pattern : charPatterns) {
                if (row < pattern.size()) {
                    String line = pattern.get(row);
                    line = line.replace('*', symbol.charAt(0));
                    System.out.print(line);
                    System.out.print(" ");
                }
            }
            System.out.print(Color.RESET.getCode());
            System.out.println();
        }
    }

    private static int getMaxLineWidth(List<String> lines) {
        return lines.stream().mapToInt(String::length).max().orElse(0);
    }
    //endregion

    //region Instance Methods
    public void print(String text) {
        print(text, this.color, this.position, this.symbol);
        if (!currentFont.isEmpty()) {
            this.position[0] += fontHeight + 1;
        } else {
            this.position[0]++;
        }
    }

    @Override
    public void close() {
        System.out.print(AnsiCodes.RESTORE_CURSOR);
        System.out.print(Color.RESET.getCode());
    }
    //endregion
}