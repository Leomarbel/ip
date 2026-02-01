package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Deadline;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class DeadlineCommand extends Command {
    private  String taskDesc;
    private  DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"))   // "2/12/2019 1800"
            .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"))  // "2/12/2019 18:00"

            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"))  // "02/12/2019 1800"
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) // "02/12/2019 18:00"

            .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy HHmm"))    // "12/2/2019 1800"
            .appendOptional(DateTimeFormatter.ofPattern("M/d/yyyy HH:mm"))   // "12/2/2019 18:00"

            .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy HHmm"))  // "12/02/2019 1800"
            .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")) // "12/02/2019 18:00"
            .toFormatter();

    public DeadlineCommand(String taskDesc) {
        this.taskDesc = taskDesc;


    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        String[] deadlineParts = taskDesc.split("/by", 2);

        if (deadlineParts.length < 2) {
            throw new LeoException("The description of a deadline must have /by.");
        }
        if (deadlineParts[1].isEmpty()) {
            throw new LeoException("Deadline must have an input!");
        }
        try {
            LocalDateTime parsedDeadline = LocalDateTime.parse(deadlineParts[1].trim(), formatter);
            Deadline d = new Deadline(deadlineParts[0].trim(), false, parsedDeadline);
            tasks.addTask(d);
            ui.showLeoReply(d + "\n Current Tasks: " + tasks.size());
        } catch (DateTimeParseException e) {
            String format = """ 
                            Acceptable formats:
                            d/M/yyyy HHmm
                            dd/MM/yyyy HHmm
                            M/d/yyyy HHmm
                            MM/dd/yyyy HHmm
                            """;

            throw new LeoException("Your Date & Time format is wrong!" + "\n" + format);
        }

        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            ui.showError("Unable to save tasks: " + e.getMessage());
        }


    }


}
