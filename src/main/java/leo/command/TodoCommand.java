package leo.command;

import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.LeoException;
import leo.task.Todo;

import java.io.IOException;

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
     * Executes todo command by creating todo, adding to task list,
     * saving, and displaying result.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        Todo todo = new Todo(taskDesc, false);
        tasks.addTask(todo);
        try {
            storage.save(tasks.toSaveFormat());
        } catch (IOException e) {
            ui.showError("Unable to save tasks: " + e.getMessage());
        }
        ui.showLeoReply(todo + "\n Current Tasks: " + tasks.size() );

    }
}
