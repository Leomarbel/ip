package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Task;

import java.io.IOException;

/** Command to delete a task from the list. */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Creates a DeleteCommand for specified task index.
     *
     * @param index The task position to delete.
     */
    public DeleteCommand(int index) {

        this.index = index;
    }

    /**
     * @inheritDoc
     * Check for correct index and delete the task
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Index outside of list bounds :(");
        }
        Task t = tasks.get(index);
        tasks.deleteTask(index);

        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            ui.showError("Unable to save tasks: " + e.getMessage());
        }
        ui.showLeoReply("Deleted: " + t
                + "\n Tasks Left: " + tasks.size() );
    }
}
