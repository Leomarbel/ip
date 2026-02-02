package leo;

import java.util.Scanner;

/** Handles all user interface interactions including input and output. */
public class Ui {
    private final Scanner sc;

    /** Creates a Ui instance with scanner for user input. */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /** Displays the welcome message and Leo logo. */
    public void showWelcome() {
        String logo = """
             __        _______   _________
            |  |      |   ____| |   ___   |
            |  |      |  |__    |  |   |  |
            |  |      |   __|   |  |   |  |
            |  |____  |  |____  |  |___|  |
            |_______| |_______| |_________|
           """;


        printSep();
        System.out.println("Hello! I'm\n" + logo);
        System.out.println("What can I do for you?\n");
        printSep();
    }

    /** Prints a separator line for visual clarity. */
    public void printSep() {
        String sep = "____________________________________________________________";
        System.out.println(sep);
    }

    /**
     * Displays Leo's reply to the user.
     *
     * @param text The message to display.
     */
    public void showLeoReply(String text) {
        printSep();
        System.out.println("Leo:\n" + text);
        printSep();
    }

    /**
     * Displays an error message to the user.
     *
     * @param text The error message to display.
     */
    public void showError(String text) {
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
