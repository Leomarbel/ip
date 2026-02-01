package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Task;

import java.io.IOException;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

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
