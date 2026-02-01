package leo.task;

/** Represents an event task with start and end times. */
public class Event extends Task {
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
        String mark = isMarked() ? "X" : "O";
        return "E | " + mark + " | "
                + getTask() + " | " + start + " | " + end;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String mark = super.isMarked()? "X" : " ";
        return "[E]" + "[" + mark + "] " + super.getTask() + " [" + start + " --- " + end + "]";
    }
}
