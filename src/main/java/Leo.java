import java.util.ArrayList;
import java.util.Scanner;

public class Leo {
    public static void main(String[] args) {
        boolean isExiting = false;

        ArrayList<Task> list = new ArrayList<>();
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


        LeoReply("Bye. Hope to see you again soon!");
    }

    private static void PrintSep() {
        String sep = "─────────────────✦─────────────────";
        System.out.println(sep);
    }

    private static void handleAdd(Scanner scanner, ArrayList<Task> list) {
        LeoReply("Adding to list now (type 'stop' to finish, 'list' to view)");

        boolean stopListing = false;

        while (!stopListing) {
            String listing = scanner.nextLine();
            String[] parts = listing.trim().split("\\s+", 2);
            String command = parts[0];

            switch (command) {
                case "list":
                    printList((list));
                    break;
                case "stop":
                    stopListing = true;
                    break;
                case "mark":
                    int index = Integer.parseInt(parts[1]) - 1;
                    markTask(index, list);
                    break;
                case "unmark":
                    int index1 = Integer.parseInt(parts[1]) - 1;
                    unmarkTask(index1, list);
                    break;
                default:
                    list.add(new Task(listing, false));
                    LeoReply("Added: " + listing);

            }

            /*if (listing.equalsIgnoreCase("list")) {
                printList(list);
            } else if (listing.equalsIgnoreCase("stop")) {
                stopListing = true;
            } else if (listing.startsWith("mark ")) {
                int index = Integer.parseInt(listing.substring(5)) - 1;
                markTask(index, list);
            } else if (listing.startsWith("unmark ")) {
                int index = Integer.parseInt(listing.substring(7)) - 1;
                unmarkTask(index, list);
            } else {
                list.add(new Task(listing, false));
                LeoReply("Added: " + listing);
            } */
        }
    }

    private static void printList(ArrayList<Task> list) {
        if (list.isEmpty()) {
            LeoReply("(list is empty)");
            return;
        }
        StringBuilder listText = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            listText.append((i + 1))
                    .append(". ")
                    .append(list.get(i))
                    .append("\n");

        }
        LeoReply(listText.toString());
    }

    public static void LeoReply(String text) {
        PrintSep();

        System.out.println("Leo: \n" + text);
        PrintSep();
    }


    public static class Task {
        private boolean hasMarked;
        private final String task;

        public Task (String task, boolean hasMarked) {
            this.task = task;
            this.hasMarked = hasMarked;
        }

        @Override
        public String toString() {
            String mark = hasMarked? "X" : " ";
            return "[" + mark + "] " + task;
        }
    }

    private static void markTask(int index, ArrayList<Task> list) {
        if (index > list.size() - 1 || index < 0) {
            LeoReply("Error, Index outside of list bounds :(");
            return;
        }

        Task t = list.get(index);
        t.hasMarked = true;
        LeoReply(t.toString());
    }

    private static void unmarkTask(int index, ArrayList<Task> list) {
        if (index > list.size() - 1 || index < 0) {
            LeoReply("Error, Index outside of list bounds :(");
            return;
        }

        Task t = list.get(index);
        t.hasMarked = false;
        LeoReply(t.toString());
    }


}