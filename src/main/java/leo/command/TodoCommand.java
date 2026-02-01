package leo.command;

import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.LeoException;
import leo.task.Todo;

import java.io.IOException;

public class TodoCommand extends Command {
    private String taskDesc;

    public TodoCommand(String taskDesc) {
        this.taskDesc = taskDesc;
    }

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
