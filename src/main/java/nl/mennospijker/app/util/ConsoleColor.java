package nl.mennospijker.util;

public class ConsoleColor {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";

    public static String blackString(String text) {
        return ANSI_BLACK + text + ANSI_RESET;
    }

    public static String redString(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String greenString(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public static String yellowString(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    public static String blueString(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    public static String purpleString(String text) {
        return ANSI_PURPLE + text + ANSI_RESET;
    }

    public static String cyanString(String text) {
        return ANSI_CYAN + text + ANSI_RESET;
    }

    public static String whiteString(String text) {
        return ANSI_WHITE + text + ANSI_RESET;
    }

    /**
     * Backgrounds
     */

    public static String blackBackground(String text) {
        return BLACK_BACKGROUND + text + ANSI_RESET;
    }

    public static String redBackground(String text) {
        return RED_BACKGROUND + text + ANSI_RESET;
    }

    public static String greenBackground(String text) {
        return GREEN_BACKGROUND + text + ANSI_RESET;
    }

    public static String yellowBackground(String text) {
        return YELLOW_BACKGROUND + text + ANSI_RESET;
    }

    public static String blueBackground(String text) {
        return BLUE_BACKGROUND + text + ANSI_RESET;
    }

    public static String purpleBackground(String text) {
        return PURPLE_BACKGROUND + text + ANSI_RESET;
    }

    public static String cyanBackground(String text) {
        return CYAN_BACKGROUND + text + ANSI_RESET;
    }

    public static String whiteBackground(String text) {
        return WHITE_BACKGROUND + text + ANSI_RESET;
    }
}
