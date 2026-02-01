package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;

public class ExitCommand extends Command{

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        ui.showLeoReply("Bye! Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
