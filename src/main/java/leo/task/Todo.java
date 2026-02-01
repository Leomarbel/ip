package leo.task;

/** Represents a todo task without any date/time constraints. */
public class Todo extends Task {

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
        String mark = isMarked()? "X" : "O";
        return "T | " + mark + " | " + getTask();

    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String mark = isMarked()? "X" : " ";
        return "[T]" + "[" + mark + "] " + getTask();
    }
}
