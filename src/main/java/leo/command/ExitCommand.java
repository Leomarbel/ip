package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
/** Command to exit the program */
public class ExitCommand extends Command{

    /** @inheritDoc */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        ui.showLeoReply("Bye! Hope to see you again soon!");
    }

    /** @inheritDoc */
    @Override
    public boolean isExit() {
        return true;
    }
}
