package leo.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Deadline;

/** Command to add a deadline task with specific due date/time. */
public class DeadlineCommand extends Command {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = createDateTimeFormatter();
    private String taskDesc;

    /**
     * Creates a DeadlineCommand with task description.
     *
     * @param taskDesc The task description including "/by" deadline.
     */
    public DeadlineCommand(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * @inheritDoc
     *      Check for correct format of deadline (task) /by (time format)
     *      Add the Deadline Task into the Tasklist
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        //Validate taskDesc to check if correct number of parts and split
        //taskDesc to appropriate parts
        String[] deadlineParts = validateAndSplitTask();

        //Parse and create deadline task
        Deadline d = createDeadlineTask(deadlineParts);
        tasks.addTask(d);

        saveTask(tasks, ui, storage, d);
        return ui.showLeoReply(d + "\n Current Tasks: " + tasks.size());
    }

    private String[] validateAndSplitTask() throws LeoException {
        String[] deadlineParts = taskDesc.split("/by", 2);

        if (deadlineParts.length < 2) {
            throw new LeoException("The description of a deadline must have /by.");
        }
        if (deadlineParts[1].isEmpty()) {
            throw new LeoException("Deadline must have an input!");
        }
        return deadlineParts;
    }

    private Deadline createDeadlineTask(String[] deadlineParts) throws LeoException {
        try {
            String taskName = deadlineParts[0].trim();
            String deadlineDateTime = deadlineParts[1].trim();
            LocalDateTime parsedDeadline = LocalDateTime.parse(
                    deadlineDateTime,
                    DATE_TIME_FORMATTER
            );
            return new Deadline(taskName, false, parsedDeadline);
        } catch (DateTimeParseException e) {
            throw new LeoException(
                    "Your Date & Time format is wrong!" + "\n" + getAcceptedFormatsMessage());
        }
    }

    private void saveTask(TaskList tasks, Ui ui, Storage storage, Deadline d) throws LeoException {
        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            throw new LeoException("Unable to save tasks: " + e.getMessage());
        }
    }

    /** Creates a formatter that supports multiple date/time formats. */
    private static DateTimeFormatter createDateTimeFormatter() {
        // Supporting multiple formats to accommodate user preferences
        return new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"))
                .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy HHmm"))
                .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy HH:mm"))
                .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy HHmm"))
                .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))
                .toFormatter();
    }
    /** Returns a user-friendly message listing accepted date formats. */
    private String getAcceptedFormatsMessage() {
        return """
               Accepted formats (examples):
               - 2/12/2019 1800 or 2/12/2019 18:00
               - 02/12/2019 1800 or 02/12/2019 18:00
               - 12/2/2019 1800 or 12/2/2019 18:00
               - 12/02/2019 1800 or 12/02/2019 18:00
               """;
    }
}
