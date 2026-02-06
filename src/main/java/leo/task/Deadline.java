package leo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/** Represents a task with a specific deadline date and time. */
public class Deadline extends Task {
    private DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private LocalDateTime deadline;

    /**
     * Creates a Deadline task with description, completion status, and due date/time.
     *
     * @param task The task description.
     * @param hasMarked Whether the task is marked as done.
     * @param deadline The deadline date and time.
     */
    public Deadline(String task, boolean hasMarked, LocalDateTime deadline) {
        super(task, hasMarked);
        this.deadline = deadline.truncatedTo(ChronoUnit.MINUTES);
    }

    /** {@inheritDoc} */
    @Override
    public String toSaveState() {
        String mark = isMarked() ? "X" : "O";
        return "D | " + mark + " | "
                + getTask() + " | " + deadline;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String mark = isMarked() ? "X" : " ";
        return "[D]" + "[" + mark + "] " + getTask() + " [Due: "
                + deadline.format(formatter) + "]";
    }
}

