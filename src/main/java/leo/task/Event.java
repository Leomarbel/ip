package leo.task;

public class Event extends Task {
    private String start;
    private String end;
    public Event(String task, boolean hasMarked, String start, String end) {
        super(task, hasMarked);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toSaveState() {
        String mark = isMarked() ? "X" : "O";
        return "E | " + mark + " | "
                + getTask() + " | " + start + " | " + end;
    }

    @Override
    public String toString() {
        String mark = super.isMarked()? "X" : " ";
        return "[E]" + "[" + mark + "] " + super.getTask() + " [" + start + " --- " + end + "]";
    }
}
