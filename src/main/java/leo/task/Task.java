package leo.task;

/** Abstract base class for all types of tasks. */
public abstract class Task {
    protected boolean hasMarked;
    protected String task;

    /**
     * Creates a Task with description and completion status.
     *
     * @param task The task description.
     * @param hasMarked Whether the task is marked as done.
     */
    public Task (String task, boolean hasMarked) {
        this.task = task;
        this.hasMarked = hasMarked;
    }

    /** Marks the task as done. */
    public void mark(){
        this.hasMarked = true;
    }

    /** Unmarks the task (sets as not done). */
    public void unmark() {
        this.hasMarked = false;
    }

    /** Checks if task is marked as done. */
    public boolean isMarked() {
        return this.hasMarked;
    }

    /** Gets the task description. */
    public String getTask() {
        return this.task;
    }

    /** Converts task to string format for saving to file. */
    public abstract String toSaveState();

    @Override
    public String toString() {
        String mark = hasMarked ? "1" : "0";
        return "T | " + mark + " | " + getTask();
    }
}
