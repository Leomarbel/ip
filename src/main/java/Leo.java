import java.util.Scanner;

public class Leo {
    public static void main(String[] args) {
        boolean isExiting = false;
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
            if (input.equalsIgnoreCase("bye")
            ) isExiting = true;
        }

        PrintSep();
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void PrintSep() {
        String sep = "─────────────────✦─────────────────";
        System.out.println(sep);
    }
}