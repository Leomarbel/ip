package leo.task;

public abstract class Task {
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
