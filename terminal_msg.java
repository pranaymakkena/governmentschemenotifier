import com.github.tomaslanger.chalk.Chalk;

public class TerminalColor {
    private Chalk chalk;

    public TerminalColor(String style) {
        switch (style) {
            case "BOLD":
                chalk = Chalk.bold();
                break;
            // Add other styles as needed
            default:
                chalk = Chalk.on();
        }
    }

    public void cprint(String message, String fgColor, String bgColor) {
        Chalk coloredMessage = chalk;
        if (fgColor != null) {
            coloredMessage = coloredMessage.foreground(getColor(fgColor));
        }
        if (bgColor != null) {
            coloredMessage = coloredMessage.background(getColor(bgColor));
        }
        coloredMessage.println(message);
    }

    private Chalk.Color getColor(String colorName) {
        switch (colorName) {
            case "BLACK":
                return Chalk.Color.BLACK;
            case "RED":
                return Chalk.Color.RED;
            case "GREEN":
                return Chalk.Color.GREEN;
            case "YELLOW":
                return Chalk.Color.YELLOW;
            case "BLUE":
                return Chalk.Color.BLUE;
            case "MAGENTA":
                return Chalk.Color.MAGENTA;
            case "CYAN":
                return Chalk.Color.CYAN;
            case "WHITE":
                return Chalk.Color.WHITE;
            default:
                return null;
        }
    }
}

public class TerminalMsg {
    private static TerminalColor colorize;

    static {
        colorize = new TerminalColor("BOLD");
    }

    public static void error(String message) {
        colorize.cprint("[X] " + message, "YELLOW", "RED");
    }

    public static void warn(String message) {
        colorize.cprint("[!] " + message, "BLACK", "YELLOW");
    }

    public static void success(String message) {
        colorize.cprint("[✓] " + message, "GREEN", "BLACK");
    }

    public static void info(String message) {
        colorize.cprint("[*] " + message, "YELLOW", "BLACK");
    }
}
