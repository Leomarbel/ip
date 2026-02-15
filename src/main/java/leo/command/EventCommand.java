package leo.command;

import java.io.IOException;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Event;


/** Command to add an event task with start and end times. */
public class EventCommand extends Command {
    private String taskDesc;

    /**
     * Creates an EventCommand with task description.
     *
     * @param taskDesc The task description including "/from" and "/to" times.
     */
    public EventCommand(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * @inheritDoc
     *      Extracts event start and end times from description string.
     *      Executes event command by parsing description, creating event,
     *      adding to task list, saving, and displaying result.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        //Validate taskDesc to check if correct number of parts and split
        //taskDesc to appropriate parts
        String[] eventParts = validateAndSplitTask();
        Event event = new Event(eventParts[0], false, eventParts[1], eventParts[2]);
        tasks.addTask(event);
        saveTask(tasks, storage);
        return ui.showLeoReply(event + "\n Current Tasks: " + tasks.size());
    }

    private void saveTask(TaskList tasks, Storage storage) throws LeoException {
        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            throw new LeoException("Unable to save tasks: " + e.getMessage());
        }
    }

    private String[] validateAndSplitTask() throws LeoException {
        String[] eventParts = taskDesc.split("/from ", 2);

        if (eventParts.length < 2) {
            throw new LeoException("The description of an event must have /from.");
        }

        String[] eventDuration = eventParts[1].split("/to", 2);
        if (eventDuration.length < 2) {
            throw new LeoException("The description of an event must have /to.");
        }
        return new String[]{
                eventParts[0].trim(), // task name
                eventDuration[0].trim(), // from date/time
                eventDuration[1].trim() // to date/time
        };
    }
}
