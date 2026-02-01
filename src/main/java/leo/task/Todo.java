package leo.task;

public class Todo extends Task {
    public Todo(String task, boolean hasMarked) {
        super(task, hasMarked);
    }

    @Override
    public String toSaveState() {
        String mark = isMarked()? "X" : "O";
        return "T | " + mark + " | " + getTask();

    }

    @Override
    public String toString() {
        String mark = isMarked()? "X" : " ";
        return "[T]" + "[" + mark + "] " + getTask();
    }
}
