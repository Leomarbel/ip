package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;

import java.io.IOException;

/** Command to mark a task as done. */
public class MarkCommand extends Command {
    private int index;

    /**
     * Creates a MarkCommand for specified task index.
     *
     * @param index The task position to mark.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /** {@inheritDoc}
     * Check for correct index and unmark the task
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Index outside of list bounds :(");
        }
        tasks.markTask(index);
        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            ui.showError("Unable to save tasks: " + e.getMessage());
        }
        ui.showLeoReply("Marked: " + tasks.get(index));

    }

}
