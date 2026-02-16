package OOP.Lab2;

public enum Color {

    //region Colors
    BLACK("\033[0;30m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    MAGENTA("\033[0;35m"),
    CYAN("\033[0;36m"),
    WHITE("\033[0;37m"),

    BRIGHT_BLACK("\033[0;90m"),
    BRIGHT_RED("\033[0;91m"),
    BRIGHT_GREEN("\033[0;92m"),
    BRIGHT_YELLOW("\033[0;93m"),
    BRIGHT_BLUE("\033[0;94m"),
    BRIGHT_MAGENTA("\033[0;95m"),
    BRIGHT_CYAN("\033[0;96m"),
    BRIGHT_WHITE("\033[0;97m"),

    RESET("\033[0m");
    //endregion

    //region Fields & Constructor
    private final String code;

    Color(String code) {
        this.code = code;
    }
    //endregion

    //region Methods
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
    //endregion
}