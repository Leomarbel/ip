package leo.command;

import java.io.IOException;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Task;


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
     *      Check for correct index and delete the task
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {

        //Validate if index is within tasks' bounds
        validateTask(tasks);
        Task t = tasks.get(index);
        tasks.deleteTask(index);
        saveTask(tasks, storage);
        return ui.showLeoReply("Deleted: " + t
                + "\n Tasks Left: " + tasks.size());
    }

    private void saveTask(TaskList tasks, Storage storage) throws LeoException {
        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            throw new LeoException("Unable to save tasks: " + e.getMessage());
        }
    }

    private void validateTask(TaskList tasks) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Index outside of list bounds :(");
        }
    }
}
