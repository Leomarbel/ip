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
import leo.task.Interview;

/** Command to add an interview task with specific due date/time. */
public class InterviewCommand extends Command {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = createDateTimeFormatter();
    private String taskDesc;

    /**
     * Creates an Interview Command with task description.
     *
     * @param taskDesc The task description including "/by" interview date.
     */
    public InterviewCommand(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * @inheritDoc
     *      Check for correct format of company (task) /by (time format)
     *      Add the Interview Task into the Tasklist
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        //Validate taskDesc to check if correct number of parts and split
        //taskDesc to appropriate parts
        String[] interviewParts = validateAndSplitTask();

        //Parse and create interview task
        Interview i = createInterviewTask(interviewParts);
        tasks.addInterviewDate(i);

        saveTask(tasks, storage);
        return ui.showLeoReply(i + "\n Current Tasks: " + tasks.size());
    }

    private String[] validateAndSplitTask() throws LeoException {
        String[] interviewParts = taskDesc.split("/by", 2);

        if (interviewParts.length < 2) {
            throw new LeoException("The description of an interview must have /by.");
        }
        if (interviewParts[1].isEmpty()) {
            throw new LeoException("Please add a company name.");
        }
        return interviewParts;
    }

    private Interview createInterviewTask(String[] interviewParts) throws LeoException {
        try {
            String taskName = interviewParts[0].trim();
            String interviewDateTime = interviewParts[1].trim();
            LocalDateTime parsedInterview = LocalDateTime.parse(
                    interviewDateTime,
                    DATE_TIME_FORMATTER
            );
            return new Interview(taskName, false, parsedInterview);
        } catch (DateTimeParseException e) {
            throw new LeoException(
                    "Your Date & Time format is wrong!" + "\n" + getAcceptedFormatsMessage());
        }
    }

    private void saveTask(TaskList tasks, Storage storage) throws LeoException {
        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            throw new LeoException("Unable to save tasks: " + e.getMessage());
        }
    }

    /** Creates a formatter that supports multiple date/time formats. */
    private static DateTimeFormatter createDateTimeFormatter() {
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

