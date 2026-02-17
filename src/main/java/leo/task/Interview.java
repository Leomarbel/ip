package leo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a deadline representing an upcoming interview */
public class Interview extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    private static final String SAVE_FORMAT = "I | %s | %s | %s";
    private static final String DISPLAY_FORMAT = "[I][%s] %s [Interview Date: %s]";
    private LocalDateTime interviewTime;
    /**
     * Creates an Interview task with company description, completion status, and due date/time.
     *
     * @param company      The company description.
     * @param hasMarked Whether the task is marked as done.
     * @param interviewTime  The deadline date and time.
     */
    public Interview(String company, boolean hasMarked, LocalDateTime interviewTime) {
        super(company, hasMarked);
        this.interviewTime = interviewTime;
    }
    public LocalDateTime getInterviewTime() {
        return this.interviewTime;
    }

    /** {@inheritDoc} */
    @Override
    public String toSaveState() {
        String mark = isMarked() ? MARKED : UNMARKED;
        return String.format(SAVE_FORMAT, mark, getTask(), interviewTime);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String mark = isMarked() ? MARKED : " ";
        return String.format(DISPLAY_FORMAT, mark, getTask(),
                interviewTime.format(DISPLAY_FORMATTER));
    }
}
