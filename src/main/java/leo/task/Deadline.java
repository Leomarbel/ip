package leo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/** Represents a task with a specific deadline date and time. */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final String SAVE_FORMAT = "D | %s | %s | %s";
    private static final String DISPLAY_FORMAT = "[D][%s] %s [Due: %s]";
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
        String mark = isMarked() ? MARKED : UNMARKED;
        return String.format(SAVE_FORMAT, mark, getTask(), deadline);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String mark = isMarked() ? MARKED : " ";
        return String.format(DISPLAY_FORMAT, mark, getTask(),
                deadline.format(DISPLAY_FORMATTER));
    }
}

