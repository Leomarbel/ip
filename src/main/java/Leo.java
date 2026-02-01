
import java.io.IOException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Leo {
    enum Command {
        BYE, ADD, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, STOP, DELETE, UNKNOWN
    }

    public static void main(String[] args) {
        boolean isExiting = false;

        Scanner scanner = new Scanner(System.in);
        String logo = """
             __        _______   _________
            |  |      |   ____| |   ___   |
            |  |      |  |__    |  |   |  |
            |  |      |   __|   |  |   |  |
            |  |____  |  |____  |  |___|  |
            |_______| |_______| |_________|
           """;


        PrintSep();
        System.out.println("Hello! I'm\n" + logo);
        System.out.println("What can I do for you?\n");
        PrintSep();

        ArrayList<Task> lists = loadTasks();
        //ArrayList<Task> lists = new ArrayList<>();

        while (!isExiting) {

            String input = scanner.nextLine();

            String command = input.trim().toLowerCase();

            switch (command) {
                case "bye":
                    isExiting = true;
                    break;

                case "add":
                    HandleAdd(scanner, lists);

                    break;

                case "list":
                    PrintList(lists);
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
            try {
                String listing = scanner.nextLine();
                String[] parts = listing.trim().split("\\s+", 2);
                Command command = ParseCommand(parts[0]);

                switch (command) {
                    case LIST, STOP:
                        break;
                    default:
                        if (parts.length < 2) {
                            throw new LeoException("Error!!! Missing description or index.");
                        }
                }


                switch (command) {
                    case LIST:
                        PrintList((list));
                        break;

                    case STOP:
                        stopListing = true;
                        break;

                    case MARK:
                        try {
                            int index = Integer.parseInt(parts[1]) - 1;
                            MarkTask(index, list);
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            throw new LeoException("Error!!! Please provide a valid task number.");
                        }
                        break;

                    case UNMARK:
                        try {
                            int index = Integer.parseInt(parts[1]) - 1;
                            UnmarkTask(index, list);
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            throw new LeoException("Error!!! Please provide a valid task number.");
                        }
                        break;

                    case TODO:
                        TodoTask(parts[1], list);
                        break;

                    case DEADLINE:
                        String[] deadlineParts = parts[1].split("/by", 2);

                        if (deadlineParts.length < 2) {
                            throw new LeoException("Error!!! The description of a deadline must have /by.");
                        }
                        if (deadlineParts[1].isEmpty()) {
                            throw new LeoException("Error!!! Deadline must have an input!");
                        }

                        DeadlineTask(deadlineParts[0].trim(),
                                list,
                                deadlineParts[1].trim());
                        break;

                    case EVENT:
                        String[] eventDuration = getStrings(parts);
                        EventTask(parts[0],
                                list,
                                eventDuration[0],
                                eventDuration[1]);
                        break;

                    case DELETE:
                        try {
                            int index = Integer.parseInt(parts[1]) - 1;
                            DeleteTask(index, list);
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            throw new LeoException("Error!!! Please provide a valid task number.");
                        }
                        break;
                    case UNKNOWN:


                    default:

                }
            } catch (LeoException e) {
                LeoReply(e.getMessage());
            }

        }
    }
    
    // region Functions

    private static String[] getStrings(String[] parts) throws LeoException {
        String[] eventParts = parts[1].split("/from ", 2);

        if (eventParts.length < 2) {
            throw new LeoException("Error!!! The description of an event must have /from.");
        }

        String[] eventDuration = eventParts[1].split("/to", 2);
        if (eventDuration.length < 2) {
            throw new LeoException("Error!!! The description of an event must have /to.");

        }
        return eventDuration;
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

    private static Command ParseCommand(String input) {
        try {
            return Command.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }


    public static void LeoReply(String text) {
        PrintSep();
        System.out.println("Leo:\n" + text);
        PrintSep();
    }



    // endregion

    // region Classes
    public abstract static class Task {
        protected boolean hasMarked;
        protected final String task;
        
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

        protected boolean isMarked() {
            return this.hasMarked;
        }

        protected String getTask() {
            return this.task;
        }
        public abstract String toSaveState();

        @Override
        public String toString() {
            String mark = hasMarked ? "1" : "0";
            return "T | " + mark + " | " + getTask();
        }
    }

    public static class Todo extends Task {

        public Todo(String task, boolean hasMarked) {
            super(task, hasMarked);
        }

        @Override
        public String toSaveState() {
            String mark = isMarked()? "X" : "O";
            return "T | " + mark + " | " + getTask();

        }

        @Override
        public String toString() {
            String mark = isMarked()? "X" : " ";
            return "[T]" + "[" + mark + "] " + getTask();
        }
    }

    public static class Deadline extends Task {
        private final LocalDateTime deadline;
        public Deadline(String task, boolean hasMarked, LocalDateTime deadline) {
            super(task, hasMarked);
            this.deadline = deadline.truncatedTo(ChronoUnit.MINUTES);
        }

        @Override
        public String toSaveState() {
            String mark = isMarked() ? "X" : "O";
            return "D | " + mark + " | "
                    + getTask() + " | " + deadline;
        }

        @Override
        public String toString() {
            String mark = isMarked()? "X" : " ";
            return "[D]" + "[" + mark + "] " + getTask() + " [Due: "
                    + deadline.getMonth() + " "
                    + deadline.getDayOfMonth() + " "
                    + deadline.getYear() + "]";
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
        public String toSaveState() {
            String mark = isMarked() ? "X" : "O";
            return "E | " + mark + " | "
                    + getTask() + " | " + start + " | " + end;
        }

        @Override
        public String toString() {
            String mark = super.isMarked()? "X" : " ";
            return "[E]" + "[" + mark + "] " + super.getTask() + " [" + start + " --- " + end + "]";
        }
    }
    // endregion

    // region Class Callers

    public static void DeleteTask(int index, ArrayList<Task> list) throws LeoException {
        if (index > list.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }
        Task t = list.get(index);
        list.remove(index);
        saveTasks(list);
        LeoReply("Task Removed: " + t.toString() + "\n Tasks Left: " + list.size() );


    }
    private static void MarkTask(int index, ArrayList<Task> list) throws LeoException {
        if (index > list.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }

        Task t = list.get(index);
        t.mark();
        saveTasks(list);
        LeoReply(t.toString());
    }

    private static void UnmarkTask(int index, ArrayList<Task> list) throws LeoException{
        if (index > list.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }
        saveTasks(list);

        Task t = list.get(index);
        t.unmark();
        LeoReply(t.toString());
    }

    private static void TodoTask(String task, ArrayList<Task> list) {
        Todo t = new Todo(task, false);
        list.add(t);
        saveTasks(list);
        LeoReply(t + "\n Current Tasks: " + list.size() );
    }

    private static void DeadlineTask(String task, ArrayList<Task> list, String deadline) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"))   // "2/12/2019 1800"
                .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"))  // "2/12/2019 18:00"

                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"))  // "02/12/2019 1800"
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) // "02/12/2019 18:00"

                .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy HHmm"))    // "12/2/2019 1800"
                .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy HH:mm"))   // "12/2/2019 18:00"

                .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy HHmm"))  // "12/02/2019 1800"
                .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")) // "12/02/2019 18:00"
                .toFormatter();
        try {
            LocalDateTime parsedDeadline = LocalDateTime.parse(deadline,
                    formatter);
            Deadline t = new Deadline(task, false, parsedDeadline);
            list.add(t);
            saveTasks(list);
            LeoReply(t + "\n Current Tasks: " + list.size() );
        } catch (DateTimeParseException e) {
            String format = """ 
                            Acceptable formats:
                            d/M/yyyy HHmm
                            dd/MM/yyyy HHmm
                            M/d/yyyy HHmm
                            MM/dd/yyyy HHmm
                            """;

            LeoReply("Error!!! Your Date & Time format is wrong!" + "\n" + format);
        }

    }

    private static void EventTask(String task, ArrayList<Task> list, String start, String end) {
            Event t = new Event(task, false, start, end);
        list.add(t);
        saveTasks(list);
        LeoReply(t + "\n Current Tasks: " + list.size() );
    }

    // endregion

    public static class LeoException extends Exception {
        public LeoException(String msg) {
            super(msg);
        }
    }
    private static void saveTasks(ArrayList<Task> list) {

        ArrayList<String> data = new ArrayList<>();

        for (Task t : list) {
            data.add(t.toSaveState());
        }

        try {
            Storage.save(data);
        } catch (IOException e) {
            LeoReply("Save Error: " + e.getMessage());

        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            tasks = Storage.load();
        } catch (IOException e) {
            LeoReply(( "Load Error: " + e.getMessage()));
        }
        return tasks;
    }
}