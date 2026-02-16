package leo;

import java.util.Scanner;

/** Handles all user interface interactions including input and output. */
public class Ui {
    static final String LOGO = """
             __        _______   _________
            |  |      |   ____| |   ___   |
            |  |      |  |__    |  |   |  |
            |  |      |   __|   |  |   |  |
            |  |____  |  |____  |  |___|  |
            |_______| |_______| |_________|
           """;
    private final Scanner sc;
    /** Creates an Ui instance with scanner for user input. */
    public Ui() {
        sc = new Scanner(System.in);
    }
    /** Displays the welcome message and Leo logo. */
    public void showWelcome() {
        printSep();
        System.out.println("Hello! I'm\n" + LOGO);
        System.out.println("What can I do for you?\n");
        printSep();
    }

    /** Prints a separator line for visual clarity. */
    private void printSep() {
        String sep = "____________________________________________________________";
        System.out.println(sep);
    }

    /**
     * Displays Leo's reply to the user.
     *
     * @param text The message to display.
     */
    public String showLeoReply(String text) {
        assert text != null : "Missing showLeoReply text";
        printSep();
        System.out.println("Leo:\n" + text);
        printSep();
        return "Leo:\n" + text;
    }

    /**
     * Displays an error message to the user.
     *
     * @param text The error message to display.
     */
    public void showError(String text) {
        assert text != null : "Missing showError text";
        showLeoReply("Error!!! " + text);
    }

    /**
     * Reads a command from the user via standard input.
     *
     * @return The user's input command as a string.
     */
    public String readCommand() {
        return sc.nextLine();
    }
}
