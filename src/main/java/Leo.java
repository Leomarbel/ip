import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Leo {
    public static void main(String[] args) {
        boolean isExiting = false;

        ArrayList<Task> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String logo = """
             _      ______  ____ 
            | |    |  ____|/ __ \\
            | |    | |__  | |  | |
            | |    |  __| | |  | |
            | |____| |____| |__| |
            |______|______|\\____/
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
                    HandleAdd(scanner, list);
                    break;

                case "list":
                    PrintList(list);
                    break;

                default:
                    System.out.println("You: " + input);
            }
        }


        LeoReply("Bye. Hope to see you again soon!");
    }

    private static void PrintSep() {
        String sep = "____________________________________________________________";
        System.out.println(sep);
    }

    private static void HandleAdd(Scanner scanner, ArrayList<Task> list) {
        LeoReply("Adding to list now (type 'stop' to finish, 'list' to view)");

        boolean stopListing = false;

        while (!stopListing) {
            String listing = scanner.nextLine();
            String[] parts = listing.trim().split("\\s+", 2);
            String command = parts[0];

            switch (command) {
                case "list":
                    PrintList((list));
                    break;

                case "stop":
                    stopListing = true;
                    break;

                case "mark":
                    int index = Integer.parseInt(parts[1]) - 1;
                    MarkTask(index, list);
                    break;

                case "unmark":
                    int index1 = Integer.parseInt(parts[1]) - 1;
                    UnmarkTask(index1, list);
                    break;

                case "todo":
                    TodoTask(parts[1], list);
                    break;

                case "deadline":
                    String[] deadlineParts = parts[1].split("/by", 2);

                    if (deadlineParts.length < 2) {
                        LeoReply("Error!!! The description of a deadline must have /by.");
                        return;
                    }

                    DeadlineTask(deadlineParts[0].trim(),
                                 list,
                                 deadlineParts[1].trim());
                    break;

                case "event":
                    String[] eventParts = parts[1].split("/from ", 2);

                    if (eventParts.length < 2) {
                        LeoReply("Error!!! The description of an event must have /from.");
                        return;
                    }

                    String[] eventDuration = eventParts[1].split("/to", 2);
                    if (eventDuration.length < 2) {
                        LeoReply("Error!!! The description of an event must have /to.");
                        return;
                    }
                    EventTask(parts[0],
                              list,
                              eventDuration[0],
                              eventDuration[1]);
                    break;

                default:
                    list.add(new Task(listing, false));
                    LeoReply("Added: " + listing);

            }

        }
    }

    private static void PrintList(ArrayList<Task> list) {
        if (list.isEmpty()) {
            LeoReply("(List is empty)");
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

    // region Classes
    public static class Task {
        private boolean hasMarked;
        private final String task;

        public Task (String task, boolean hasMarked) {
            this.task = task;
            this.hasMarked = hasMarked;
        }

        public void mark(){
            this.hasMarked = true;
        }

        public void unmark() {
            this.hasMarked = false;
        }

        @Override
        public String toString() {
            String mark = hasMarked? "X" : " ";
            return "[" + mark + "] " + task;
        }
    }

    public static class Todo extends Task {

        public Todo(String task, boolean hasMarked) {
            super(task, hasMarked);
        }

        @Override
        public String toString() {
            String mark = super.hasMarked? "X" : " ";
            return "[T]" + "[" + mark + "] " + super.task;
        }
    }

    public static class Deadline extends Task {
        private final String deadline;
        public Deadline(String task, boolean hasMarked, String deadline) {
            super(task, hasMarked);
            this.deadline = deadline;
        }

        @Override
        public String toString() {
            String mark = super.hasMarked? "X" : " ";
            return "[D]" + "[" + mark + "] " + super.task + " [Due: " + deadline + "]";
        }
    }

    public static class Event extends Task {
        private String start;
        private String end;
        public Event(String task, boolean hasMarked, String start, String end) {
            super(task, hasMarked);
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            String mark = super.hasMarked? "X" : " ";
            return "[E]" + "[" + mark + "] " + super.task + " [" + start + " --- " + end + "]";
        }
    }
    // endregion

    private static void MarkTask(int index, ArrayList<Task> list) {
        if (index > list.size() - 1 || index < 0) {
            LeoReply("Error!!! Index outside of list bounds :(");
            return;
        }

        Task t = list.get(index);
        t.mark();
        LeoReply(t.toString());
    }

    private static void UnmarkTask(int index, ArrayList<Task> list) {
        if (index > list.size() - 1 || index < 0) {
            LeoReply("Error!!! Index outside of list bounds :(");
            return;
        }

        Task t = list.get(index);
        t.unmark();
        LeoReply(t.toString());
    }

    private static void TodoTask(String task, ArrayList<Task> list) {
        Todo t = new Todo(task, false);
        list.add(t);
        LeoReply(t.toString() + "\n Current Tasks: " + list.size() );
    }

    private static void DeadlineTask(String task, ArrayList<Task> list, String deadline) {
        Deadline t = new Deadline(task, false, deadline);
        list.add(t);
        LeoReply(t.toString() + "\n Current Tasks: " + list.size() );
    }

    private static void EventTask(String task, ArrayList<Task> list, String start, String end) {
            Event t = new Event(task, false, start, end);
        list.add(t);
        LeoReply(t.toString() + "\n Current Tasks: " + list.size() );
    }


}