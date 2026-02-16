package leo.task;

/** Represents a todo task without any date/time constraints. */
public class Todo extends Task {
    private static final String SAVE_FORMAT = "T | %s | %s";
    private static final String DISPLAY_FORMAT = "[T][%s] %s";
    /**
     * Creates a Todo task with description and completion status.
     *
     * @param task The todo description.
     * @param hasMarked Whether the todo is marked as done.
     */
    public Todo(String task, boolean hasMarked) {
        super(task, hasMarked);
    }

    /** {@inheritDoc} */
    @Override
    public String toSaveState() {
        String mark = isMarked() ? MARKED : UNMARKED;
        return String.format(SAVE_FORMAT, mark, getTask());

    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String mark = isMarked() ? MARKED : " ";
        return String.format(DISPLAY_FORMAT, mark, getTask());
    }
}
