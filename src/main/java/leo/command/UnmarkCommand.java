package leo.command;

import java.io.IOException;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;

/** Command to unmark a task as not done. */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Creates an UnmarkCommand for specified task index.
     *
     * @param index The task position to unmark.
     */
    public UnmarkCommand(int index) {

        this.index = index;
    }

    /**
     * @inheritDoc
     *      Check for correct index and mark the task
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Index outside of list bounds :(");
        }
        tasks.unmarkTask(index);

        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            ui.showError("Unable to save tasks: " + e.getMessage());
        }

        ui.showLeoReply("Unmarked: " + tasks.get(index));
    }
}
