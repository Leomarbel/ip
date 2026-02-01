package leo;

import java.util.Scanner;

public class Ui {
    private final Scanner sc;
    static final String LOGO = """
             __        _______   _________
            |  |      |   ____| |   ___   |
            |  |      |  |__    |  |   |  |
            |  |      |   __|   |  |   |  |
            |  |____  |  |____  |  |___|  |
            |_______| |_______| |_________|
           """;

    public Ui() {
        sc = new Scanner(System.in);
    }

    public void showWelcome() {



        printSep();
        System.out.println("Hello! I'm\n" + LOGO);
        System.out.println("What can I do for you?\n");
        printSep();
    }

    private void printSep() {
        String sep = "____________________________________________________________";
        System.out.println(sep);
    }

    public void showLeoReply(String text) {
        printSep();
        System.out.println("Leo:\n" + text);
        printSep();
    }
    public void showError(String text) {
        showLeoReply("Error!!! " + text);
    }

    public String readCommand() {
        return sc.nextLine();
    }
}
