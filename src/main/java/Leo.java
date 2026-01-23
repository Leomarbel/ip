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

            System.out.println("You: " + input);
            if (input.equalsIgnoreCase("bye")) isExiting = true;
            if (input.equalsIgnoreCase("add")) {
                System.out.println("Adding to list now");

                boolean stopListing = false;
                while (!stopListing) {
                    String listing = scanner.nextLine();



                    if (listing.equalsIgnoreCase("list")) {
                        for (int i = 0; i < list.size(); i++ ) {
                            System.out.println(i + 1 + ". " + list.get(i));
                        }
                    } else if (listing.equalsIgnoreCase("stop")) stopListing = true;
                    else {
                        list.add(listing);
                        System.out.println("Adding: " + listing);
                    }

                }
            }
        }

        PrintSep();
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void PrintSep() {
        String sep = "─────────────────✦─────────────────";
        System.out.println(sep);
    }
}