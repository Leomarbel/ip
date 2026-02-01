package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Event;

import java.io.IOException;

public class EventCommand extends Command {
    private String taskDesc;

    public EventCommand(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        String[] eventParts = taskDesc.split("/from ", 2);

        if (eventParts.length < 2) {
            throw new LeoException("The description of an event must have /from.");
        }
        String[] eventDuration = eventParts[1].split("/to", 2);

        if (eventDuration.length < 2) {
            throw new LeoException("The description of an event must have /to.");
        }
        Event event = new Event(eventParts[0], false, eventDuration[0], eventDuration[1]);
        tasks.addTask(event);

        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            ui.showError("Unable to save tasks: " + e.getMessage());
        }
        ui.showLeoReply(event + "\n Current Tasks: " + tasks.size() );
    }

    private static String[] getStrings(String parts) throws LeoException {
        String[] eventParts = parts.split("/from ", 2);

        if (eventParts.length < 2) {
            throw new LeoException("The description of an event must have /from.");
        }

        String[] eventDuration = eventParts[1].split("/to", 2);
        if (eventDuration.length < 2) {
            throw new LeoException("The description of an event must have /to.");

        }
        return eventDuration;
    }
}
