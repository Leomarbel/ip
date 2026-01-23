import java.util.ArrayList;
import java.util.Scanner;

public class Leo {
    public static void main(String[] args) {
        boolean isExiting = false;

        ArrayList<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String logo = """
            ██╗     ███████╗ ██████╗
            ██║     ██╔════╝██╔═══██╗
            ██║     █████╗  ██║   ██║
            ██║     ██╔══╝  ██║   ██║
            ███████╗███████╗╚██████╔╝
            ╚══════╝╚══════╝ ╚═════╝
            """;


        PrintSep();
        System.out.println("Hello! I'm \n" + logo);
        System.out.println("What can I do for you? \n");
        PrintSep();

        while (!isExiting) {

            String input = scanner.nextLine();

            String command = input.trim().toLowerCase();

            switch (command) {
                case "bye":
                    isExiting = true;
                    break;

                case "add":
                    handleAdd(scanner, list);
                    break;

                case "list":
                    printList(list);
                    break;

                default:
                    System.out.println("You: " + input);
            }
        }

        PrintSep();
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void PrintSep() {
        String sep = "─────────────────✦─────────────────";
        System.out.println(sep);
    }

    private static void handleAdd(Scanner scanner, ArrayList<String> list) {
        System.out.println("Adding to list now (type 'stop' to finish, 'list' to view)");

        boolean stopListing = false;

        while (!stopListing) {
            String listing = scanner.nextLine();

            if (listing.equalsIgnoreCase("list")) {
                printList(list);
            } else if (listing.equalsIgnoreCase("stop")) {
                stopListing = true;
            } else {
                list.add(listing);
                System.out.println("Added: " + listing);
            }
        }
    }

    private static void printList(ArrayList<String> list) {
        if (list.isEmpty()) {
            System.out.println("(list is empty)");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

}