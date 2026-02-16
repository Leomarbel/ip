package leo.task;

/** Represents an event task with start and end times. */
public class Event extends Task {
    private static final String SAVE_FORMAT = "E | %s | %s | %s | %s";
    private static final String DISPLAY_FORMAT = "[E][%s] %s [%s --- %s]";
    private String start;
    private String end;

    /**
     * Creates an Event task with description, completion status, and time range.
     *
     * @param task The event description.
     * @param hasMarked Whether the event is marked as done.
     * @param start The start time of the event.
     * @param end The end time of the event.
     */
    public Event(String task, boolean hasMarked, String start, String end) {
        super(task, hasMarked);
        this.start = start;
        this.end = end;
    }

    /** {@inheritDoc} */
    @Override
    public String toSaveState() {
        String mark = isMarked() ? MARKED : UNMARKED;
        return String.format(SAVE_FORMAT, mark, getTask(), start, end);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String mark = super.isMarked() ? MARKED : " ";
        return String.format(DISPLAY_FORMAT, mark, getTask(), start, end);
    }
}
