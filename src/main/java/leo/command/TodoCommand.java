package leo.command;

import java.io.IOException;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Todo;

/** Command to add a todo task without time constraints. */
public class TodoCommand extends Command {
    private String taskDesc;

    /**
     * Creates a TodoCommand with task description.
     *
     * @param taskDesc The todo task description.
     */
    public TodoCommand(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * @inheritDoc
     *      Executes todo command by creating todo, adding to task list,
     *      saving, and displaying result.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        Todo todo = new Todo(taskDesc, false);
        tasks.addTask(todo);
        saveTask(tasks, storage);
        return ui.showLeoReply(todo + "\nCurrent Tasks: " + tasks.size());
    }

    private void saveTask(TaskList tasks, Storage storage) throws LeoException {
        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            throw new LeoException("Unable to save tasks: " + e.getMessage());
        }
    }
}
