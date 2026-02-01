package leo.task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private LocalDateTime deadline;
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
                + deadline.format(formatter) + "]";
    }
}

