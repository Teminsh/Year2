package OOP.Lab2;

public class AnsiCodes {

    //region Constants
    public static final String CURSOR_HOME = "\033[H";
    public static final String CLEAR_SCREEN = "\033[2J";
    public static final String CLEAR_LINE = "\033[2K";
    public static final String SAVE_CURSOR = "\033[s";
    public static final String RESTORE_CURSOR = "\033[u";
    public static final String HIDE_CURSOR = "\033[?25l";
    public static final String SHOW_CURSOR = "\033[?25h";
    public static final String RESET = "\033[0m";
    //endregion

    //region Methods
    public static String moveCursor(int row, int col) {
        return String.format("\033[%d;%dH", row, col);
    }
    //endregion
}